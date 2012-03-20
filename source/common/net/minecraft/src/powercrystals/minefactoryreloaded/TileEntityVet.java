package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;

public class TileEntityVet extends TileEntityFactoryInventory
{
	public TileEntityVet()
	{
		super(25, 25);
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
	public String getInvName()
	{
		return "Veterinary";
	}

	@Override
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		for(int i = 0; i < getSizeInventory(); i++)
		{
			ItemStack s = getStackInSlot(i);
			if(s == null || s.itemID < Block.blocksList.length || !(Item.itemsList[s.itemID] instanceof ItemFood))
			{
				continue;
			}
			int healAmount = ((ItemFood)Item.itemsList[s.itemID]).getHealAmount();
			List<?> entities = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, getHarvestArea().toAxisAlignedBB());
			for(Object o : entities)
			{
				if(!(o instanceof EntityLiving) || o instanceof EntityPlayer)
				{
					continue;
				}
				EntityLiving e = (EntityLiving)o;
				if(e.getEntityHealth() < e.getMaxHealth())
				{
					e.heal(healAmount);
					decrStackSize(i, 1);
					return;
				}
			}
			return; // didn't find anyone to heal - no point in continuing to search the inventory
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
