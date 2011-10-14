package net.minecraft.src.powercrystals.minefactoryreloaded.api;

import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityRancher;

public interface IFactoryRanchable
{
	public Class<?> getRanchableEntity();
	
	public List<ItemStack> ranch(World world, EntityLiving entity, TileEntityRancher rancher);
	public boolean getDamageRanchedEntity(World world, EntityLiving entity, List<ItemStack> drops);
	
	public int getDamageAmount(World world, EntityLiving entity, List<ItemStack> drops);
}
