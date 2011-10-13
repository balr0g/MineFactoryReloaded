package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Item;
import net.minecraft.src.forge.ITextureProvider;

public class ItemFactory extends Item implements ITextureProvider
{
	public ItemFactory(int i)
	{
		super(i);
	}
	
	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.itemTexture;
	}
}
