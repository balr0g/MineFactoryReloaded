package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class FactoryHarvestableStandard implements IFactoryHarvestable
{
	private int sourceId;
	private FactoryHarvestType harvestType;
	
	public FactoryHarvestableStandard(int sourceId, FactoryHarvestType harvestType)
	{
		if(sourceId > Block.blocksList.length)
		{
			throw new IllegalArgumentException("Passed an Item ID to FactoryHarvestableStandard's source block argument");
		}
		this.sourceId = sourceId;
		this.harvestType = harvestType;
	}

	@Override
	public int getSourceId()
	{
		return sourceId;
	}

	@Override
	public FactoryHarvestType getHarvestType()
	{
		return harvestType;
	}

	@Override
	public boolean canBeHarvested(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean hasDifferentDrops()
	{
		return false;
	}

	@Override
	public List<ItemStack> getDifferentDrops(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void preHarvest(World world, int x, int y, int z)
	{
	}

	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
	}
}
