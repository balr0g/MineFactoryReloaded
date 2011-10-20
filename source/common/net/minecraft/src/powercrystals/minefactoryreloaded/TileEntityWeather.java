package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.buildcraft.api.API;
import net.minecraft.src.buildcraft.api.ILiquidContainer;
import net.minecraft.src.buildcraft.api.Orientations;

public class TileEntityWeather extends TileEntityFactoryInventory implements ILiquidContainer
{
	public TileEntityWeather()
	{
		super(10, 10);
	}

	@Override
	public String getInvName()
	{
		return "Weather Harvester";
	}

	@Override
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		if(worldObj.getWorldInfo().getIsRaining() && canSeeSky())
		{
			int bucketIndex = findFirstStack(Item.bucketEmpty.shiftedIndex, 0);
			if(bucketIndex >= 0)
			{
				dropStack(new ItemStack(Item.bucketWater), 0.5F, -0.5F, 0.5F);
				decrStackSize(bucketIndex, 1);
			}
			else
			{
				if(!produceLiquid(API.getLiquidForBucket(Item.bucketWater.shiftedIndex)))
				{
					dropStack(new ItemStack(Item.snowball), 0.5F, -0.5F, 0.5F);
				}
			}
		}
	}
	
	private boolean canSeeSky()
	{
		for(int y = yCoord + 1; y <= 128; y++)
		{
			int blockId = worldObj.getBlockId(xCoord, y, zCoord);
			if(Block.blocksList[blockId] != null && !Block.blocksList[blockId].isAirBlock(worldObj, xCoord, y, zCoord))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public int fill(Orientations from, int quantity, int id, boolean doFill)
	{
		return 0;
	}

	@Override
	public int empty(int quantityMax, boolean doEmpty)
	{
		return 0;
	}

	@Override
	public int getLiquidQuantity()
	{
		return 0;
	}

	@Override
	public int getCapacity()
	{
		return 0;
	}

	@Override
	public int getLiquidId()
	{
		return 0;
	}
}
