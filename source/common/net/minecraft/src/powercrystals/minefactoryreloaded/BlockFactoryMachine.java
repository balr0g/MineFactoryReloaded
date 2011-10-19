package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.api.IPipeConnection;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class BlockFactoryMachine extends BlockContainer implements IPipeConnection, ITextureProvider
{
	public static int[][] textures = new int[16][6];
	
	public BlockFactoryMachine(int i, int j, Material material)
	{
		super(i, j, material);
		setBlockName("blockFactoryMachine");
	}

    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
    	int md = iblockaccess.getBlockMetadata(x, y, z);
    	TileEntity te = iblockaccess.getBlockTileEntity(x, y, z);
    	if(te instanceof TileEntityFactory)
    	{
    		return textures[md][((TileEntityFactory)te).getRotatedSide(side)];
    	}
    	return textures[md][side];
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        return textures[j][i];
    }

	@Override
    protected int damageDropped(int i)
    {
        return i;
    }

	@Override
	public TileEntity getBlockEntity()
	{
		return null;
	}
	
	@Override
    public TileEntity getBlockEntity(int md)
	{
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Planter)) return new TileEntityPlanter();
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fisher)) return new TileEntityFisher();
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Harvester)) return new TileEntityHarvester();
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Rancher)) return new TileEntityRancher();
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fertilizer)) return new TileEntityFertilizer();
	    if(md == MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Vet)) return new TileEntityVet();
	    return null;
	}

	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if(MineFactoryReloadedCore.proxy.isClient(world))
		{
			return true;
		}
		if(Util.isHoldingWrench(entityplayer))
		{
			if(te instanceof TileEntityFactory)
			{
				((TileEntityFactory)te).rotate();
				world.markBlockNeedsUpdate(i, j, k);
			}
		}
		else if(te != null && te instanceof TileEntityFactoryInventory && ((TileEntityFactoryInventory)te).getSizeInventory() == 27)
		{
			entityplayer.displayGUIChest((TileEntityFactoryInventory)te);
		}
		return true;
	}

	@Override
	public void onBlockRemoval(World world, int i, int j, int k)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if(te == null || !(te instanceof IInventory))
		{
			return;
		}
		
		IInventory inventory = ((IInventory)te);
label0:
		for(int l = 0; l < inventory.getSizeInventory(); l++)
		{
			ItemStack itemstack = inventory.getStackInSlot(l);
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
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if(te != null && te instanceof TileEntityFactoryPowered)
		{
			((TileEntityFactoryPowered)te).neighborBlockChanged();
		}
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	@Override
	public boolean isPipeConnected(IBlockAccess blockAccess, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		return true;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
