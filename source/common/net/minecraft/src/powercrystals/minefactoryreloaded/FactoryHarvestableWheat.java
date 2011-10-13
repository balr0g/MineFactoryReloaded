package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class FactoryHarvestableWheat extends FactoryHarvestableStandard implements IFactoryHarvestable
{
	public FactoryHarvestableWheat()
	{
		super(Block.crops.blockID, FactoryHarvestType.Normal);
	}
	
	@Override
	public boolean canBeHarvested(World world, int x, int y, int z)
	{
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return blockMetadata == 7;
	}
}
