package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.buildcraft.api.API;
import net.minecraft.src.buildcraft.api.EntityPassiveItem;
import net.minecraft.src.buildcraft.api.ILiquidContainer;
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
	
	protected void dropStack(ItemStack s, float dropOffsetX, float dropOffsetY, float dropOffsetZ)
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
				if(chest.getInvName() == "Engine")
				{
					continue;
				}
				s.stackSize = Util.addToInventory(chest, s);
				if(s.stackSize == 0)
				{
					return;
				}
			}
		}
		if(s.stackSize > 0)
		{
			EntityItem entityitem = new EntityItem(worldObj, xCoord + dropOffsetX, yCoord + dropOffsetY, zCoord + dropOffsetZ, s);
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
		
		if(MineFactoryReloadedCore.proxy.isServer())
		{
			MineFactoryReloadedCore.proxy.sendPacketToAll((Packet230ModLoader)getDescriptionPacket());
		}
	}
	
	public void rotateDirectlyTo(int rotation)
	{
		forwardDirection = Orientations.values()[rotation];
		if(MineFactoryReloadedCore.proxy.isClient(worldObj))
		{
			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
		}
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
		int shiftsRemaining = shift;
		int out = side;
		while(shiftsRemaining > 0)
		{
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
	
	protected boolean produceLiquid(int liquidId)
	{
		int amountToFill = API.BUCKET_VOLUME;
		for(int i = 0; i < 6; i++)
		{
			Orientations or = Orientations.values()[i];
			
			BlockPosition p = new BlockPosition(xCoord, yCoord, zCoord, or);
			p.moveForwards(1);

			TileEntity tile = worldObj.getBlockTileEntity(p.x, p.y,	p.z);

			if(tile instanceof ILiquidContainer && !(p.x == xCoord && p.y == yCoord && p.z == zCoord))
			{
				ILiquidContainer lc = (ILiquidContainer)tile;
				amountToFill -= lc.fill(or.reverse(), API.BUCKET_VOLUME, liquidId, true);
			}
		}
		if(amountToFill < API.BUCKET_VOLUME)
		{
			return true;
		}
		return false;
	}
}
