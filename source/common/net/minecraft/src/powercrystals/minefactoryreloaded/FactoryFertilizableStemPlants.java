package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class FactoryFertilizableStemPlants implements IFactoryFertilizable
{
	private int fertilizableId;
	
	public FactoryFertilizableStemPlants(int fertilizableId)
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
