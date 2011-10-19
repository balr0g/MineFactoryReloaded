package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Area;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.BlockPosition;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestType;

public class TileEntityHarvester extends TileEntityFactoryPowered
{
	private static Map<Integer, IFactoryHarvestable> harvestables = new HashMap<Integer, IFactoryHarvestable>();
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		harvestables.put(harvestable.getSourceId(), harvestable);
	}
	
	public TileEntityHarvester()
	{
		super(5, 1);
	}

	@Override
	public void doWork()
	{	
		if(!powerAvailable())
		{
			return;
		}
		
		int harvestedBlockId = 0;
		int harvestedBlockMetadata = 0;
		float dropOffsetX = 0.0F;
		float dropOffsetZ = 0.0F;
		
		if(getDirectionFacing() == Orientations.XPos)
		{
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZPos)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
		}
		else if(getDirectionFacing() == Orientations.XNeg)
		{
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZNeg)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}

		BlockPosition targetCoords = getNextHarvest();
		
		
		if(targetCoords == null)
		{
			return;
		}
		
		
		harvestedBlockId = worldObj.getBlockId(targetCoords.x, targetCoords.y, targetCoords.z);
		harvestedBlockMetadata = worldObj.getBlockMetadata(targetCoords.x, targetCoords.y, targetCoords.z);
		IFactoryHarvestable harvestable = harvestables.get(new Integer(harvestedBlockId));

		harvestable.preHarvest(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		
		List<ItemStack> drops;
		
		if(harvestable.hasDifferentDrops())
		{
			drops = harvestable.getDifferentDrops(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
		}
		else
		{
			drops = Block.blocksList[harvestedBlockId].getBlockDropped(worldObj, targetCoords.x, targetCoords.y, targetCoords.z, harvestedBlockMetadata);
		}

		if(drops != null)
		{
			for(ItemStack dropStack : drops)
			{
				dropStack(dropStack, dropOffsetX, 0, dropOffsetZ);
			}
		}
		
		if(Util.getBool(MineFactoryReloadedCore.playSounds))
		{
			worldObj.playSoundEffect(xCoord, yCoord, zCoord, "damage.fallsmall", 1.0F, 1.0F);
		}
		worldObj.setBlockWithNotify(targetCoords.x, targetCoords.y, targetCoords.z, 0);
		
		harvestable.postHarvest(worldObj, targetCoords.x, targetCoords.y, targetCoords.z);
	}

	private BlockPosition getNextHarvest()
	{
		Area harvestArea = getHarvestArea();
		for(BlockPosition bp : harvestArea.getPositions())
		{
			int searchId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			
			if(!harvestables.containsKey(new Integer(searchId)))
			{
				continue;
			}
			
			IFactoryHarvestable harvestable = harvestables.get(new Integer(searchId));
			if(harvestable.canBeHarvested(worldObj, bp.x, bp.y, bp.z))
			{
				if(harvestable.getHarvestType() == HarvestType.Normal)
				{
					return bp;
				}
				else if(harvestable.getHarvestType() == HarvestType.LeaveBottom)
				{
					BlockPosition temp = getNextVertical(bp.x, bp.y, bp.z);
					if(temp == null)
					{
						continue;
					}
					return temp;
				}
				else if(harvestable.getHarvestType() == HarvestType.Tree)
				{
					return getNextTreeSegment(bp.x, bp.y, bp.z);
				}
			}
		}
		return null;
	}
	
	private BlockPosition getNextVertical(int x, int y, int z)
	{
		int highestBlockOffset = -1;
		
		for(int currentYoffset = 1; currentYoffset < Util.getInt(MineFactoryReloadedCore.verticalHarvestSearchMaxVertical); currentYoffset++)
		{
			int blockId = worldObj.getBlockId(x, y + currentYoffset, z);
			if(harvestables.containsKey(new Integer(blockId)) && harvestables.get(new Integer(blockId)).canBeHarvested(worldObj, x, y + currentYoffset, z))
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
			return null;
		}
		
		return new BlockPosition(x, y + highestBlockOffset, z);
	}

	private BlockPosition getNextTreeSegment(int x, int y, int z)
	{
		int blockId;
 
		Area a = new Area(x - Util.getInt(MineFactoryReloadedCore.treeSearchMaxHorizontal), x + Util.getInt(MineFactoryReloadedCore.treeSearchMaxHorizontal),
				y, y + Util.getInt(MineFactoryReloadedCore.treeSearchMaxVertical),
				z - Util.getInt(MineFactoryReloadedCore.treeSearchMaxHorizontal), z + Util.getInt(MineFactoryReloadedCore.treeSearchMaxHorizontal));
		
		for(BlockPosition bp : a.getPositions())
		{
			blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
			if(harvestables.containsKey(new Integer(blockId))
					&& harvestables.get(new Integer(blockId)).canBeHarvested(worldObj, bp.x, bp.y, bp.z))
			{
				return new BlockPosition(bp.x, bp.y, bp.z);
			}

		}
		return null;
	}

}
