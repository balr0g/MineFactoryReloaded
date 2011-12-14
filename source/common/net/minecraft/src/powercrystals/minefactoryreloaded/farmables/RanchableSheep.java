package net.minecraft.src.powercrystals.minefactoryreloaded.farmables;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityRancher;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class RanchableSheep implements IFactoryRanchable
{
	@Override
	public Class<?> getRanchableEntity()
	{
		return EntitySheep.class;
	}

	@Override
	public List<ItemStack> ranch(World world, EntityLiving entity, TileEntityRancher rancher)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		if(world.rand.nextInt(100) > 40)
		{
			drops.add(new ItemStack(Block.cloth, 1, ((EntitySheep)entity).getFleeceColor()));
		}
		return drops;
	}

	@Override
	public boolean getDamageRanchedEntity(World world, EntityLiving entity, List<ItemStack> drops)
	{
		return world.rand.nextInt(100) > 30;
	}

	@Override
	public int getDamageAmount(World world, EntityLiving entity, List<ItemStack> drops)
	{
		return 1;
	}
}
