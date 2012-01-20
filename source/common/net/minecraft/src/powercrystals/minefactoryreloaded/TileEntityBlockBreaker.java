package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.BlockPosition;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class TileEntityBlockBreaker extends TileEntityFactoryPowered {

	public TileEntityBlockBreaker()
	{
		super(25, 25);
	}

	@Override
	public void doWork()
	{
		if(!powerAvailable())
		{
			return;
		}
		
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
		
		BlockPosition bp = BlockPosition.fromFactoryTile(this);
		bp.moveForwards(1);
		int blockId = worldObj.getBlockId(bp.x, bp.y, bp.z);
		
		Block b = Block.blocksList[blockId];
		if(b != null && !b.isAirBlock(worldObj, bp.x, bp.y, bp.z) && !Util.isBlockUnbreakable(b))
		{
			List<ItemStack> drops = b.getBlockDropped(worldObj, bp.x, bp.y, bp.z, worldObj.getBlockMetadata(bp.x, bp.y, bp.z), 0);
			for(ItemStack s : drops)
			{
				dropStack(s, dropOffsetX, 0, dropOffsetZ);
			}
			worldObj.setBlockWithNotify(bp.x, bp.y, bp.z, 0);
		}
	}

}
