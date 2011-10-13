package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.src.ItemStack;

public class TileEntityFertilizer extends TileEntityFactoryInventoryBase
{
	private static List<Integer> fertilizerItems = new LinkedList<Integer>();
	private static Map<Integer, IFactoryFertilizable> fertilizables = new HashMap<Integer, IFactoryFertilizable>();
	
	public TileEntityFertilizer()
	{
		super(25, 25);
	}
    
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
		
        int centerX = xCoord;
        int centerY = yCoord;
        int centerZ = zCoord;
    	int ourMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    	int currentXoffset = -1;
    	int currentZoffset = -1;
        if(ourMetadata == 0 || ourMetadata == 5)
        {
            centerX -= 2;
        }
        else if(ourMetadata == 1 || ourMetadata == 6)
        {
            centerZ -= 2;
        }
        else if(ourMetadata == 2 || ourMetadata == 7)
        {
            centerX += 2;
        }
        else if(ourMetadata == 3 || ourMetadata == 8)
        {
            centerZ += 2;
        }
        else if(ourMetadata == 4 || ourMetadata == 9)
        {
            centerY -= 2;
        }
        for(currentXoffset = -1; currentXoffset <= 1; currentXoffset++)
        {
            for(currentZoffset = -1; currentZoffset <= 1; currentZoffset++)
            {
            	int targetX = centerX + currentXoffset;
            	int targetZ = centerZ + currentZoffset;
	            int targetId = worldObj.getBlockId(targetX, centerY, targetZ);
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
	            	if(!fertilizable.canFertilizeBlock(worldObj, targetX, centerY, targetZ, fertStack))
        			{
	            		continue;
        			}
	            	if(fertilizable.fertilize(worldObj, targetX, centerY, targetZ, fertStack))
	            	{
	            		decrStackSize(stackIndex, 1);
	            	}
	            	return;
	            }
            }
        }
    }
}
