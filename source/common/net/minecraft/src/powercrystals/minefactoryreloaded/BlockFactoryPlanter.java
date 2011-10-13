package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockFactoryPlanter extends BlockContainer implements ITextureProvider
{
	
	public BlockFactoryPlanter(int i, int j)
	{
		super(i, j, Material.rock);
		setBlockName("planter");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	public TileEntity getBlockEntity()
	{
		return new TileEntityPlanter();
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 1)
		{
			return MineFactoryReloadedCore.steelHoleSideTexture;
		}
		if(i == 2)
		{
			return MineFactoryReloadedCore.planterMushroomTexture;
		}
		if(i == 3)
		{
			return MineFactoryReloadedCore.planterSugarTexture;
		}
		if(i == 4)
		{
			return MineFactoryReloadedCore.planterCactusTexture;
		}
		if(i == 5)
		{
			return MineFactoryReloadedCore.planterSaplingTexture;
		}
		return blockIndexInTexture;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		TileEntityPlanter tileentityplanter = (TileEntityPlanter)world.getBlockTileEntity(i, j, k);
		tileentityplanter.neighborBlockChanged();
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return super.canPlaceBlockAt(world, i, j, k);
	}

	public void onBlockRemoval(World world, int i, int j, int k)
	{
		TileEntityPlanter tileentityplanter = (TileEntityPlanter)world.getBlockTileEntity(i, j, k);
label0:
		for(int l = 0; l < tileentityplanter.getSizeInventory(); l++)
		{
			ItemStack itemstack = tileentityplanter.getStackInSlot(l);
			if(itemstack == null)
			{
				continue;
			}
			float f = world.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
			do
			{
				if(itemstack.stackSize <= 0)
				{
					continue label0;
				}
				int i1 = world.rand.nextInt(21) + 10;
				if(i1 > itemstack.stackSize)
				{
					i1 = itemstack.stackSize;
				}
				itemstack.stackSize -= i1;
				EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (float)world.rand.nextGaussian() * f3;
				entityitem.motionY = (float)world.rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)world.rand.nextGaussian() * f3;
				world.entityJoinedWorld(entityitem);
			} while(true);
		}

		super.onBlockRemoval(world, i, j, k);
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		TileEntityPlanter tileentityplanter = (TileEntityPlanter)world.getBlockTileEntity(i, j, k);
		if(MineFactoryReloadedCore.proxy.isClient(world))
		{
			return true;
		}
		if(tileentityplanter != null)
		{
			entityplayer.displayGUIChest(tileentityplanter);
		}
		return true;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
