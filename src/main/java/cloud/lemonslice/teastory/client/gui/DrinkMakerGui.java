package cloud.lemonslice.teastory.client.gui;


import cloud.lemonslice.teastory.container.DrinkMakerContainer;
import cloud.lemonslice.teastory.recipe.drink.DrinkRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import xueluoanping.teastory.TeaStory;

import java.util.List;


public class DrinkMakerGui extends AbstractContainerScreen<DrinkMakerContainer> {
    private static final String TEXTURE_PATH = "textures/gui/container/gui_drink_maker.png";
    private static final ResourceLocation TEXTURE = TeaStory.rl( TEXTURE_PATH);

    private static final int QUESTION_X = 83;
    private static final int QUESTION_Y = 16;
    private static final int EXCLAMATION_X = 96;
    private static final int EXCLAMATION_Y = 54;

    private final DrinkMakerContainer container;
    private boolean isEnough = true;


    public DrinkMakerGui(DrinkMakerContainer container, Inventory inventory, Component name) {
        super(container, inventory, name);
        this.container = container;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {

        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;
        //
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        // // RenderSystem.enableAlphaTest();
        // minecraft.getTextureManager().bindForSetup(TEXTURE);
        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE);
        guiGraphics.blit(TEXTURE, offsetX, offsetY, 0, 0, imageWidth, imageHeight);

        // GuiHelper.drawLayer(guiGraphics, offsetX + QUESTION_X, offsetY + QUESTION_Y, TEXTURE, new TexturePos(176, 94, 11, 11));
        //
        // if (this.container.getTileEntity().getCurrentRecipe() == null || !isEnough) {
        //     GuiHelper.drawLayer(guiGraphics, offsetX + EXCLAMATION_X, offsetY + EXCLAMATION_Y, TEXTURE, new TexturePos(187, 94, 11, 11));
        // }


        int totalTicks = this.container.getTileEntity().getTotalTicks();
        int processTicks = this.container.getTileEntity().getProcessTicks();
        int textureWidth = 0;
        if (totalTicks != 0) {
            textureWidth = (int) Math.ceil(22.0 * processTicks / totalTicks);
        }
        guiGraphics.blit(TEXTURE, offsetX + 99, offsetY + 37, 176, 0, textureWidth, 16);

        // float per=container.getTileEntity().getProcessTicks()*1.0f/container.getTileEntity().getTotalTicks();
        // TeaStory.logger(per);
        // guiGraphics.blit(TEXTURE,offsetX+99, offsetY+37, 176, 0, Mth.ceil(20 * per), 16);
        container.getTileEntity().getFluidHandler().ifPresent(f ->
        {
            int capacity = f.getTankCapacity(0);
            int height;
            if (capacity != 0) {
                height = (int) Math.ceil(64.0 * this.container.getTileEntity().getFluidAmount() / capacity);
                // GuiHelper.drawLayer(guiGraphics, offsetX + 126, offsetY + 10, new TexturePos(176, 17, 20, 68));
            } else {
                height = 0;
            }
            var fs = f.getFluidInTank(0);

            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            if (!fs.isEmpty()) {
                RenderUtil.renderFluidStackInGUI(guiGraphics.pose().last().pose(), fs, 16, (int) (64*(height/64f)), offsetX + 128, offsetY + 12 + 64);
            }
            poseStack.popPose();
            // GuiHelper.drawTank(this, new TexturePos(), , height);

        });

        // RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        // this.container.detectAndSendChanges();
    }

    @Override
    public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTick) {
        // this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTick);
        renderTooltip(matrixStack, mouseX, mouseY);
        // super.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics matrixStack, int mouseX, int mouseY) {
        DrinkRecipe recipe = container.getTileEntity().getCurrentRecipe();
        if (!isEnough) isEnough = true;
        if (recipe != null) {
            int n = container.getTileEntity().getNeededAmount();
            for (int i = 0; i < 4; i++) {
                Slot slot = this.container.slots.get(i);
                ItemStack itemStack = slot.getItem();
                if (!itemStack.isEmpty() && itemStack.getCount() < n) {
                    renderSlotWarning(matrixStack, slot.x, slot.y);
                    isEnough = false;
                }
            }
        }

        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;
        matrixStack.drawString(this.font, this.title.getString(), this.imageWidth / 3.0F - this.font.width(this.title.getString()) / 2.0F - 1, 8.0F, 0xdec674, false);
        matrixStack.drawString(this.font, this.playerInventoryTitle.getString(), 8.0F, (float) (this.imageHeight - 95), 0xdec674, false);
        if (offsetX + QUESTION_X <= mouseX && mouseX <= offsetX + QUESTION_X + 11 && offsetY + QUESTION_Y <= mouseY && mouseY <= offsetY + QUESTION_Y + 11) {
            Component ingredient = Component.translatable("info.teastory.tooltip.drink_maker.help.1");
            Component residue = Component.translatable("info.teastory.tooltip.drink_maker.help.2");

            // GuiHelper.drawTransparentStringDefault(this.font, ingredient.getString(), this.imageWidth / 3.0F - this.font.width(ingredient.getString()) / 2.0F - 1, 28, 0xbfdec674, true);
            // GuiHelper.drawTransparentStringDefault(this.font, residue.getString(), this.imageWidth / 3.0F - this.font.width(residue.getString()) / 2.0F - 1, 55, 0xbfdec674, true);
        }
    }

    private void renderSlotWarning(GuiGraphics matrixStack, int x, int y) {
        matrixStack.fillGradient(x, y, x + 16, y + 16, 0x9fd64f44, 0x9fd64f44);
    }


    @Override
    protected void renderTooltip(GuiGraphics matrixStack, int mouseX, int mouseY) {
        super.renderTooltip(matrixStack, mouseX, mouseY);
        int offsetX = (width - imageWidth) / 2, offsetY = (height - imageHeight) / 2;

        if (offsetX + 128 < mouseX && mouseX < offsetX + 128 + 16
                && offsetY + 12 < mouseY && mouseY < offsetY + 12 + 64)
            matrixStack.renderComponentTooltip(this.font, List.of(this.container.getTileEntity().getFluidTranslation()), mouseX, mouseY);


        // GuiHelper.drawFluidTooltip(matrixStack, mouseX, mouseY, offsetX + 128, offsetY + 12, 16, 64, this.container.getTileEntity().getFluidTranslation(), this.container.getTileEntity().getFluidAmount());

        // Component warn_1 = Component.translatable("info.teastory.tooltip.drink_maker.warn.1");
        // Component warn_2 = Component.translatable("info.teastory.tooltip.drink_maker.warn.2");
        //
        // if (this.container.getTileEntity().getCurrentRecipe() == null) {
        //     GuiHelper.drawTooltip(matrixStack, mouseX, mouseY, offsetX + EXCLAMATION_X, offsetY + EXCLAMATION_Y, 11, 11, Collections.singletonList(warn_1));
        // } else if (!isEnough) {
        //     GuiHelper.drawTooltip(matrixStack, mouseX, mouseY, offsetX + EXCLAMATION_X, offsetY + EXCLAMATION_Y, 11, 11, Collections.singletonList(warn_2));
        // }
    }


}
