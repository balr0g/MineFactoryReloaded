package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;

public class ItemFactoryMachine extends ItemBlock
{
	private static int highestMachineMeta = 8;
	
	public ItemFactoryMachine(int i)
	{
		super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
	}

    public int getIconFromDamage(int i)
    {
    	return Math.min(i, highestMachineMeta);
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }
    
    // server version - MCP doesn't name them the same..
    public int getMetadata(int i)
    {
        return getPlacedBlockMetadata(i);
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        int md = itemstack.getItemDamage();
        if(md > highestMachineMeta)
        {
        	// return highest meta entry
        	return "factoryWeatherItem";
        }
        
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Planter)) return "factoryPlanterItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fisher)) return "factoryFisherItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Harvester)) return "factoryHarvesterItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Rancher)) return "factoryRancherItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fertilizer)) return "factoryFertilizerItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Vet)) return "factoryVetItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Collector)) return "factoryCollectorItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Breaker)) return "factoryBlockBreakerItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Weather)) return "factoryWeatherItem";
	    return "Invalid";
    }
}
