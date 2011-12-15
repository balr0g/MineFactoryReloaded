package net.minecraft.src.powercrystals.minefactoryreloaded.machines;

import net.minecraft.src.buildcraft.api.PowerProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityFactoryMachine;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.HarvestType;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Area;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.BlockPosition;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IMachine;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class Harvester implements IMachine {

	@Override
	public void doWork()
	{
		
	}

	@Override
	public boolean getRequiresPower()
	{
		return true;
	}

	@Override
	public int getBCPowerNeeded()
	{
		return 1;
	}

	@Override
	public boolean getHasInventory()
	{
		return false;
	}

	@Override
	public String getInventoryName()
	{
		return null;
	}

	@Override
	public boolean getIsRotateable()
	{
		return true;
	}

	@Override
	public void rotate()
	{
		
	}

	@Override
	public void setupBCPower(PowerProvider powerProvider)
	{
		
	}

	@Override
	public void neighborBlockChanged(TileEntityFactoryMachine tileEntityMachine)
	{
	}
}
