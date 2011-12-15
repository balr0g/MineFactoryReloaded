package net.minecraft.src.powercrystals.minefactoryreloaded.core;

import net.minecraft.src.buildcraft.api.PowerProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityFactoryMachine;

public interface IMachine
{
	public void doWork();
	
	public boolean getRequiresPower();
	public int getBCPowerNeeded();
	
	public boolean getHasInventory();
	public String getInventoryName();
	
	public boolean getIsRotateable();
	public void rotate();
	
	public void setupBCPower(PowerProvider powerProvider);
	
	public void neighborBlockChanged(TileEntityFactoryMachine tileEntityMachine);
}
