package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.Block;
import net.minecraft.src.BlockRail;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.InventoryAndSide;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class BlockRailCargoPickup extends BlockRail implements ITextureProvider
{
	public BlockRailCargoPickup(int i, int j)
	{
		super(i, j, true);
		setBlockName("cargoPickupRail");
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
		if(minecart.minecartType != 1)
		{
			return;
		}
		
		for(InventoryAndSide chest : Util.findChests(world, x, y, z))
		{
			for(int slotIndex = 0; slotIndex < chest.getInventory().getSizeInventory(); slotIndex++)
			{
				ItemStack sourceStack = chest.getInventory().getStackInSlot(slotIndex);
				if(sourceStack == null)
				{
					continue;
				}
				ItemStack stackToAdd = sourceStack.copy();
				int amountRemaining = Util.addToInventory(new InventoryAndSide(minecart, -1), stackToAdd);
				if(amountRemaining == 0)
				{
					chest.getInventory().setInventorySlotContents(slotIndex, null);
				}
				else
				{
					sourceStack.stackSize = amountRemaining;
					break;
				}
			}
		}
	}

	@Override
	public String getTextureFile()
	{
		return MineFactoryReloadedCore.terrainTexture;
	}

}
