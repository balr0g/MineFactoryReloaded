package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.src.ItemStack;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Area;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.BlockPosition;

public class TileEntityFertilizer extends TileEntityFactoryInventory
{
	private static List<Integer> fertilizerItems = new LinkedList<Integer>();
	private static Map<Integer, IFactoryFertilizable> fertilizables = new HashMap<Integer, IFactoryFertilizable>();
    
    public static void registerFertilizable(IFactoryFertilizable fertilizable)
    {
    	fertilizables.put(fertilizable.getFertilizableBlockId(), fertilizable);
    }
    
    public static void registerFertilizerItem(int itemId)
    {
    	Integer i = new Integer(itemId);
    	if(!fertilizerItems.contains(i))
		{
    		fertilizerItems.add(i);
		}
    }

	public TileEntityFertilizer()
	{
		super(10, 10);
	}

	@Override
	public String getInvName()
	{
		return "Fertilizer";
	}

    @Override
    public void doWork()
    {
		if(!powerAvailable())
		{
			return;
		}
		
		Area a = getHarvestArea();
		
		for(BlockPosition bp : a.getPositionsBottomFirst())
		{
		    int targetId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		    if(!fertilizables.containsKey(new Integer(targetId)))
		    {
		    	continue;
		    }
		    for(int stackIndex = 0; stackIndex < getSizeInventory(); stackIndex++)
		    {
		    	ItemStack fertStack = getStackInSlot(stackIndex);
		    	if(fertStack == null || !fertilizerItems.contains(new Integer(fertStack.itemID)))
		    	{
		    		continue;
		    	}
		    	IFactoryFertilizable fertilizable = fertilizables.get(new Integer(targetId));
		    	if(!fertilizable.canFertilizeBlock(worldObj, bp.x, bp.y, bp.z, fertStack))
				{
		    		continue;
				}
		    	if(fertilizable.fertilize(worldObj, bp.x, bp.y, bp.z, fertStack))
		    	{
		    		decrStackSize(stackIndex, 1);
		    	}
		    	return;
		    }
		}
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
