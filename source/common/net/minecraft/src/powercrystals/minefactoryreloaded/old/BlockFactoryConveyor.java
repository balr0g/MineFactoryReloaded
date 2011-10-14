package net.minecraft.src.powercrystals.minefactoryreloaded.old;

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
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

/* Metadata reference
 * 0	-X
 * 1	-Z
 * 2	+X
 * 3	+Z
 * 4	-X +Y
 * 5	+X +Y
 * 6	+Y +Z
 * 7	+Y -Z
 * 8	+X -Y
 * 9	-X -Y
 * 10	-Y -Z
 * 11	-Y +Z
 */

public class BlockFactoryConveyor extends Block implements ITextureProvider
{
	public BlockFactoryConveyor(int i, int j)
	{
		super(i, j, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 0.1F, 0.1F);
		setBlockName("conveyor");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if((entity instanceof EntityItem) || (entity instanceof EntityLiving))
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

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(j == 7 || j == 5 || j == 8 || j == 10)
		{
			return MineFactoryReloadedCore.conveyorReverseTexture;
		}
		else
		{
			return blockIndexInTexture;
		}
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return MineFactoryReloadedCore.proxy.getConveyorRenderId();
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
			world.setBlockMetadataWithNotify(i, j, k, 1);
			checkSurroundingTrack(world, i, j, k, 1);
		}
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(entityplayer.inventory.getCurrentItem() != null &&
				entityplayer.inventory.getCurrentItem().itemID == MineFactoryReloadedCore.factoryHammerItem.shiftedIndex)
		{
			int currentMetadata = world.getBlockMetadata(i, j, k);
			if(currentMetadata == 3)
			{
				world.setBlockMetadataWithNotify(i, j, k, 0);
			}
			else if(currentMetadata < 4)
			{
				world.setBlockMetadataWithNotify(i, j, k, currentMetadata + 1);
			}
			else if(currentMetadata < 8)
			{
				world.setBlockMetadataWithNotify(i, j, k, currentMetadata + 4);
			}
			else if(currentMetadata < 12)
			{
				world.setBlockMetadataWithNotify(i, j, k, currentMetadata - 4);
			}
			world.markBlockNeedsUpdate(i, j, k);
		}
		return true;
	}

	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(MineFactoryReloadedCore.proxy.isClient(world))
		{
			return;
		}
		int i1 = world.getBlockMetadata(i, j, k);
		checkSurroundingTrack(world, i, j, k, i1);
		boolean flag = false;
		if(!world.isBlockOpaqueCube(i, j - 1, k))
		{
			flag = true;
		}
		if(flag)
		{
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
			world.setBlockWithNotify(i, j, k, 0);
		}
	}

	private void checkSurroundingTrack(World world, int x, int y, int z, int ourMetadata)
	{
		if(world.getBlockId(x + 1, y + 1, z) == blockID)
		{
			int nextMetadata = world.getBlockMetadata(x + 1, y + 1, z);
			if(nextMetadata == 0 || nextMetadata == 9)
			{
				if(ourMetadata != 8 && ourMetadata != 9)
				{
					world.setBlockMetadataWithNotify(x, y, z, 9);
				}
			}
			else if(ourMetadata != 4 && ourMetadata != 5)
			{
				world.setBlockMetadataWithNotify(x, y, z, 5);
			}
		}
		else if(world.getBlockId(x - 1, y + 1, z) == blockID)
		{
			int nextMetadata = world.getBlockMetadata(x - 1, y + 1, z);
			if(nextMetadata == 2 || nextMetadata == 8)
			{
				if(ourMetadata != 8 && ourMetadata != 9)
				{
					world.setBlockMetadataWithNotify(x, y, z, 8);
				}
			}
			else if(ourMetadata != 5 && ourMetadata != 4)
			{
				world.setBlockMetadataWithNotify(x, y, z, 4);
			}
		}
		else if(world.getBlockId(x, y + 1, z - 1) == blockID)
		{
			int nextMetadata = world.getBlockMetadata(x, y + 1, z - 1);
			if(nextMetadata == 3 || nextMetadata == 11)
			{
				if(ourMetadata != 10 && ourMetadata != 11)
				{
					world.setBlockMetadataWithNotify(x, y, z, 11);
				}
			}
			else if(ourMetadata != 6 && ourMetadata != 7)
			{
				world.setBlockMetadataWithNotify(x, y, z, 7);
			}
		}
		else if(world.getBlockId(x, y + 1, z + 1) == blockID)
		{
			int nextMetadata = world.getBlockMetadata(x, y + 1, z + 1);
			if(nextMetadata == 1 || nextMetadata == 10)
			{
				if(ourMetadata != 10 && ourMetadata != 11)
				{
					world.setBlockMetadataWithNotify(x, y, z, 10);
				}
			}
			else if(ourMetadata != 7 && ourMetadata != 6)
			{
				world.setBlockMetadataWithNotify(x, y, z, 6);
			}
		}
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
