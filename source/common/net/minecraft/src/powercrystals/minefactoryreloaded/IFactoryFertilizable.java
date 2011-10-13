package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface IFactoryFertilizable
{
	public int getFertilizableBlockId();
	
	public boolean canFertilizeBlock(World world, int x, int y, int z, ItemStack fertilizer);
	
	public boolean fertilize(World world, int x, int y, int z, ItemStack fertilizer);
}
