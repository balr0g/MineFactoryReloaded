package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class TileEntityFisher extends TileEntityFactory
{
	public TileEntityFisher()
	{
		super(5, 1);
	}

	@Override
	public String getInvName()
	{
		return "Fisher";
	}
	
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		for(int xOffset = -1; xOffset <= 1; xOffset++)
		{
			for(int zOffset = -1; zOffset <= 1; zOffset++)
			{
				
				if(worldObj.getBlockId(xCoord + xOffset, yCoord - 1, zCoord + zOffset) == Block.waterStill.blockID)
				{
					int bucketIndex = findFirstStack(Item.bucketEmpty.shiftedIndex, 0);
					if(bucketIndex >= 0)
					{
						dropStack(worldObj, new ItemStack(Item.bucketWater), xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F, xCoord, yCoord, zCoord);
						setInventorySlotContents(bucketIndex, null);
						worldObj.setBlockWithNotify(xCoord + xOffset, yCoord - 1, zCoord + zOffset, 0);
						return;
					}
					if(worldObj.rand.nextInt(100) < 1)
					{
						dropStack(worldObj, new ItemStack(Item.fishRaw), xCoord + 0.5F, yCoord + 1.5F, zCoord + 0.5F, xCoord, yCoord, zCoord);
						return;
					}
				}
			}
		}
	}
}
