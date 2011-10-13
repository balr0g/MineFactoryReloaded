package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;

public class TileEntityVeterinary extends TileEntityFactoryInventoryBase
{
	public TileEntityVeterinary()
	{
		super(25, 25);
	}
	
	@Override
	public String getInvName()
	{
		return "Veterinary Clinic";
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
		
		if(ourMetadata == 0)
		{
			// -Z
			pen = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 5, xCoord + 3, yCoord + 3, zCoord);
		}
		else if(ourMetadata == 1)
		{
			// -X
			pen = AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord, zCoord - 2, xCoord, yCoord + 3, zCoord + 3);
		}
		else if(ourMetadata == 2)
		{
			// +Z
			pen = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord + 1, xCoord + 3, yCoord + 3, zCoord + 6);
		}
		else 
		{
			// +X
			pen = AxisAlignedBB.getBoundingBox(xCoord + 1, yCoord, zCoord - 2, xCoord + 6, yCoord + 3, zCoord + 3);
		}
		
		List<?> entities = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, pen);
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityLiving))
			{
				continue;
			}
			EntityLiving e = (EntityLiving)o;
			if(e.health < 20)
			{
				for(int i = 0; i < getSizeInventory(); i++)
				{
					ItemStack stack = getStackInSlot(i);
					if(stack == null)
					{
						continue;
					}
					if(Item.itemsList[stack.itemID] instanceof ItemFood)
					{
						int healAmount = ((ItemFood)Item.itemsList[stack.itemID]).getHealAmount();
						e.heal(healAmount);
						decrStackSize(i, 1);
					}
				}
			}
		}
	}

}
