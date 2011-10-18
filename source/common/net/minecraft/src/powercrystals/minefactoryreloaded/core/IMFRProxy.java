package net.minecraft.src.powercrystals.minefactoryreloaded.core;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet230ModLoader;
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
	
	public void sendPacketToAll(Packet230ModLoader packet);
}
