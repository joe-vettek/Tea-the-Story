package cloud.lemonslice.teastory.client.gui;


import cloud.lemonslice.teastory.blockentity.StoneRollerTileEntity;
import cloud.lemonslice.teastory.container.StoneRollerContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import xueluoanping.teastory.TeaStory;


public class StoneRollerGui extends AbstractContainerScreen<StoneRollerContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_stone_roller.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TeaStory.MODID, TEXTURE_PATH);

    private StoneRollerContainer container;

    public StoneRollerGui(StoneRollerContainer container, Inventory inv, Component name)
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
        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        // RenderSystem.enableAlphaTest();
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
        // blit(matrixStack, offsetX, offsetY, 0, 0, imageWidth, imageHeight);
        matrixStack.blit(TEXTURE, offsetX, offsetY, 0, 0, imageWidth, imageHeight);

        int totalTicks = 0;
        if (((StoneRollerTileEntity)this.container.getTileEntity()).getCurrentRecipe() != null)
        {
            totalTicks = ((StoneRollerTileEntity)this.container.getTileEntity()).getCurrentRecipe().getWorkTime();
        }
        int processTicks =  ((StoneRollerTileEntity)this.container.getTileEntity()).getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(22.0 * processTicks / totalTicks);
        }
        // blit(matrixStack, offsetX + 77, offsetY + 37, 176, 0, textureWidth, 16);
        matrixStack.blit(TEXTURE, offsetX + 77, offsetY + 37, 176, 0, textureWidth, 16);

        // RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        this.container.broadcastChanges();

    }

    @Override
    protected void renderLabels(GuiGraphics  matrixStack, int mouseX, int mouseY)
    {
        matrixStack.drawString(this.font, this.title.getString(), (int) ((this.imageWidth - this.font.width(this.title.getString())) / 2.0F), (int) 8.0F, 0x262626);
        matrixStack.drawString(this.font, this.playerInventoryTitle.getString(), (int) 8.0F, (int) (this.imageHeight - 95), 0x262626);
    }
}
