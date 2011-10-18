package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.buildcraft.api.IPowerReceptor;
import net.minecraft.src.buildcraft.api.PowerFramework;
import net.minecraft.src.buildcraft.api.PowerProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.PowerSystem;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public abstract class TileEntityFactoryPowered extends TileEntityFactory implements IPowerReceptor
{
	private boolean lastRedstonePowerState = false;
	private boolean redstonePowerAvailable = false;
	
	private PowerProvider powerProvider;
	private int powerNeeded;
	
	protected TileEntityFactoryPowered(int bcEnergyNeededToWork, int bcEnergyNeededToActivate)
	{
		powerProvider = PowerFramework.currentFramework.createPowerProvider();
		powerNeeded = bcEnergyNeededToWork;
		powerProvider.configure(25, powerNeeded, powerNeeded, bcEnergyNeededToActivate, powerNeeded);
	}
	
	public void neighborBlockChanged()
	{
		boolean isPowered = Util.isRedstonePowered(this);
		if(isPowered && !lastRedstonePowerState && MineFactoryReloadedCore.powerSystem == MineFactoryReloadedCore.PowerSystem.Redstone)
		{
			lastRedstonePowerState = isPowered;
			redstonePowerAvailable = true;
			System.out.println("te: power available, starting work");
			doWork();
			redstonePowerAvailable = false;
		}
		else
		{
			System.out.println("te: no power available, not starting work");
			lastRedstonePowerState = isPowered;
		}
	}
	
	protected boolean powerAvailable()
	{
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.Redstone)
		{
			System.out.println("Power framework is redstone; power available: " + redstonePowerAvailable);
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
