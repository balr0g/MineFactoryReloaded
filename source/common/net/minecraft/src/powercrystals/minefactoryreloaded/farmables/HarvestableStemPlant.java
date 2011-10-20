package net.minecraft.src.powercrystals.minefactoryreloaded.farmables;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.HarvestType;

public class HarvestableStemPlant extends HarvestableStandard
{
	public HarvestableStemPlant(int sourceId, HarvestType harvestType)
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
