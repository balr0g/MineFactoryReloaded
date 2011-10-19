package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;

public class ItemFactoryMachine extends ItemBlock
{
	public ItemFactoryMachine(int i)
	{
		super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
	}

    public int getIconFromDamage(int i)
    {
        return i;
    }

    public int getPlacedBlockMetadata(int i)
    {
        return i;
    }

    public String getItemNameIS(ItemStack itemstack)
    {
        int md = itemstack.getItemDamage();
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Planter)) return "factoryPlanterItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fisher)) return "factoryFisherItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Harvester)) return "factoryHarvesterItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Rancher)) return "factoryRancherItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fertilizer)) return "factoryFertilizerItem";
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Vet)) return "factoryVetItem";
	    return "Invalid";
    }
}
