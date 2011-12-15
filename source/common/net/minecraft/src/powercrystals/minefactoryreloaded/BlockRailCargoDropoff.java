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

public class BlockRailCargoDropoff extends BlockRail implements ITextureProvider
{
	public BlockRailCargoDropoff(int i, int j)
	{
		super(i, j, true);
		setBlockName("cargoDropoffRail");
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
		
		for(int slotIndex = 0; slotIndex < minecart.getSizeInventory(); slotIndex++)
		{
			ItemStack ourStack = minecart.getStackInSlot(slotIndex);
			if(ourStack == null)
			{
				continue;
			}
			for(InventoryAndSide chest : Util.findChests(world, x, y, z))
			{
				ItemStack stackToAdd = ourStack.copy();
				int amountRemaining = Util.addToInventory(chest, stackToAdd);
				if(amountRemaining == 0)
				{
					minecart.setInventorySlotContents(slotIndex, null);
					break;
				}
				else
				{
					ourStack.stackSize = amountRemaining;
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
