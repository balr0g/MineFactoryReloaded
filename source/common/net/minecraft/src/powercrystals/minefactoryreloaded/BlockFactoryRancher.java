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

public class BlockFactoryRancher extends BlockContainer implements ITextureProvider
{
	private static int[][] sprites = new int[6][16];
	
	public BlockFactoryRancher(int i, int j)
	{
		super(i, j, Material.rock);
		
		setBlockName("rancher");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
		
		sprites[2][0] = MineFactoryReloadedCore.rancherAnimatedTexture;
		sprites[3][0] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[4][0] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[5][0] = MineFactoryReloadedCore.rancherSideTexture;

		sprites[2][1] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[3][1] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[4][1] = MineFactoryReloadedCore.rancherAnimatedTexture;
		sprites[5][1] = MineFactoryReloadedCore.steelHoleSideTexture;

		sprites[2][2] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[3][2] = MineFactoryReloadedCore.rancherAnimatedTexture;
		sprites[4][2] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[5][2] = MineFactoryReloadedCore.rancherSideTexture;

		sprites[2][3] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[3][3] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[4][3] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[5][3] = MineFactoryReloadedCore.rancherAnimatedTexture;
		
		// vet
		
		sprites[2][4] = MineFactoryReloadedCore.rancherAnimatedTexture;
		sprites[3][4] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[4][4] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[5][4] = MineFactoryReloadedCore.rancherSideTexture;

		sprites[2][5] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[3][5] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[4][5] = MineFactoryReloadedCore.rancherAnimatedTexture;
		sprites[5][5] = MineFactoryReloadedCore.steelHoleSideTexture;

		sprites[2][6] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[3][6] = MineFactoryReloadedCore.rancherAnimatedTexture;
		sprites[4][6] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[5][6] = MineFactoryReloadedCore.rancherSideTexture;

		sprites[2][7] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[3][7] = MineFactoryReloadedCore.rancherSideTexture;
		sprites[4][7] = MineFactoryReloadedCore.steelHoleSideTexture;
		sprites[5][7] = MineFactoryReloadedCore.rancherAnimatedTexture;
	}
	
	public TileEntity getBlockEntity(int metadata)
	{
		if((metadata & 4) == 0)
		{
			return new TileEntityRancher();
		}
		return new TileEntityVeterinary();
	}
	
	public TileEntity getBlockEntity()
	{
		return null;
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 1 || i == 0)
		{
			return blockIndexInTexture;
		}
		return sprites[i][j];
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		TileEntityFactoryBase rancher = (TileEntityFactoryBase)world.getBlockTileEntity(i, j, k);
		if(rancher == null)
		{
			world.setBlockTileEntity(i, j, k, getBlockEntity(world.getBlockMetadata(i, j, k)));
			rancher = (TileEntityFactoryBase)world.getBlockTileEntity(i, j, k);
		}
		rancher.neighborBlockChanged();
	}

	public void onBlockRemoval(World world, int i, int j, int k)
	{
		TileEntityFactoryInventoryBase tileentityrancher = (TileEntityFactoryInventoryBase)world.getBlockTileEntity(i, j, k);
label0:
		for(int l = 0; l < tileentityrancher.getSizeInventory(); l++)
		{
			ItemStack itemstack = tileentityrancher.getStackInSlot(l);
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
		else
		{
			TileEntityFactoryInventoryBase tileentityrancher = (TileEntityFactoryInventoryBase)world.getBlockTileEntity(i, j, k);
			if(tileentityrancher != null)
			{
				entityplayer.displayGUIChest(tileentityrancher);
			}
		}
		return true;
	}

	@Override
    protected int damageDropped(int i)
    {
        return i;
    }

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
