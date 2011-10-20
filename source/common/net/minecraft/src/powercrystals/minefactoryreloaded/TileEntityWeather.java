package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class TileEntityWeather extends TileEntityFactoryInventory
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
		
		System.out.println("Starting work: raining: " + worldObj.getWorldInfo().getIsRaining() + ", sky visible: " + canSeeSky());
		
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
				dropStack(new ItemStack(Item.snowball), 0.5F, -0.5F, 0.5F);
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
}
