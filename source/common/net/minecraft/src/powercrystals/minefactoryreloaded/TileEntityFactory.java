package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.buildcraft.api.EntityPassiveItem;
import net.minecraft.src.buildcraft.api.IPipeEntry;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.buildcraft.api.Position;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Area;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.BlockPosition;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IRotateableTile;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public abstract class TileEntityFactory extends TileEntity implements IRotateableTile
{
	private Orientations forwardDirection;
	
	protected TileEntityFactory()
	{
		forwardDirection = Orientations.XPos;
	}
	
	protected boolean canDropInPipeAt(Orientations o)
	{
		return true;
	}
	
	protected void dropStack(ItemStack s, float dropOffsetX, float dropOffsetY, float dropZ)
	{
		for(Orientations o : Util.findPipes(worldObj, xCoord, yCoord, zCoord))
		{
			BlockPosition bp = new BlockPosition(xCoord, yCoord, zCoord);
			bp.orientation = o;
			bp.moveForwards(1);
			TileEntity te = worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te != null && te instanceof IPipeEntry && canDropInPipeAt(o) && ((IPipeEntry)te).acceptItems())
			{
				Position ep = new Position(this);
				ep.x += 0.5;
				ep.y += 0.25;
				ep.z += 0.5;
				ep.orientation = o;
				ep.moveForwards(0.5);
				
				EntityPassiveItem i = new EntityPassiveItem(worldObj, ep.x, ep.y, ep.z, s);
				((IPipeEntry)te).entityEntering(i, o);
				return;
			}
		}
		
		if(Util.getBool(MineFactoryReloadedCore.machinesCanDropInChests))
		{
			for(IInventory chest : Util.findChests(worldObj, xCoord, yCoord, zCoord))
			{
				s.stackSize = Util.addToInventory(chest, s);
				if(s.stackSize == 0)
				{
					return;
				}
			}
		}
		if(s.stackSize > 0)
		{
			EntityItem entityitem = new EntityItem(worldObj, xCoord + dropOffsetX, yCoord + dropOffsetY, zCoord + dropZ, s);
			entityitem.motionX = 0.0D;
			entityitem.motionY = 0.3D;
			entityitem.motionZ = 0.0D;
			worldObj.entityJoinedWorld(entityitem);
		}
	}
	
	protected int getHarvestRadius()
	{
		return 1;
	}
	
	protected int getHarvestDistanceDown()
	{
		return 0;
	}
	
	protected int getHarvestDistanceUp()
	{
		return 0;
	}
	
	protected final Area getHarvestArea()
	{
		BlockPosition ourpos = BlockPosition.fromFactoryTile(this);
		ourpos.moveForwards(getHarvestRadius() + 1);
		return new Area(ourpos, getHarvestRadius(), 0, 0);
	}
	
	public Orientations getDirectionFacing()
	{
		return forwardDirection;
	}
	
	@Override
	public boolean canRotate()
	{
		return true;
	}
	
	@Override
	public void rotate()
	{
		if(forwardDirection == Orientations.XPos)
		{
			forwardDirection = Orientations.ZPos;
		}
		else if(forwardDirection == Orientations.ZPos)
		{
			forwardDirection = Orientations.XNeg;
		}
		else if(forwardDirection == Orientations.XNeg)
		{
			forwardDirection = Orientations.ZNeg;
		}
		else if(forwardDirection == Orientations.ZNeg)
		{
			forwardDirection = Orientations.XPos;
		}
		else
		{
			forwardDirection = Orientations.XPos;
		}
	}
	
	public void rotateDirectlyTo(int rotation)
	{
		forwardDirection = Orientations.values()[rotation];
	}
	
	public int getRotatedSide(int side)
	{
		if(side < 2)
		{
			return side;
		}
		else if(forwardDirection == Orientations.ZPos)
		{
			return addToSide(side, 1);
		}
		else if(forwardDirection == Orientations.XNeg)
		{
			return addToSide(side, 2);
		}
		else if(forwardDirection == Orientations.ZNeg)
		{
			return addToSide(side, 3);
		}
		return side;
	}
	
	private int addToSide(int side, int shift)
	{
		// 0 bottom 1 top 2 east 3 west 4 north 5 south
		// east xpos, north zneg
		// rotation is xpos -> zpos -> xneg -> zneg
		// which is east -> south -> west -> north
		// and thus 2 -> 5 -> 3 -> 4
		
		// n/s backwards, so: 2 -> 4 -> 3 -> 5
		
		int shiftsRemaining = shift;
		int out = side;
		while(shiftsRemaining > 0)
		{
			/*if(out == 2) out = 5;
			else if(out == 3) out = 4;
			else if(out == 4) out = 2;
			else if(out == 5) out = 3;*/
			if(out == 2) out = 4;
			else if(out == 4) out = 3;
			else if(out == 3) out = 5;
			else if(out == 5) out = 2;
			shiftsRemaining--;
		}
		return out;
	}
	
	public Packet getDescriptionPacket()
	{
		int[] data = new int[1];
		data[0] = getDirectionFacing().ordinal();
		Packet p = MineFactoryReloadedCore.proxy.getTileEntityPacket(this, data, null, null);
		return p;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
		super.readFromNBT(nbttagcompound);
		int rotation = nbttagcompound.getInteger("rotation");
		forwardDirection = Orientations.values()[rotation];
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("rotation", getDirectionFacing().ordinal());
    }
}
