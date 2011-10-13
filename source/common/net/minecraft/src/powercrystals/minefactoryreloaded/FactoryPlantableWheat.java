package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class FactoryPlantableWheat extends FactoryPlantableStandard implements IFactoryPlantable
{
	public FactoryPlantableWheat()
	{
		super(Item.seeds.shiftedIndex, Block.crops.blockID);
	}
	
	@Override
	public boolean canBePlantedHere(World world, int x, int y, int z, ItemStack stack)
	{
		int groundId = world.getBlockId(x, y - 1, z);
		int ourId = world.getBlockId(x, y, z);
		return (groundId == Block.dirt.blockID || groundId == Block.grass.blockID || groundId == Block.tilledField.blockID)	&& ourId == 0;
	}

	@Override
	public void prePlant(World world, int x, int y, int z, ItemStack stack)
	{
		int groundId = world.getBlockId(x, y - 1, z);
		if(groundId != Block.tilledField.blockID)
		{
			world.setBlockWithNotify(x, y - 1, z, Block.tilledField.blockID);
		}
	}
}
