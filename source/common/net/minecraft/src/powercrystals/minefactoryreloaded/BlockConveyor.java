package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.IInventory;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IRotateableTile;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.InventoryAndSide;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class BlockConveyor extends BlockContainer implements ITextureProvider
{
	public BlockConveyor(int i, int j)
	{
		super(i, j, Material.circuits);
		setHardness(0.5F);
		setBlockName("factoryConveyor");
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 0.1F, 0.1F);
		setRequiresSelfNotify();
	}

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity)
    {
		if(entity == null)
		{
			return;
		}
        int l = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if(l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1);
        }
        if(l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2);
        }
        if(l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3);
        }
        if(l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0);
        }
    }

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(!(entity instanceof EntityItem) && !(entity instanceof EntityLiving))
		{
			return;
		}
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityConveyor)
		{
			if(Util.isRedstonePowered(te))
			{
				return;
			}
		}
		
		int md = world.getBlockMetadata(x, y, z);
		double xVelocity = 0;
		double yVelocity = 0;
		double zVelocity = 0;
		if(md == 4 || md == 5 || md == 6 || md == 7)
		{
			yVelocity = 0.2D;
		}
		if(md == 0 || md == 4 || md == 8)
		{
			if(entity.posZ > (double)z + 0.55D)
			{
				zVelocity = -0.05D;
			}
			else if(entity.posZ < (double)z + 0.45D)
			{
				zVelocity = 0.05D;
			}
			xVelocity = 0.1D;
			if(entity.posX > x + 0.8)
			{
				InventoryAndSide inv = getInventoryAtEndOfBelt(world, x, y, z);
				if(inv != null)
				{
					putInInventory(((EntityItem)entity), inv);
					return;
				}
			}
		}
		if(md == 1 || md == 5 || md == 9)
		{
			if(entity.posX > (double)x + 0.55D)
			{
				xVelocity = -0.05D;
			}
			else if(entity.posX < (double)x + 0.45D)
			{
				xVelocity = 0.05D;
			}
			zVelocity = 0.1D;
			if(entity.posZ > z + 0.8)
			{
				InventoryAndSide inv = getInventoryAtEndOfBelt(world, x, y, z);
				if(inv != null)
				{
					putInInventory(((EntityItem)entity), inv);
					return;
				}
			}
		}
		if(md == 2 || md == 6 || md == 10)
		{
			if(entity.posZ > (double)z + 0.55D)
			{
				zVelocity = -0.05D;
			}
			else if(entity.posZ < (double)z + 0.45D)
			{
				zVelocity = 0.05D;
			}
			xVelocity = -0.1D;
			if(entity.posX < z + 0.2)
			{
				InventoryAndSide inv = getInventoryAtEndOfBelt(world, x, y, z);
				if(inv != null)
				{
					putInInventory(((EntityItem)entity), inv);
					return;
				}
			}
		}
		if(md == 3 || md == 7 || md == 11)
		{
			if(entity.posX > (double)x + 0.55D)
			{
				xVelocity = -0.05D;
			}
			else if(entity.posX < (double)x + 0.45D)
			{
				xVelocity = 0.05D;
			}
			zVelocity = -0.1D;
			if(entity.posZ < z + 0.2)
			{
				InventoryAndSide inv = getInventoryAtEndOfBelt(world, x, y, z);
				if(inv != null)
				{
					putInInventory(((EntityItem)entity), inv);
					return;
				}
			}
		}
		
		setEntityVelocity(entity, xVelocity, yVelocity, zVelocity);
		entity.onGround = false;
	}
	
	private InventoryAndSide getInventoryAtEndOfBelt(World world, int x, int y, int z)
	{
		int xOffset = 0;
		int yOffset = 0;
		int zOffset = 0;
		int md = world.getBlockMetadata(x, y, z);
		if(md == 0 || md == 4 || md == 8)
		{
			xOffset = 1;
		}
		if(md == 1 || md == 5 || md == 9)
		{
			zOffset = 1;
		}
		if(md == 2 || md == 6 || md == 10)
		{
			xOffset = 1;
		}
		if(md == 3 || md == 7 || md == 11)
		{
			zOffset = 1;
		}
		if(md == 4 || md == 5 || md == 6 || md == 7)
		{
			yOffset = 1;
		}
		TileEntity te = world.getBlockTileEntity(x + xOffset, y + yOffset, z + zOffset);
		if(te != null && te instanceof IInventory)
		{
			// deliberately not including Y offset so that we can connect to the side of a machine
			return new InventoryAndSide((IInventory)te, Util.getDestinationSide(x, y, z, x + xOffset, y, z + zOffset));
		}
		return null;
	}
	
	private void putInInventory(EntityItem item, InventoryAndSide inventory)
	{
		Util.addToInventory(inventory, item.item);
		item.setEntityDead();
	}
	
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		TileEntity te = iblockaccess.getBlockTileEntity(i, j, k);
		if(te != null && te instanceof TileEntityConveyor)
		{
			if(Util.isRedstonePowered(te))
			{
				if(Util.getBool(MineFactoryReloadedCore.animateBlockFaces))
				{
					return MineFactoryReloadedCore.conveyorOffTexture;
				}
				else
				{
					return MineFactoryReloadedCore.conveyorStillOffTexture;
				}
			}
			else
			{
				return blockIndexInTexture;
			}
		}
		else
		{
			return blockIndexInTexture;
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int l = world.getBlockMetadata(i, j, k);
		float f = 0.2F;
		float f1 = 0.2F;
		if(l == 0 || l == 2)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		else if(l == 1 || l == 3)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		return AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f1, (float)(i + 1) - f, (float)j + 0.1F, (float)(k + 1) - f1);
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int l = world.getBlockMetadata(i, j, k);
		float f = 0.2F;
		float f1 = 0.2F;
		if(l == 0 || l == 2)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		else if(l == 1 || l == 3)
		{
			f = 0.05F;
			f1 = 0.05F;
		}
		return AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f1, (float)(i + 1) - f, (float)j + 0.1F, (float)(k + 1) - f1);
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1)
	{
		setBlockBoundsBasedOnState(world, i, j, k);
		return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int l = iblockaccess.getBlockMetadata(i, j, k);
		if(l >= 4 && l <= 11)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
		else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		}
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return MineFactoryReloadedCore.proxy.getRenderId();
	}

	public int quantityDropped(Random random)
	{
		return 1;
	}

	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return world.isBlockOpaqueCube(i, j - 1, k);
	}

	public void onBlockAdded(World world, int i, int j, int k)
	{
		super.onBlockAdded(world, i, j, k);
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(!MineFactoryReloadedCore.proxy.isClient(world) && Util.isHoldingWrench(entityplayer))
		{
			TileEntity te = world.getBlockTileEntity(i, j, k);
			if(te != null && te instanceof IRotateableTile)
			{
				((IRotateableTile)te).rotate();
			}
		}
		return true;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(MineFactoryReloadedCore.proxy.isClient(world))
		{
			return;
		}
		if(!world.isBlockOpaqueCube(i, j - 1, k))
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockWithNotify(i, j, k, 0);
		}
	}
	
	private void setEntityVelocity(Entity e, double x, double y, double z)
	{
		e.motionX = x;
		e.motionY = y;
		e.motionZ = z;
	}

	public float getBlockBrightness(IBlockAccess iblockaccess, int x, int y, int z)
	{
		return 1.0F;
	}
	
	@Override
	public TileEntity getBlockEntity()
	{
		return new TileEntityConveyor();
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}

	@Override
	public boolean canProvidePower()
	{
		return true;
	}
}
