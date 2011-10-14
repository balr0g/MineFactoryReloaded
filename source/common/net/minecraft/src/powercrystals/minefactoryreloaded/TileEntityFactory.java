package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityItem;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.api.IPowerReceptor;
import net.minecraft.src.buildcraft.api.PowerFramework;
import net.minecraft.src.buildcraft.api.PowerProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.PowerSystem;

public abstract class TileEntityFactory extends TileEntity implements IPowerReceptor
{
	private boolean lastRedstonePowerState = false;
	private boolean redstonePowerAvailable = false;
	
	private PowerProvider powerProvider;
	private int powerNeeded;
	
	protected TileEntityFactory(int bcEnergyNeededToWork, int bcEnergyNeededToActivate)
	{
		powerProvider = PowerFramework.currentFramework.createPowerProvider();
		powerNeeded = bcEnergyNeededToWork;
		powerProvider.configure(25, powerNeeded, powerNeeded, bcEnergyNeededToActivate, powerNeeded);
	}
	
	protected void dropStack(World world, ItemStack s, float dropX, float dropY, float dropZ, int harvesterX, int harvesterY, int harvesterZ)
	{
		if(MineFactoryReloadedCore.HarvesterCanDropInChests)
		{
			for(IInventory chest : InventoryUtil.findChests(world, harvesterX, harvesterY, harvesterZ))
			{
				s.stackSize = InventoryUtil.addToInventory(chest, s);
				if(s.stackSize == 0)
				{
					return;
				}
			}
		}
		if(s.stackSize > 0)
		{
			EntityItem entityitem = new EntityItem(world, dropX, dropY, dropZ, s);
			entityitem.motionX = 0.0D;
			entityitem.motionY = 0.3D;
			entityitem.motionZ = 0.0D;
			world.entityJoinedWorld(entityitem);
		}
	}
	
	public void neighborBlockChanged()
	{
		boolean isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		if(isPowered && !lastRedstonePowerState && MineFactoryReloadedCore.powerSystem == MineFactoryReloadedCore.PowerSystem.Redstone)
		{
			lastRedstonePowerState = isPowered;
			redstonePowerAvailable = true;
			doWork();
			redstonePowerAvailable = false;
		}
		else
		{
			lastRedstonePowerState = isPowered;
		}
	}
	
	protected boolean powerAvailable()
	{
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.Redstone)
		{
			return redstonePowerAvailable;
		}
		else if(MineFactoryReloadedCore.powerSystem == PowerSystem.BuildCraft)
		{
			if(powerProvider.useEnergy(powerNeeded, powerNeeded, false) >= powerNeeded)
			{
				powerProvider.useEnergy(powerNeeded, powerNeeded, true);
				return true;
			}
			return false;
		}
		return false;
	}
	
	// base methods
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.BuildCraft)
		{
			getPowerProvider().update(this);
		}
	}
	
	// IPowerReceptor methods

	@Override
	public void setPowerProvider(PowerProvider provider)
	{
		powerProvider = provider;
	}

	@Override
	public PowerProvider getPowerProvider()
	{
		return powerProvider;
	}

	@Override
	public int powerRequest()
	{
		return powerNeeded;
	}
}
