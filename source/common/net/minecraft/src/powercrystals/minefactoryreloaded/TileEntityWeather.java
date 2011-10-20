package net.minecraft.src.powercrystals.minefactoryreloaded;

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
		
		if(worldObj.getWorldInfo().getIsRaining() && worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
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
}
