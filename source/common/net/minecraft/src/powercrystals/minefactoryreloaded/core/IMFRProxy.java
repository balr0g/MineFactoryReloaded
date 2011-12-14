package net.minecraft.src.powercrystals.minefactoryreloaded.core;

import java.util.List;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentData;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet230ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public interface IMFRProxy
{
	public boolean isClient(World world);
	public boolean isServer();
	
	public void movePlayerToCoordinates(EntityPlayer e, double x, double y, double z);
	
	public int getBlockDamageDropped(Block block, int metadata);
	
	public int getRenderId();
	
	public boolean fertilizeGiantMushroom(World world, int x, int y, int z);
	public void fertilizeStemPlant(World world, int x, int y, int z);
	
	public String getConfigPath();
	
	public Packet getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString);
	
	public void sendPacketToAll(Packet230ModLoader p);
	
	public int calcItemStackEnchantability(Random random, int i, int j, ItemStack itemstack);
	public List<?> buildEnchantmentList(Random random, ItemStack itemstack, int i);
	public Enchantment getEnchantment(EnchantmentData ed);
	public int getLevel(EnchantmentData ed);
	public void applyEnchantment(EnchantmentData ed, ItemStack stack);
	
	public void setFieldA(EntityAnimal animal, int value);
	public int getAnimalMethodG(EntityAnimal animal);
	public void setEntityToAttack(EntityCreature entity, Entity target);
}
