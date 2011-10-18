package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IRotateableTile;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class BlockConveyor extends BlockContainer implements ITextureProvider
{
	public BlockConveyor(int i, int j)
	{
		super(i, j, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 0.1F, 0.1F);
		setRequiresSelfNotify();
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if(!(entity instanceof EntityItem) && !(entity instanceof EntityLiving))
		{
			return;
		}
		if(world.isBlockIndirectlyGettingPowered(i, j, k))
		{
			return;
		}
		
		int md = world.getBlockMetadata(i, j, k);
		if(md == 4)
		{
			setEntityVelocity(entity, 0.1D, 0.2D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 5)
		{
			setEntityVelocity(entity, 0.0D, 0.2D, 0.1D);
			entity.onGround = false;
		}
		else if(md == 6)
		{
			setEntityVelocity(entity, -0.1D, 0.2D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 7)
		{
			setEntityVelocity(entity, 0.0D, 0.2D, -0.1D);
			entity.onGround = false;
		}
		else if(md == 8)
		{
			setEntityVelocity(entity, 0.1D, 0.0D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 9)
		{
			setEntityVelocity(entity, 0.0D, 0.0D, 0.1D);
			entity.onGround = false;
		}
		else if(md == 10)
		{
			setEntityVelocity(entity, -0.1D, 0.0D, 0.0D);
			entity.onGround = false;
		}
		else if(md == 11)
		{
			setEntityVelocity(entity, 0.0D, 0.0D, -0.1D);
			entity.onGround = false;
		}
		else if(md == 0)
		{
			if(entity.posZ > (double)k + 0.55D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, -0.05D);
			}
			else if(entity.posZ < (double)k + 0.45D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, 0.05D);
			}
			else
			{
				setEntityVelocity(entity, 0.1D, 0.0D, 0.0D);
			}
		}
		else if(md == 1)
		{
			if(entity.posX > (double)i + 0.55D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, 0.05D);
			}
			else if(entity.posX < (double)i + 0.45D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, 0.05D);
			}
			else
			{
				setEntityVelocity(entity, 0.0D, 0.0D, 0.1D);
			}
		}
		else if(md == 2)
		{
			if(entity.posZ > (double)k + 0.55D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, -0.05D);
			}
			else if(entity.posZ < (double)k + 0.45D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, 0.05D);
			}
			else
			{
				setEntityVelocity(entity, -0.1D, 0.0D, 0.0D);
			}
		}
		else if(md == 3)
		{
			if(entity.posX > (double)i + 0.55D)
			{
				setEntityVelocity(entity, -0.05D, 0.0D, -0.05D);
			}
			else if(entity.posX < (double)i + 0.45D)
			{
				setEntityVelocity(entity, 0.05D, 0.0D, -0.05D);
			}
			else
			{
				setEntityVelocity(entity, 0.0D, 0.0D, -0.1D);
			}
		}
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
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
			world.setBlockWithNotify(i, j, k, 0);
		}
	}
	
	private void setEntityVelocity(Entity e, double x, double y, double z)
	{
		e.motionX = x;
		e.motionY = y;
		e.motionZ = z;
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
