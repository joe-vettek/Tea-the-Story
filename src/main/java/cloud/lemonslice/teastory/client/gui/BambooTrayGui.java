package cloud.lemonslice.teastory.client.gui;


import cloud.lemonslice.teastory.blockentity.BambooTrayTileEntity;
import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.container.BambooTrayContainer;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xueluoanping.teastory.TeaStory;

import java.util.List;

public class BambooTrayGui extends AbstractContainerScreen<BambooTrayContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_bamboo_tray.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TeaStory.MODID, TEXTURE_PATH);
    private BambooTrayContainer container;

    public BambooTrayGui(BambooTrayContainer container, Inventory inv, Component name)
    {
        super(container, inv, name);
        this.container = container;
    }

    @Override
    public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTick)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTick);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void  renderBg(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);

        // this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;

        // blit(matrixStack, offsetX, offsetY, 0, 0, imageWidth, imageHeight);
        matrixStack.blit(TEXTURE, offsetX, offsetY, 0, 0, imageWidth, imageHeight);

        // blit(matrixStack, offsetX + 51, offsetY + 29, 176, 107, 20, 20);
        matrixStack.blit(TEXTURE, offsetX + 51, offsetY + 29, 176, 107, 20, 20);

        int totalTicks = ((BambooTrayTileEntity)container.getTileEntity()).getTotalTicks();
        int processTicks =((BambooTrayTileEntity)container.getTileEntity()).getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil((double) (24 * processTicks) / totalTicks);
        }
        // blit(matrixStack, offsetX + 76, offsetY + 31, 176, 0, textureWidth, 17);
        matrixStack.blit(TEXTURE, offsetX + 76, offsetY + 31, 176, 0, textureWidth, 17);

        int id = ((BambooTrayTileEntity)container.getTileEntity()).getMode().ordinal();
        // blit(matrixStack, offsetX + 52, offsetY + 30, 176, 17 + id * 18, 18, 18);
        matrixStack.blit(TEXTURE, offsetX + 52, offsetY + 30, 176, 17 + id * 18, 18, 18);

    }

    @Override
    protected void renderLabels(GuiGraphics matrixStack, int mouseX, int mouseY)
    {

        matrixStack.drawString(this.font, this.title.getString(), (int) ((this.imageWidth - this.font.width(this.title.getString())) / 2.0F), (int) 6.0F, 4210752);
        matrixStack.drawString(this.font, this.playerInventoryTitle.getString(), (int) 8.0F, (int) (this.imageHeight - 96 + 2), 4210752);
    }


    @Override
    protected void renderTooltip(GuiGraphics matrixStack, int mouseX, int mouseY)
    {
        super.renderTooltip(matrixStack, mouseX, mouseY);
        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;
        if (offsetX + 52 < mouseX && mouseX < offsetX + 70 && offsetY + 30 < mouseY && mouseY < offsetY + 48)
        {
            matrixStack.renderComponentTooltip(this.font, List.of(((BambooTrayTileEntity)container.getTileEntity()).getMode().getTranslationKey()), mouseX, mouseY);
        }
    }
}
