package cloud.lemonslice.teastory.client.gui;


import cloud.lemonslice.teastory.blockentity.StoveTileEntity;
import cloud.lemonslice.teastory.container.StoveContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xueluoanping.teastory.TeaStory;

public class StoveGui extends AbstractContainerScreen<StoveContainer> {
    private static final String TEXTURE_PATH = "textures/gui/container/gui_stove.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TeaStory.MODID, TEXTURE_PATH);
    private StoveContainer container;

    public StoveGui(StoveContainer container, Inventory inventory, Component name) {
        super(container, inventory, name);
        this.container = container;
    }

    @Override
    public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTick);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
        int offsetX = (this.width - this.imageWidth) / 2, offsetY = (height - imageHeight) / 2;

        // blit(matrixStack, offsetX, offsetY, 0, 0, imageWidth, imageHeight);
        matrixStack.blit(TEXTURE, offsetX, offsetY, 0, 0, imageWidth, imageHeight);

        int fuelTicks = ((StoveTileEntity) this.container.getTileEntity()).getFuelTicks();
        int remainTicks = ((StoveTileEntity) this.container.getTileEntity()).getRemainTicks();
        int textureHeight = fuelTicks == 0 ? 0 : (int) Math.ceil(14.0F * remainTicks / fuelTicks);

        // blit(matrixStack, offsetX + 81, offsetY + 16 + 14 - textureHeight, 176, 14 - textureHeight, 14, textureHeight);
        matrixStack.blit(TEXTURE, offsetX + 81, offsetY + 16 + 14 - textureHeight, 176, 14 - textureHeight, 14, textureHeight);

    }

    @Override
    protected void renderLabels(GuiGraphics matrixStack, int mouseX, int mouseY) {
        matrixStack.drawString(this.font, this.title.getString(), (int) (this.imageWidth / 2 - this.font.width(this.title.getString()) / 2), (int) 6.0F, 4210752);
        matrixStack.drawString(this.font, this.playerInventoryTitle.getString(), (int) 8.0F, (int) (this.imageHeight - 96 + 2), 4210752);
    }
}
