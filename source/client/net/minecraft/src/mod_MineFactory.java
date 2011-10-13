package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.powercrystals.minefactoryreloaded.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.IFactoryPlantable;
import net.minecraft.src.powercrystals.minefactoryreloaded.IFactoryRanchable;
import net.minecraft.src.powercrystals.minefactoryreloaded.IMFRProxy;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class mod_MineFactory extends BaseModMp
{
	public static int fertilizerRenderId = 1000;
	public static int conveyorRenderId = 1001;
	
	public mod_MineFactory()
	{
		MineFactoryReloadedCore.Init(new ClientProxy());
		
		ModLoader.AddName(MineFactoryReloadedCore.conveyorBlock, "Conveyor Belt Block");
		ModLoader.AddName(MineFactoryReloadedCore.fertilizerBlock, "Fertilizer Block");
		ModLoader.AddName(MineFactoryReloadedCore.harvesterBlock, "Harvester");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.rancherBlock, 1, 0), "Rancher");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.rancherBlock, 1, 4), "Veterinary Clinic");
		ModLoader.AddName(MineFactoryReloadedCore.planterBlock, "Planter");
		ModLoader.AddName(MineFactoryReloadedCore.fisherBlock, "Fisher");
		ModLoader.AddName(MineFactoryReloadedCore.passengerRailPickupBlock, "Passenger Pickup Rail");
		ModLoader.AddName(MineFactoryReloadedCore.passengerRailDropoffBlock, "Passenger Dropoff Rail");
		ModLoader.AddName(MineFactoryReloadedCore.cargoRailDropoffBlock, "Cargo Dropoff Rail");
		ModLoader.AddName(MineFactoryReloadedCore.cargoRailPickupBlock, "Cargo Pickup Rail");
		
		ModLoader.AddName(MineFactoryReloadedCore.fertilizerItem, "Fertilizer");
		ModLoader.AddName(MineFactoryReloadedCore.steelIngotItem, "Steel Ingot");
		ModLoader.AddName(MineFactoryReloadedCore.factoryHammerItem, "Factory Hammer");
		
		fertilizerRenderId = ModLoader.getUniqueBlockModelID(this, false);
		conveyorRenderId = ModLoader.getUniqueBlockModelID(this, false);
	}

	@Override
	public void ModsLoaded()
	{
		ModLoaderMp.Init();
		MinecraftForgeClient.preloadTexture(MineFactoryReloadedCore.terrainTexture);
		MinecraftForgeClient.preloadTexture(MineFactoryReloadedCore.itemTexture);
	}
	
	public String Version()
	{
		return "1.8.1R1.2.2";
	}

	public void RegisterAnimation(Minecraft minecraft)
	{
		if(MineFactoryReloadedCore.AnimateBlockFaces)
		{
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.conveyorNormalTexture, "/MineFactorySprites/animations/ConveyorNormal.png", MineFactoryReloadedCore.AnimationTileSize));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.conveyorReverseTexture, "/MineFactorySprites/animations/ConveyorReversed.png", MineFactoryReloadedCore.AnimationTileSize));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.harvesterAnimatedTexture, "/MineFactorySprites/animations/Harvester.png", MineFactoryReloadedCore.AnimationTileSize));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.rancherAnimatedTexture, "/MineFactorySprites/animations/Rancher.png", MineFactoryReloadedCore.AnimationTileSize));
		}
	}

	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int renderId)
	{
		if(renderId == fertilizerRenderId)
		{
			int ourMetadata = iblockaccess.getBlockMetadata(i, j, k);
			if(ourMetadata == 0 || ourMetadata == 5)
			{
				block.setBlockBounds(0.6F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.3F, 0.2F, 0.2F, 0.6F, 0.8F, 0.8F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.4F, 0.4F, 0.3F, 0.6F, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			else if(ourMetadata == 1 || ourMetadata == 6)
			{
				block.setBlockBounds(0.0F, 0.0F, 0.6F, 1.0F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.2F, 0.2F, 0.3F, 0.8F, 0.8F, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.3F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			else if(ourMetadata == 2 || ourMetadata == 7)
			{
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.4F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.4F, 0.2F, 0.2F, 0.7F, 0.8F, 0.8F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.7F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			else if(ourMetadata == 3 || ourMetadata == 8)
			{
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.4F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.2F, 0.2F, 0.4F, 0.8F, 0.8F, 0.7F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.4F, 0.4F, 0.7F, 0.6F, 0.6F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			else if(ourMetadata == 4 || ourMetadata == 9)
			{
				block.setBlockBounds(0.0F, 0.6F, 0.0F, 1.0F, 1.0F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.2F, 0.3F, 0.2F, 0.8F, 0.6F, 0.8F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.4F, 0.0F, 0.4F, 0.6F, 0.3F, 0.6F);
				renderblocks.renderStandardBlock(block, i, j, k);
				block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			return true;
		}
		if(renderId == conveyorRenderId)
		{
			Tessellator tessellator = Tessellator.instance;
			int conveyorMetadata = iblockaccess.getBlockMetadata(i, j, k);
			int conveyorTextureIndex = block.getBlockTextureFromSideAndMetadata(0, conveyorMetadata);
			float f = block.getBlockBrightness(iblockaccess, i, j, k);
			tessellator.setColorOpaque_F(f, f, f);
			int l1 = (conveyorTextureIndex & 0xf) << 4;
			int i2 = conveyorTextureIndex & 0xf0;
			double d = (float)l1 / 256F;
			double d1 = ((float)l1 + 15.99F) / 256F;
			double d2 = (float)i2 / 256F;
			double d3 = ((float)i2 + 15.99F) / 256F;
			float f1 = 0.0625F;
			float f2 = i + 1;
			float f3 = i + 1;
			float f4 = i + 0;
			float f5 = i + 0;
			float f6 = k + 0;
			float f7 = k + 1;
			float f8 = k + 1;
			float f9 = k + 0;
			float f10 = (float)j + f1;
			float f11 = (float)j + f1;
			float f12 = (float)j + f1;
			float f13 = (float)j + f1;
			if(conveyorMetadata == 0 || conveyorMetadata == 4 || conveyorMetadata == 5 || conveyorMetadata == 8 || conveyorMetadata == 9)
			{
				f2 = f5 = i + 1;
				f3 = f4 = i + 0;
				f6 = f7 = k + 1;
				f8 = f9 = k + 0;
			}
			else if(conveyorMetadata == 1)
			{
				f2 = f3 = i + 0;
				f4 = f5 = i + 1;
				f6 = f9 = k + 1;
				f7 = f8 = k + 0;
			}
			else if(conveyorMetadata == 2)
			{
				f2 = f5 = i + 0;
				f3 = f4 = i + 1;
				f6 = f7 = k + 0;
				f8 = f9 = k + 1;
			}
			else if(conveyorMetadata == 3)
			{
				f2 = f3 = i + 1;
				f4 = f5 = i + 0;
				f6 = f9 = k + 0;
				f7 = f8 = k + 1;
			}
			if(conveyorMetadata == 4 || conveyorMetadata == 6 || conveyorMetadata == 8 || conveyorMetadata == 10)
			{
				f11++;
				f12++;
			}
			else if(conveyorMetadata == 5 || conveyorMetadata == 7 || conveyorMetadata == 9 || conveyorMetadata == 11)
			{
				f10++;
				f13++;
			}
			tessellator.addVertexWithUV(f2, f10, f6, d1, d2);
			tessellator.addVertexWithUV(f3, f11, f7, d1, d3);
			tessellator.addVertexWithUV(f4, f12, f8, d, d3);
			tessellator.addVertexWithUV(f5, f13, f9, d, d2);
			tessellator.addVertexWithUV(f5, f13, f9, d, d2);
			tessellator.addVertexWithUV(f4, f12, f8, d, d3);
			tessellator.addVertexWithUV(f3, f11, f7, d1, d3);
			tessellator.addVertexWithUV(f2, f10, f6, d1, d2);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		MineFactoryReloadedCore.registerPlantable(plantable);
	}
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		MineFactoryReloadedCore.registerHarvestable(harvestable);
	}
	
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		MineFactoryReloadedCore.registerFertilizable(fertilizable);
	}
	
	public static void registerFertilizerItem(int itemId)
	{
		MineFactoryReloadedCore.registerFertilizerItem(itemId);
	}
	
	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		MineFactoryReloadedCore.registerRanchable(ranchable);
	}
	
	public class ClientProxy implements IMFRProxy
	{

		@Override
		public boolean isClient(World world)
		{
			return world.multiplayerWorld;
		}

		@Override
		public boolean isServer()
		{
			return false;
		}

		@Override
		public void movePlayerToCoordinates(EntityPlayer e, double x, double y,	double z)
		{
			e.setPosition(x, y, z);
		}

		@Override
		public int getBlockDamageDropped(Block block, int metadata)
		{
			return block.damageDropped(metadata);
		}

		@Override
		public int getFertilizerRenderId()
		{
			return fertilizerRenderId;
		}

		@Override
		public int getConveyorRenderId()
		{
			return conveyorRenderId;
		}

		@Override
		public boolean fertilizeGiantMushroom(World world, int x, int y, int z)
		{
			int blockId = world.getBlockId(x, y, z);
			return ((BlockMushroom)Block.blocksList[blockId]).func_35293_c(world, x, y, z, world.rand);
		}

		@Override
		public void fertilizeStemPlant(World world, int x, int y, int z)
		{
			int blockId = world.getBlockId(x, y, z);
			((BlockStem)Block.blocksList[blockId]).func_35294_i(world, x, y, z);
		}
		
	}
}
