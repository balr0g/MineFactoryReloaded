package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockConveyor extends Block implements ITextureProvider
{
	public BlockConveyor(int i, int j)
	{
		super(i, j, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 0.1F, 0.1F);
		setRequiresSelfNotify();
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		/*if((entity instanceof EntityItem) || (entity instanceof EntityLiving))
		{
			int l = world.getBlockMetadata(i, j, k);
			if(l == 4)
			{
				setEntityVelocity(entity, -0.1D, 0.2D, 0.0D);
				entity.onGround = false;
			}
			else if(l == 5)
			{
				setEntityVelocity(entity, 0.1D, 0.2D, 0.0D);
				entity.onGround = false;
			}
			else if(l == 6)
			{
				setEntityVelocity(entity, 0.0D, 0.2D, 0.1D);
				entity.onGround = false;
			}
			else if(l == 7)
			{
				setEntityVelocity(entity, 0.0D, 0.2D, -0.1D);
				entity.onGround = false;
			}
			else if(l == 8)
			{
				setEntityVelocity(entity, 0.1D, 0.0D, 0.0D);
				entity.onGround = false;
			}
			else if(l == 9)
			{
				setEntityVelocity(entity, -0.1D, 0.0D, 0.0D);
				entity.onGround = false;
			}
			else if(l == 10)
			{
				setEntityVelocity(entity, 0.0D, 0.0D, -0.1D);
				entity.onGround = false;
			}
			else if(l == 11)
			{
				setEntityVelocity(entity, 0.0D, 0.0D, 0.1D);
				entity.onGround = false;
			}
			else if(l == 0)
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
			else if(l == 1)
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
			else if(l == 2)
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
			else if(l == 3)
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
		}*/
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
		if(!MineFactoryReloadedCore.proxy.isClient(world))
		{
			world.setBlockMetadataWithNotify(i, j, k, 0);
			//checkSurroundingTrack(world, i, j, k, 1);
		}
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(!MineFactoryReloadedCore.proxy.isClient(world) && entityplayer.inventory.getCurrentItem() != null &&
				entityplayer.inventory.getCurrentItem().itemID == MineFactoryReloadedCore.factoryHammerItem.shiftedIndex)
		{
			rotate(world, i, j, k);
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
	
	public void rotate(World world, int x, int y, int z)
	{
		int md = world.getBlockMetadata(x, y, z);
		if(md == 0)
		{
			int nextBlockId = world.getBlockId(x + 1, y, z);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(world, x, y, z, 4);
			}
			else
			{
				rotateTo(world, x, y, z, 1);
			}
		}
		else if(md == 4)
		{
			rotateTo(world, x, y, z, 8);
		}
		else if(md == 8)
		{
			rotateTo(world, x, y, z, 1);
		}
		
		else if(md == 1)
		{
			int nextBlockId = world.getBlockId(x, y, z + 1);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(world, x, y, z, 5);
			}
			else
			{
				rotateTo(world, x, y, z, 2);
			}
		}
		else if(md == 5)
		{
			rotateTo(world, x, y, z, 9);
		}
		else if(md == 9)
		{
			rotateTo(world, x, y, z, 2);
		}

		
		else if(md == 2)
		{
			int nextBlockId = world.getBlockId(x - 1, y, z);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(world, x, y, z, 6);
			}
			else
			{
				rotateTo(world, x, y, z, 3);
			}
		}
		else if(md == 6)
		{
			rotateTo(world, x, y, z, 10);
		}
		else if(md == 10)
		{
			rotateTo(world, x, y, z, 3);
		}
		
		
		else if(md == 3)
		{
			int nextBlockId = world.getBlockId(x, y, z - 1);
			if(Block.blocksList[nextBlockId] != null && Block.blocksList[nextBlockId].isOpaqueCube())
			{
				rotateTo(world, x, y, z, 7);
			}
			else
			{
				rotateTo(world, x, y, z, 0);
			}
		}
		else if(md == 7)
		{
			rotateTo(world, x, y, z, 11);
		}
		else if(md == 11)
		{
			rotateTo(world, x, y, z, 0);
		}
	}
	
	private void rotateTo(World world, int x, int y, int z, int newmd)
	{
		System.out.println("Currently at " + world.getBlockMetadata(x, y, z) + ", setting to " + newmd);
		world.setBlockMetadataWithNotify(x, y, z, newmd);
	}
	
	private void setEntityVelocity(Entity e, double x, double y, double z)
	{
		e.motionX = x;
		e.motionY = y;
		e.motionZ = z;
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
