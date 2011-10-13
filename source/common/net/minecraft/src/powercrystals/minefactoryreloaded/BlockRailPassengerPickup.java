package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockRail;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class BlockRailPassengerPickup extends BlockRail implements ITextureProvider
{
	public BlockRailPassengerPickup(int blockId, int textureIndex)
	{
		super(blockId, textureIndex, true);
		setBlockName("passengerPickupRail");
		setHardness(0.5F);
		setStepSound(Block.soundMetalFootstep);
	}

	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(MineFactoryReloadedCore.proxy.isClient(world) || !(entity instanceof EntityMinecart))
		{
			return;
		}
		EntityMinecart minecart = (EntityMinecart)entity;
		if(minecart.minecartType != 0 || minecart.riddenByEntity != null)
		{
			return;
		}
		
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
				x - MineFactoryReloadedCore.PassengerRailMaxHorizontal,
				y - MineFactoryReloadedCore.PassengerRailMaxVertical,
				z - MineFactoryReloadedCore.PassengerRailMaxHorizontal,
				x + MineFactoryReloadedCore.PassengerRailMaxHorizontal + 1,
				y + MineFactoryReloadedCore.PassengerRailMaxVertical + 1,
				z + MineFactoryReloadedCore.PassengerRailMaxHorizontal + 1);
		
		@SuppressWarnings("rawtypes")
		List entities = world.getEntitiesWithinAABB(EntityPlayer.class, bb);
		
		for(Object o : entities)
		{
			if(!(o instanceof EntityPlayer))
			{
				continue;
			}
			((EntityPlayer)o).mountEntity(minecart);
			return;
		}
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}
}
