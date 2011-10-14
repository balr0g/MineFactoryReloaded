package net.minecraft.src;

import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;

public class mod_MineFactory extends BaseModMp
{	
	public mod_MineFactory()
	{
		MineFactoryReloadedCore.Init(null);
		ModLoaderMp.InitModLoaderMp();
		
	}
	
	public String Version()
	{
		return "1.8.1R1.2.2";
	}
	
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		MineFactoryReloadedCore.registerPlantable(plantable);
	}
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		MineFactoryReloadedCore.registerHarvestable(harvestable);
	}
	
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		MineFactoryReloadedCore.registerFertilizable(fertilizable);
	}
	
	public static void registerFertilizerItem(int itemId)
	{
		MineFactoryReloadedCore.registerFertilizerItem(itemId);
	}
	
	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		MineFactoryReloadedCore.registerRanchable(ranchable);
	}
}
