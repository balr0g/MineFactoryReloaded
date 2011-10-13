package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockFactoryHarvester extends BlockContainer implements ITextureProvider
{
	private static int[][] sprites = new int[6][16];
	
	public BlockFactoryHarvester(int i, int j)
	{
		super(i, j, Material.rock);

		setBlockName("harvester");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		
		sprites[2][0] = MineFactoryReloadedCore.harvesterAnimatedTexture;
		sprites[3][0] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[4][0] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[5][0] = MineFactoryReloadedCore.rancherSideTexture;

		sprites[2][1] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[3][1] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[4][1] = MineFactoryReloadedCore.harvesterAnimatedTexture;
		sprites[5][1] = MineFactoryReloadedCore.steelHoleSideTexture;

		sprites[2][2] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[3][2] = MineFactoryReloadedCore.harvesterAnimatedTexture;
		sprites[4][2] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[5][2] = MineFactoryReloadedCore.rancherSideTexture;

		sprites[2][3] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[3][3] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[4][3] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[5][3] = MineFactoryReloadedCore.harvesterAnimatedTexture;
	}

	public TileEntity getBlockEntity()
	{
		return new TileEntityHarvester();
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 1 || i == 0)
		{
			return blockIndexInTexture;
		}
		return sprites[i][j];
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(entityplayer.inventory.getCurrentItem() != null &&
				entityplayer.inventory.getCurrentItem().itemID == MineFactoryReloadedCore.factoryHammerItem.shiftedIndex)
		{
			TileEntityHarvester harvester = (TileEntityHarvester)world.getBlockTileEntity(i, j, k);
			int currentMetadata = harvester.getBlockMetadata();
			if(currentMetadata >= 3)
			{
				world.setBlockMetadataWithNotify(i, j, k, 0);
				harvester.setBlockMetadata(0);
			}
			else
			{
				world.setBlockMetadataWithNotify(i, j, k, currentMetadata + 1);
				harvester.setBlockMetadata(currentMetadata + 1);
			}
			world.markBlockNeedsUpdate(i, j, k);
		}
		return true;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		TileEntityHarvester harvester = (TileEntityHarvester)world.getBlockTileEntity(i, j, k);
		if(harvester == null)
		{
			world.setBlockTileEntity(i, j, k, getBlockEntity());
			harvester = (TileEntityHarvester)world.getBlockTileEntity(i, j, k);
		}
		harvester.neighborBlockChanged();
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
