package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.minecraft.src.RenderEngine;
import net.minecraft.src.TextureFX;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class TextureFrameAnimFX extends TextureFX
{

    public TextureFrameAnimFX(int indexToReplace, String filePath, int tileSize)
    {
        super(indexToReplace);
        this.tileResolution = tileSize;
        tick = 0;
        this.tileSize = 1;
        numFrames = 0;
        srcSize = 0;
        try
        {
            BufferedImage bufferedimage = ImageIO.read((net.minecraft.client.Minecraft.class).getResource(filePath));
            srcSize = bufferedimage.getHeight();
            fileBuffer = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
            numFrames = bufferedimage.getWidth() / bufferedimage.getHeight();
            bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), fileBuffer, 0, bufferedimage.getWidth());
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void onTick()
    {
        if(srcSize == 0)
        {
            return;
        }
        tick++;
        tick %= numFrames;
        for(int i = 0; i < tileResolution; i++)
        {
            int j = i * srcSize * numFrames;
            for(int k = 0; k < tileResolution; k++)
            {
                int l = srcSize * tick + k;
                int i1 = fileBuffer[j + l];
                int j1 = i * tileResolution + k;
                int k1 = i1 >> 0 & 0xff;
                int l1 = i1 >> 8 & 0xff;
                int i2 = i1 >> 16 & 0xff;
                int j2 = i1 >> 24 & 0xff;
                if(anaglyphEnabled)
                {
                    int k2 = (i2 * 30 + l1 * 59 + k1 * 11) / 100;
                    int l2 = (i2 * 30 + l1 * 70) / 100;
                    int i3 = (i2 * 30 + k1 * 70) / 100;
                    i2 = k2;
                    l1 = l2;
                    k1 = i3;
                }
                imageData[j1 * 4 + 0] = (byte)i2;
                imageData[j1 * 4 + 1] = (byte)l1;
                imageData[j1 * 4 + 2] = (byte)k1;
                imageData[j1 * 4 + 3] = (byte)j2;
            }
        }
    }

    public void bindImage(RenderEngine renderengine)
    {
        if(tileImage == 0)
        {
        	MinecraftForgeClient.bindTexture(MineFactoryReloadedCore.terrainTexture);
        }
        else if(tileImage == 1)
        {
        	MinecraftForgeClient.bindTexture(MineFactoryReloadedCore.itemTexture);
        }
    }

    protected int fileBuffer[];
    private int tick;
    private int srcSize;
    private int numFrames;
    private int tileResolution;
}
