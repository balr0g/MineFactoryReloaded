package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockFactoryFertilizer extends BlockContainer implements ITextureProvider
{
	public BlockFactoryFertilizer(int i, int j)
	{
		super(i, j, Material.rock);
		setBlockName("fertilizer");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		localrandom = new Random();
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		if(metadata == 0 && side == 5)
		{
			return MineFactoryReloadedCore.fertilizerBackTexture;
		}
		if(metadata == 1 && side == 3)
		{
			return MineFactoryReloadedCore.fertilizerBackTexture;
		}
		if(metadata == 2 && side == 4)
		{
			return MineFactoryReloadedCore.fertilizerBackTexture;
		}
		if(metadata == 3 && side == 2)
		{
			return MineFactoryReloadedCore.fertilizerBackTexture;
		}
		
		return blockIndexInTexture;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return MineFactoryReloadedCore.proxy.getFertilizerRenderId();
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public TileEntity getBlockEntity()
	{
		return new TileEntityFertilizer();
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		TileEntityFertilizer fertilizer = (TileEntityFertilizer)world.getBlockTileEntity(i, j, k);
		fertilizer.neighborBlockChanged();
	}

	public void onBlockRemoval(World world, int i, int j, int k)
	{
		TileEntityFertilizer tileentityfertilizer = (TileEntityFertilizer)world.getBlockTileEntity(i, j, k);
label0:
		for(int l = 0; l < tileentityfertilizer.getSizeInventory(); l++)
		{
			ItemStack itemstack = tileentityfertilizer.getStackInSlot(l);
			if(itemstack == null)
			{
				continue;
			}
			float f = localrandom.nextFloat() * 0.8F + 0.1F;
			float f1 = localrandom.nextFloat() * 0.8F + 0.1F;
			float f2 = localrandom.nextFloat() * 0.8F + 0.1F;
			do
			{
				if(itemstack.stackSize <= 0)
				{
					continue label0;
				}
				int i1 = localrandom.nextInt(21) + 10;
				if(i1 > itemstack.stackSize)
				{
					i1 = itemstack.stackSize;
				}
				itemstack.stackSize -= i1;
				EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (float)localrandom.nextGaussian() * f3;
				entityitem.motionY = (float)localrandom.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)localrandom.nextGaussian() * f3;
				world.entityJoinedWorld(entityitem);
			} while(true);
		}

		super.onBlockRemoval(world, i, j, k);
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		TileEntityFertilizer tileentityfertilizer = (TileEntityFertilizer)world.getBlockTileEntity(i, j, k);
		if(MineFactoryReloadedCore.proxy.isClient(world))
		{
			return true;
		}
		if(entityplayer.inventory.getCurrentItem() != null &&
				entityplayer.inventory.getCurrentItem().itemID == MineFactoryReloadedCore.factoryHammerItem.shiftedIndex)
		{
			int currentMetadata = world.getBlockMetadata(i, j, k);
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
		else if(tileentityfertilizer != null)
		{
			entityplayer.displayGUIChest(tileentityfertilizer);
		}
		return true;
	}

	public int idDropped(int i, Random random)
	{
		return MineFactoryReloadedCore.fertilizerItem.shiftedIndex;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}

	private Random localrandom;
}
