package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockFactoryFisher extends BlockContainer implements ITextureProvider
{
	public BlockFactoryFisher(int i, int j)
	{
		super(i, j, Material.rock);
		setBlockName("fisher");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	public TileEntity getBlockEntity()
	{
		return new TileEntityFisher();
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 0)
		{
			return MineFactoryReloadedCore.rancherAnimatedTexture;
		}
		if(i == 2 || i == 3)
		{
			return MineFactoryReloadedCore.fisherBucketTexture;
		}
		if(i == 4 || i == 5)
		{
			return MineFactoryReloadedCore.fisherFishTexture;
		}
		return blockIndexInTexture;
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(entityplayer.inventory.getCurrentItem() != null &&
				entityplayer.inventory.getCurrentItem().itemID == MineFactoryReloadedCore.factoryHammerItem.shiftedIndex)
		{
			TileEntityFisher fisher = (TileEntityFisher)world.getBlockTileEntity(i, j, k);
			int currentMetadata = fisher.getBlockMetadata();
			if(currentMetadata >= 3)
			{
				world.setBlockMetadataWithNotify(i, j, k, 0);
			}
			else
			{
				world.setBlockMetadataWithNotify(i, j, k, currentMetadata + 1);
			}
			world.markBlockNeedsUpdate(i, j, k);
		}
		else
		{
			TileEntityFisher fisher = (TileEntityFisher)world.getBlockTileEntity(i, j, k);
			if(fisher != null)
			{
				entityplayer.displayGUIChest(fisher);
			}
		}
		return true;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		TileEntityFisher fisher = (TileEntityFisher)world.getBlockTileEntity(i, j, k);
		if(fisher == null)
		{
			world.setBlockTileEntity(i, j, k, getBlockEntity());
			fisher = (TileEntityFisher)world.getBlockTileEntity(i, j, k);
		}
		fisher.neighborBlockChanged();
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
