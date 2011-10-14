package net.minecraft.src.powercrystals.minefactoryreloaded.farmables;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableStemPlants implements IFactoryFertilizable
{
	private int fertilizableId;
	
	public FertilizableStemPlants(int fertilizableId)
	{
		this.fertilizableId = fertilizableId;
	}

	@Override
	public int getFertilizableBlockId()
	{
		return fertilizableId;
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
		MineFactoryReloadedCore.proxy.fertilizeStemPlant(world, x, y, z);
		return world.getBlockMetadata(x, y, z) == 7;
	}

}
