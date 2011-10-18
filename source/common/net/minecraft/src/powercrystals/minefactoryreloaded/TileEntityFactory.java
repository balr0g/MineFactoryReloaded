package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.api.Orientations;
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
	
	protected void dropStack(World world, ItemStack s, float dropX, float dropY, float dropZ, int harvesterX, int harvesterY, int harvesterZ)
	{
		if(Util.getBool(MineFactoryReloadedCore.machinesCanDropInChests))
		{
			for(IInventory chest : Util.findChests(world, harvesterX, harvesterY, harvesterZ))
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
			EntityItem entityitem = new EntityItem(world, dropX, dropY, dropZ, s);
			entityitem.motionX = 0.0D;
			entityitem.motionY = 0.3D;
			entityitem.motionZ = 0.0D;
			world.entityJoinedWorld(entityitem);
		}
	}
	
	protected int getHarvestRadius()
	{
		return 1;
	}
	
	protected Area getHarvestArea()
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
			Packet230ModLoader p = new Packet230ModLoader();
			p.packetType = 0;
			p.dataInt = new int[5];
			p.dataInt[0] = worldObj.getWorldInfo().getDimension();
			p.dataInt[1] = xCoord;
			p.dataInt[2] = yCoord;
			p.dataInt[3] = zCoord;
			p.dataInt[4] = getDirectionFacing().ordinal();
			MineFactoryReloadedCore.proxy.sendPacketToAll(p);
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
		int shiftsRemaining = shift;
		int out = side;
		while(shiftsRemaining > 0)
		{
			if(out == 2) out = 5;
			else if(out == 3) out = 4;
			else if(out == 4) out = 2;
			else if(out == 5) out = 3;
			shiftsRemaining--;
		}
		return out;
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
