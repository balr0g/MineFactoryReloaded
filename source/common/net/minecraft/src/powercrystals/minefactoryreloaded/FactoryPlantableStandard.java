package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

/*
 * Used for directly placing blocks (ie saplings) and items (ie sugarcane). Pass in source ID to constructor,
 * so one instance per source ID.
 */

public class FactoryPlantableStandard implements IFactoryPlantable
{
	private int sourceId;
	private int plantedBlockId;
	
	public FactoryPlantableStandard(int sourceId, int plantedBlockId)
	{
		if(plantedBlockId >= Block.blocksList.length)
		{
			throw new IllegalArgumentException("Passed an Item ID to FactoryPlantableStandard's planted block argument");
		}
		this.sourceId = sourceId;
		this.plantedBlockId = plantedBlockId;
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		return Block.blocksList[plantedBlockId].canPlaceBlockAt(world, x, y, z) && world.getBlockId(x, y, z) == 0;
	}

	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
		return;
	}
	
	@Override
	public void postPlant(World world, int x, int y, int z)
	{
		return;
	}

	@Override
	public int getPlantedBlockId(World world, int x, int y, int z, ItemStack stack)
	{
		if(stack.itemID != sourceId)
		{
			return -1;
		}
		return plantedBlockId;
	}

	@Override
	public int getPlantedBlockMetadata(World world, int x, int y, int z, ItemStack stack)
	{
		return stack.getItemDamage();
	}
	
	@Override
	public int getSourceId()
	{
		return sourceId;
	}
}
