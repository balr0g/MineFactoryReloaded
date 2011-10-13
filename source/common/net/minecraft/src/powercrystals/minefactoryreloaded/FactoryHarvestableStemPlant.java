package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class FactoryHarvestableStemPlant extends FactoryHarvestableStandard
{
	public FactoryHarvestableStemPlant(int sourceId, FactoryHarvestType harvestType)
	{
		super(sourceId, harvestType);
	}
	
	@Override
	public void postHarvest(World world, int x, int y, int z)
	{
		int blockId = world.getBlockId(x, y, z);
		int groundId = world.getBlockId(x, y - 1, z);
		if(blockId == 0 && (groundId == Block.dirt.blockID || groundId == Block.grass.blockID))
		{
			world.setBlockWithNotify(x, y - 1, z, Block.tilledField.blockID);
		}
	}
}
