package net.minecraft.src.powercrystals.minefactoryreloaded.farmables;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCrops;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableWheat implements IFactoryFertilizable
{
	@Override
	public int getFertilizableBlockId()
	{
		return Block.crops.blockID;
	}

	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, ItemStack fertilizer)
	{
		return fertilizer.itemID == Item.dyePowder.shiftedIndex && fertilizer.getItemDamage() == 15 &&
			world.getBlockMetadata(x, y, z) < 7;
	}

	@Override
	public boolean fertilize(World world, int x, int y, int z, ItemStack fertilizer)
	{
		((BlockCrops)Block.crops).fertilize(world, x, y, z);
		return true;
	}
}
