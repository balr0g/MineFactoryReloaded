package net.minecraft.src;

import net.minecraft.src.BaseModMp;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class mod_MineFactoryCompat_Grass extends BaseModMp
{
	@Override
	public String Version()
	{
		return "1.3.0B";
	}
	
	@Override
	public void ModsLoaded()
	{
		mod_MineFactory.registerFertilizable(new FactoryFertilizableGrass());
	}
	
	public class FactoryFertilizableGrass implements IFactoryFertilizable
	{
		@Override
		public boolean canFertilizeBlock(World world, int x, int y, int z, ItemStack fertilizer)
		{
			return fertilizer.itemID == Item.dyePowder.shiftedIndex && fertilizer.getItemDamage() == 15;
		}

		@Override
		public boolean fertilize(World world, int x, int y, int z, ItemStack fertilizer)
		{
label0:
            for(int j1 = 0; j1 < 128; j1++)
            {
                int k1 = x;
                int l1 = y + 1;
                int i2 = z;
                for(int j2 = 0; j2 < j1 / 16; j2++)
                {
                    k1 += world.rand.nextInt(3) - 1;
                    l1 += ((world.rand.nextInt(3) - 1) * world.rand.nextInt(3)) / 2;
                    i2 += world.rand.nextInt(3) - 1;
                    if(world.getBlockId(k1, l1 - 1, i2) != Block.grass.blockID || world.isBlockNormalCube(k1, l1, i2))
                    {
                        continue label0;
                    }
                }

                if(world.getBlockId(k1, l1, i2) != 0)
                {
                    continue;
                }
                if(world.rand.nextInt(10) != 0)
                {
                    world.setBlockAndMetadataWithNotify(k1, l1, i2, Block.tallGrass.blockID, 1);
                    continue;
                }
                if(world.rand.nextInt(3) != 0)
                {
                    world.setBlockWithNotify(k1, l1, i2, Block.plantYellow.blockID);
                } else
                {
                    world.setBlockWithNotify(k1, l1, i2, Block.plantRed.blockID);
                }
            }
			return true;
		}

		@Override
		public int getFertilizableBlockId()
		{
			return Block.grass.blockID;
		}
		
	}
}
