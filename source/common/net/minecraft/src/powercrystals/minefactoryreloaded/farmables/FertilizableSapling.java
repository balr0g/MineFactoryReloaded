package net.minecraft.src.powercrystals.minefactoryreloaded.farmables;

import net.minecraft.src.Block;
import net.minecraft.src.BlockSapling;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableSapling implements IFactoryFertilizable
{	
	@Override
	public int getFertilizableBlockId()
	{
		return Block.sapling.blockID;
	}

	@Override
	public boolean canFertilizeBlock(World world, int x, int y, int z, ItemStack fertilizer)
	{
		return fertilizer.itemID == Item.dyePowder.shiftedIndex && fertilizer.getItemDamage() == 15;
	}

	@Override
	public boolean fertilize(World world, int x, int y, int z, ItemStack fertilizer)
	{
		((BlockSapling)Block.sapling).growTree(world, x, y, z, world.rand);
        return world.getBlockId(x, y, z) != Block.sapling.blockID;
	}
}
