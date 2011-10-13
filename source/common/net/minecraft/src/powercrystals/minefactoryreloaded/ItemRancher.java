package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemRancher extends ItemBlock
{

	public ItemRancher(int i)
	{
		super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
	}
	
    public int getIconFromDamage(int i)
    {
        return MineFactoryReloadedCore.rancherBlock.getBlockTextureFromSideAndMetadata(2, i);
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
    	if((itemstack.getItemDamage() & 4) == 0)
    	{
    		return "block.rancher";
    	}
    	else
    	{
    		return "block.veterinary";
    	}
    }
}
