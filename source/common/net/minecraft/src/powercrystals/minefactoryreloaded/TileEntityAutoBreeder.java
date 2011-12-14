package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;
import java.util.Random;

import net.minecraft.src.EntityAnimal;
import net.minecraft.src.Item;

public class TileEntityAutoBreeder extends TileEntityFactoryInventory
{
	private Random rand;
	
	public TileEntityAutoBreeder()
	{
		super(25, 25);
		rand = new Random();
	}

	@Override
	public String getInvName()
	{
		return "Auto Breeder";
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
		return 2;
	}

	@Override
	public void doWork()
	{
		int wheatslot = findFirstStack(Item.wheat.shiftedIndex, 0);
		if(wheatslot < 0)
		{
			return;
		}
		
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityAnimal.class, getHarvestArea().toAxisAlignedBB());
		for(Object o : entities)
		{
			if(!(o instanceof EntityAnimal))
			{
				continue;
			}
			EntityAnimal animal = (EntityAnimal)o;

	        if(MineFactoryReloadedCore.proxy.getAnimalMethodG(animal) == 0)
	        {
	            decrStackSize(wheatslot, 1);
	            MineFactoryReloadedCore.proxy.setFieldA(animal, 600);
	            MineFactoryReloadedCore.proxy.setEntityToAttack(animal, null);
	            for(int i = 0; i < 7; i++)
	            {
	                double d = rand.nextGaussian() * 0.02D;
	                double d1 = rand.nextGaussian() * 0.02D;
	                double d2 = rand.nextGaussian() * 0.02D;
	                worldObj.spawnParticle("heart", (animal.posX + (double)(rand.nextFloat() * animal.width * 2.0F)) - (double)animal.width, animal.posY + 0.5D + (double)(rand.nextFloat() * animal.height), (animal.posZ + (double)(rand.nextFloat() * animal.width * 2.0F)) - (double)animal.width, d, d1, d2);
	            }
	            return;
	        }
		}
	}
}
