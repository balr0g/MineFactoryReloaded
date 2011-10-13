package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFactoryFertilizer extends ItemFactory
{
    public ItemFactoryFertilizer(int i)
    {
        super(i);
        maxStackSize = 64;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            j--;
        }
        if(l == 1)
        {
            j++;
        }
        if(l == 2)
        {
            k--;
        }
        if(l == 3)
        {
            k++;
        }
        if(l == 4)
        {
            i--;
        }
        if(l == 5)
        {
            i++;
        }
        if(MineFactoryReloadedCore.fertilizerBlock.canPlaceBlockAt(world, i, j, k))
        {
            itemstack.stackSize--;
            world.setBlockWithNotify(i, j, k, MineFactoryReloadedCore.fertilizerBlock.blockID);
            return true;
        }
        else
        {
        	return false;
        }
    }
}
