package net.minecraft.src.powercrystals.minefactoryreloaded.farmables;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.HarvestType;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableWheat extends HarvestableStandard implements IFactoryHarvestable
{
	public HarvestableWheat()
	{
		super(Block.crops.blockID, HarvestType.Normal);
	}
	
	@Override
	public boolean canBeHarvested(World world, int x, int y, int z)
	{
		int blockMetadata = world.getBlockMetadata(x, y, z);
		return blockMetadata == 7;
	}
}
