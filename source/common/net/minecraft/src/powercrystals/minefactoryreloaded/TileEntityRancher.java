package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class TileEntityRancher extends TileEntityFactoryRotateable
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
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZPos)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}
		else if(getDirectionFacing() == Orientations.XNeg)
		{
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZNeg)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
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
					dropStack(worldObj, s, (float)xCoord + dropOffsetX, (float)yCoord, (float)zCoord + dropOffsetZ, xCoord, yCoord, zCoord);
				}
				
				if(MineFactoryReloadedCore.RancherInjuresAnimals && r.getDamageRanchedEntity(worldObj, e, drops))
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
}
