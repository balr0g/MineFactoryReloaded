package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class TileEntityRancher extends TileEntityFactoryInventoryRotateable
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
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		AxisAlignedBB pen;
		int ourMetadata = getBlockMetadata();
		float dropOffsetX = 0.0F;
		float dropOffsetZ = 0.0F;
		
		if(ourMetadata == 0)
		{
			// -Z
			pen = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 5, xCoord + 3, yCoord + 3, zCoord);
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}
		else if(ourMetadata == 1)
		{
			// -X
			pen = AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord, zCoord - 2, xCoord, yCoord + 3, zCoord + 3);
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(ourMetadata == 2)
		{
			// +Z
			pen = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord + 1, xCoord + 3, yCoord + 3, zCoord + 6);
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
		}
		else 
		{
			// +X
			pen = AxisAlignedBB.getBoundingBox(xCoord + 1, yCoord, zCoord - 2, xCoord + 6, yCoord + 3, zCoord + 3);
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		
		List<?> entities = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, pen);
		
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
