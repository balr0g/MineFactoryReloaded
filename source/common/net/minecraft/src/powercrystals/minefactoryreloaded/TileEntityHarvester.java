package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestType;

public class TileEntityHarvester extends TileEntityFactoryInventoryRotateable
{
	private static Map<Integer, IFactoryHarvestable> harvestables = new HashMap<Integer, IFactoryHarvestable>();
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		harvestables.put(harvestable.getSourceId(), harvestable);
	}
	
	public TileEntityHarvester()
	{
		super(10, 1);
	}

	@Override
	public String getInvName()
	{
		return "Harvester";
	}

	@Override
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
		int targetCoords[] = new int[3];
		int ourMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int harvestedBlockId = 0;
		int harvestedBlockMetadata = 0;
		float dropOffsetX = 0.0F;
		float dropOffsetZ = 0.0F;
		
		if(ourMetadata == 0 || ourMetadata == 4)
		{
			targetCoords = getNextHarvest(worldObj, xCoord, yCoord, zCoord - 1, ourMetadata);
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}
		else if(ourMetadata == 1 || ourMetadata == 5)
		{
			targetCoords = getNextHarvest(worldObj, xCoord - 1, yCoord, zCoord, ourMetadata);
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(ourMetadata == 2 || ourMetadata == 6)
		{
			targetCoords = getNextHarvest(worldObj, xCoord, yCoord, zCoord + 1, ourMetadata);
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
		}
		else if(ourMetadata == 3 || ourMetadata == 7)
		{
			targetCoords = getNextHarvest(worldObj, xCoord + 1, yCoord, zCoord, ourMetadata);
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		
		if(targetCoords[1] < 0)
		{
			return;
		}
		
		harvestedBlockId = worldObj.getBlockId(targetCoords[0], targetCoords[1], targetCoords[2]);
		harvestedBlockMetadata = worldObj.getBlockMetadata(targetCoords[0], targetCoords[1], targetCoords[2]);
		IFactoryHarvestable harvestable = harvestables.get(new Integer(harvestedBlockId));

		harvestable.preHarvest(worldObj, targetCoords[0], targetCoords[1], targetCoords[2]);
		
		List<ItemStack> drops;
		
		if(harvestable.hasDifferentDrops())
		{
			drops = harvestable.getDifferentDrops(worldObj, xCoord, yCoord, zCoord);
		}
		else
		{
			drops = Block.blocksList[harvestedBlockId].getBlockDropped(worldObj, xCoord, yCoord, zCoord, harvestedBlockMetadata);
		}

		if(drops != null)
		{
			for(ItemStack dropStack : drops)
			{
				dropStack(worldObj, dropStack, (float)xCoord + dropOffsetX, (float)yCoord, (float)zCoord + dropOffsetZ, xCoord, yCoord, zCoord);
			}
		}
		
		if(MineFactoryReloadedCore.PlaySounds)
		{
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "damage.fallsmall", 1.0F, 1.0F);
		}
		worldObj.setBlockWithNotify(targetCoords[0], targetCoords[1], targetCoords[2], 0);
		
		harvestable.postHarvest(worldObj, targetCoords[0], targetCoords[1], targetCoords[2]);
	}

	private int[] getNextHarvest(World world, int i, int j, int k, int ourMetadata)
	{
		int[] target = new int[3];
		int currentXoffset = -1;
		int currentZoffset = -1;
		
		int centerX = i;
		int centerZ = k;
		
		if(ourMetadata == 0 || ourMetadata == 4)
		{
			centerZ--;
		}
		else if(ourMetadata == 1 || ourMetadata == 5)
		{
			centerX--;
		}
		else if(ourMetadata == 2 || ourMetadata == 6)
		{
			centerZ++;
		}
		else if(ourMetadata == 3 || ourMetadata == 7)
		{
			centerX++;
		}
		
		for(currentXoffset = -1; currentXoffset <= 1; currentXoffset++)
		{
			for(currentZoffset = -1; currentZoffset <= 1; currentZoffset++)
			{
				int searchId = world.getBlockId(centerX + currentXoffset, j, centerZ + currentZoffset);
				
				if(!harvestables.containsKey(new Integer(searchId)))
				{
					continue;
				}
				
				IFactoryHarvestable harvestable = harvestables.get(new Integer(searchId));
				if(harvestable.canBeHarvested(world, centerX + currentXoffset, j, centerZ + currentZoffset))
				{
					if(harvestable.getHarvestType() == HarvestType.Normal)
					{
						target[0] = centerX + currentXoffset;
						target[1] = j;
						target[2] = centerZ + currentZoffset;
						return target;
					}
					else if(harvestable.getHarvestType() == HarvestType.LeaveBottom)
					{
						int[] temp = getNextVertical(world, centerX + currentXoffset, j, centerZ + currentZoffset);
						if(temp[1] < 0)
						{
							continue;
						}
						return temp;
					}
					else if(harvestable.getHarvestType() == HarvestType.Tree)
					{
						return getNextLog(world, centerX + currentXoffset, j, centerZ + currentZoffset);
					}
				}
			}
		}
		
		target[1] = -1;
		return target;
	}
	
	private int[] getNextVertical(World world, int x, int y, int z)
	{
		int target[] = new int[3];
		int highestBlockOffset = -1;
		
		for(int currentYoffset = 1; currentYoffset < MineFactoryReloadedCore.SugarAndCactusSearchMaxVertical; currentYoffset++)
		{
			int blockId = world.getBlockId(x, y + currentYoffset, z);
			if(harvestables.containsKey(new Integer(blockId)) && harvestables.get(new Integer(blockId)).canBeHarvested(world, x, y + currentYoffset, z))
			{
				highestBlockOffset = currentYoffset;
			}
			else
			{
				break;
			}
		}
		
		if(highestBlockOffset < 0)
		{
			target[1] = -1;
			return target;
		}
		
		target[0] = x;
		target[1] = y + highestBlockOffset;
		target[2] = z;
		
		return target;
	}

	private int[] getNextLog(World world, int i, int j, int k)
	{
		int target[] = new int[3];
		int currentXoffset;
		int currentYoffset;
		int currentZoffset;
		int searchX;
		int searchY;
		int searchZ;
		int blockId;
 
		for(currentYoffset = MineFactoryReloadedCore.TreeSearchMaxVertical; currentYoffset >= 0 ; currentYoffset--)
		{
			for(currentXoffset = -MineFactoryReloadedCore.TreeSearchMaxHorizontal; currentXoffset < MineFactoryReloadedCore.TreeSearchMaxHorizontal; currentXoffset++)
			{
				for(currentZoffset = -MineFactoryReloadedCore.TreeSearchMaxHorizontal; currentZoffset < MineFactoryReloadedCore.TreeSearchMaxHorizontal; currentZoffset++)
				{
					searchX = i + currentXoffset;
					searchY = j + currentYoffset;
					searchZ = k + currentZoffset;
					blockId = world.getBlockId(searchX, searchY, searchZ);
					if(harvestables.containsKey(new Integer(blockId))
							&& harvestables.get(new Integer(blockId)).canBeHarvested(world, searchX, searchY, searchZ))
					{
						target[0] = searchX;
						target[1] = searchY;
						target[2] = searchZ;
						return target;
					}
				}
			}
		}
		
		target[1] = -1;
		
		return target;
	}

}
