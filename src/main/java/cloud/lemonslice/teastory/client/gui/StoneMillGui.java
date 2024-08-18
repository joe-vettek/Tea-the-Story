package cloud.lemonslice.teastory.client.gui;

import cloud.lemonslice.silveroak.client.texture.TexturePos;
import cloud.lemonslice.teastory.blockentity.StoneMillTileEntity;
import cloud.lemonslice.teastory.container.DrinkMakerContainer;
import cloud.lemonslice.teastory.container.StoneMillContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import xueluoanping.teastory.TeaStory;

import java.util.List;


public class StoneMillGui extends AbstractContainerScreen<StoneMillContainer>
{
    private static final String TEXTURE_PATH = "textures/gui/container/gui_stone_mill.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TeaStory.MODID, TEXTURE_PATH);

    private StoneMillContainer container;

    public StoneMillGui(StoneMillContainer container, Inventory inv, Component name)
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
    protected void renderBg(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        // RenderSystem.enableAlphaTest();
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
        // blit(matrixStack, offsetX, offsetY, 0, 0, imageWidth, imageHeight);
        matrixStack.blit(TEXTURE, offsetX, offsetY, 0, 0, imageWidth, imageHeight);

        int totalTicks = 0;
        if (((StoneMillTileEntity)this.container.getTileEntity()).getCurrentRecipe() != null)
        {
            totalTicks = ((StoneMillTileEntity)this.container.getTileEntity()).getCurrentRecipe().getWorkTime();
        }
        int processTicks =((StoneMillTileEntity)this.container.getTileEntity()).getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0)
        {
            textureWidth = (int) Math.ceil(22.0 * processTicks / totalTicks);
        }
        // blit(matrixStack, offsetX + 95, offsetY + 37, 176, 0, textureWidth, 16);
        matrixStack.blit(TEXTURE, offsetX, offsetY, offsetX + 95, offsetY + 37, 176, 0, textureWidth, 16);

        container.getTileEntity().getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(fluidHandler ->
        {
            int capacity = fluidHandler.getTankCapacity(0);
            int height = 0;
            if (capacity != 0)
            {
                height = (int) Math.ceil(48.0 * fluidHandler.getFluidInTank(0).getAmount() / capacity);
            }
            // GuiHelper.drawTank(this, new TexturePos(offsetX + 37, offsetY + 22, 16, 48), fluidHandler.getFluidInTank(0), height);
            PoseStack poseStack = matrixStack.pose();
            poseStack.pushPose();
            var fs=fluidHandler.getFluidInTank(0);
            if (!fs.isEmpty())
                RenderUtil.renderFluidStackInGUI(matrixStack.pose().last().pose(), fs, 16, 48, offsetX + 128, offsetY + 22 + height);
            poseStack.popPose();
        });
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

    @Override
    protected void renderTooltip(GuiGraphics matrixStack, int mouseX, int mouseY)
    {
        super.renderTooltip(matrixStack, mouseX, mouseY);
        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;

        matrixStack.renderComponentTooltip(this.font, List.of(((StoneMillTileEntity)this.container.getTileEntity()).getFluidTank().getFluid().getDisplayName()), mouseX, mouseY);

    }
}
