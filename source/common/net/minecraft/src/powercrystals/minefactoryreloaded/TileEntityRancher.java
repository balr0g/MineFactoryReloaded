package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.buildcraft.api.API;
import net.minecraft.src.buildcraft.api.ILiquidContainer;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class TileEntityRancher extends TileEntityFactoryInventory implements ILiquidContainer
{
	private static Map<Class<?>, IFactoryRanchable> ranchables = new HashMap<Class<?>, IFactoryRanchable>();

	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		ranchables.put(ranchable.getRanchableEntity(), ranchable);
	}
	
	public TileEntityRancher()
	{
		super(25, 25);
	}

	@Override
	public String getInvName()
	{
		return "Rancher";
	}
	
	@Override
	protected int getHarvestRadius()
	{
		return 2;
	}
	
	@Override
	protected int getHarvestDistanceDown()
	{
		return 1;
	}
	
	@Override
	protected int getHarvestDistanceUp()
	{
		return 3;
	}
	
	@Override
	protected boolean canDropInPipeAt(Orientations o)
	{
		return o == getDirectionFacing().reverse();
	}

	@Override
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		float dropOffsetX = 0.0F;
		float dropOffsetZ = 0.0F;
		
		if(getDirectionFacing() == Orientations.XPos)
		{
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZPos)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
		}
		else if(getDirectionFacing() == Orientations.XNeg)
		{
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZNeg)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}
		
		List<?> entities = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, getHarvestArea().toAxisAlignedBB());
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityLiving))
			{
				continue;
			}
			EntityLiving e = (EntityLiving)o;
			if(ranchables.containsKey(e.getClass()))
			{
				IFactoryRanchable r = ranchables.get(e.getClass());
				List<ItemStack> drops = r.ranch(worldObj, e, this);
				for(ItemStack s : drops)
				{
					if(API.getBucketForLiquid(s.itemID) != 0)
					{
						produceLiquid(s.itemID);
						continue; // abort if we got a liquid block/item - nowhere to put it, it'll just be destroyed
					}
					dropStack(s, dropOffsetX, 0, dropOffsetZ);
				}
				
				if(Util.getBool(MineFactoryReloadedCore.rancherInjuresAnimals) && r.getDamageRanchedEntity(worldObj, e, drops))
				{
					e.attackEntityFrom(DamageSource.generic, r.getDamageAmount(worldObj, e, drops));
				}
				
				if(drops.size() > 0)
				{
					return;
				}
			}
		}
	}

	@Override
	public int fill(Orientations from, int quantity, int id, boolean doFill)
	{
		return 0;
	}

	@Override
	public int empty(int quantityMax, boolean doEmpty)
	{
		return 0;
	}

	@Override
	public int getLiquidQuantity()
	{
		return 0;
	}

	@Override
	public int getCapacity()
	{
		return 0;
	}

	@Override
	public int getLiquidId()
	{
		return 0;
	}
}
