package cloud.lemonslice.silveroak.helper;

public class GuiHelper2 {
    // public static void drawLayer(GuiGraphics matrixStack, int x, int y, ResourceLocation texture, TexturePos pos) {
    //     Minecraft.getInstance().getTextureManager().bindForSetup(texture);
    //     GuiUtils.drawTexturedModalRect(matrixStack, x, y, pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(), 0.0F);
    // }
    //
    // public static void drawLayer(GuiGraphics matrixStack, int x, int y, TexturePos pos) {
    //     GuiUtils.drawTexturedModalRect(matrixStack, x, y, pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(), 0.0F);
    // }
    //
    // public static void renderIconButton(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY, ResourceLocation texture, Button button, TexturePos normalPos, TexturePos hoveredPos, TexturePos pressedPos) {
    //     button.render(matrixStack, mouseX, mouseY, partialTicks);
    //     if (button.isActive()) {
    //         drawLayer(matrixStack, button.getX(), button.getY(), texture, pressedPos);
    //     } else if (button.isHovered()) {
    //         drawLayer(matrixStack, button.getX(), button.getY(), texture, hoveredPos);
    //     } else {
    //         drawLayer(matrixStack, button.getX(), button.getY(), texture, normalPos);
    //     }
    // }
    //
    // public static void renderButton(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY, ResourceLocation texture, Button button, TexturePos normalPos, TexturePos hoveredPos) {
    //     button.render(matrixStack, mouseX, mouseY, partialTicks);
    //     if (button.isHovered()) {
    //         drawLayer(matrixStack, button.getX(), button.getY(), texture, hoveredPos);
    //     } else {
    //         drawLayer(matrixStack, button.getX(), button.getY(), texture, normalPos);
    //     }
    //
    // }
    //
    // public static TextureAtlasSprite getBlockSprite(ResourceLocation sprite) {
    //     return Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(sprite);
    // }
    //
    //
    // public static void drawTank(AbstractContainerScreen gui, TexturePos pos, FluidStack fluid, int fluidHeight) {
    //     int width = pos.getWidth();
    //     if (fluid != null) {
    //         if (fluidHeight != 0) {
    //
    //             TextureAtlasSprite sprite = getBlockSprite(IClientFluidTypeExtensions.of(fluid.getFluid()).getStillTexture(fluid));
    //             gui.getMinecraft().getTextureManager().bindForSetup(InventoryMenu.BLOCK_ATLAS);
    //             int color = IClientFluidTypeExtensions.of(fluid.getFluid()).getTintColor(fluid);
    //             RenderSystem.setShaderColor(ColorHelper.getRedF(color), ColorHelper.getGreenF(color), ColorHelper.getBlueF(color), ColorHelper.getAlphaF(color));
    //
    //             int fluidWidth;
    //             int j;
    //             int count;
    //             for (fluidWidth = 0; fluidWidth < width / 16; ++fluidWidth) {
    //                 j = 0;
    //
    //                 for (count = fluidHeight; count > 16; ++j) {
    //                     drawFluid(pos.getX() + fluidWidth * 16, pos.getY() + pos.getHeight() - (j + 1) * 16, 0, 16, 16, sprite);
    //                     count -= 16;
    //                 }
    //
    //                 drawFluid(pos.getX() + fluidWidth * 16, pos.getY() + pos.getHeight() - j * 16 - count, 0, 16, count, sprite);
    //             }
    //
    //             fluidWidth = width % 16;
    //             j = width / 16;
    //             if (fluidWidth != 0) {
    //                 count = 0;
    //
    //                 int fluidH;
    //                 for (fluidH = fluidHeight; fluidH > 16; ++count) {
    //                     drawFluid(pos.getX() + j * 16, pos.getY() + pos.getHeight() - (count + 1) * 16, 0, fluidWidth, 16, sprite);
    //                     fluidH -= 16;
    //                 }
    //
    //                 drawFluid(pos.getX() + j * 16, pos.getY() + pos.getHeight() - count * 16 - fluidH, 0, fluidWidth, fluidH, sprite);
    //             }
    //         }
    //
    //     }
    // }
    //
    // public static void drawTransparentStringDefault(FontRenderer font, String text, float x, float y, int color, boolean shadow) {
    //     drawSpecialString(font, text, x, y, color, shadow, true, 0, 15728880);
    // }
    //
    // public static void drawSpecialString(FontRenderer font, String text, float x, float y, int color, boolean shadow, boolean transparent, int colorBackground, int packedLight) {
    //     IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
    //     font.renderString(text, x, y, color, shadow, TransformationMatrix.identity().getMatrix(), irendertypebuffer$impl, transparent, colorBackground, packedLight);
    //     irendertypebuffer$impl.finish();
    // }
    //
    // public static void drawTooltip(AbstractContainerScreen gui, GuiGraphics matrix, int mouseX, int mouseY, int x, int y, int weight, int height, List<Component> list) {
    //     if (x <= mouseX && mouseX <= x + weight && y <= mouseY && mouseY <= y + height) {
    //         matrix.renderComponentTooltip(gui.getMinecraft().font, list, mouseX, mouseY);
    //
    //     }
    //
    // }
    //
    // public static void drawFluidTooltip(AbstractContainerScreen gui, GuiGraphics matrix, int mouseX, int mouseY, int x, int y, int width, int height, Component name, int amount) {
    //     if (amount != 0) {
    //         List<Component> list = Lists.newArrayList(name);
    //         DecimalFormat df = new DecimalFormat("#,###");
    //         list.add(Component.literal(ChatFormatting.GRAY.toString() + df.format((long) amount) + " mB"));
    //         drawTooltip(gui, matrix, mouseX, mouseY, x, y, width, height, list);
    //     }
    //
    // }
    //
    // public static void drawFluid(int x0, int y0, int z, int destWidth, int destHeight, TextureAtlasSprite sprite) {
    //     int height = ((AtlasTexture.SheetData) ((Pair) ModelLoader.instance().sheetData.get(PlayerContainer.LOCATION_BLOCKS_TEXTURE)).getSecond()).height;
    //     int width = ((AtlasTexture.SheetData) ((Pair) ModelLoader.instance().sheetData.get(PlayerContainer.LOCATION_BLOCKS_TEXTURE)).getSecond()).width;
    //     innerBlit(x0, x0 + destWidth, y0, y0 + destHeight, z, sprite.getMinU(), (sprite.getMaxU() * (float) width - (float) sprite.getWidth() + (float) destWidth / 16.0F * (float) sprite.getWidth()) / (float) width, (sprite.getMinV() * (float) height + (float) sprite.getHeight() - (float) destHeight / 16.0F * (float) sprite.getHeight()) / (float) height, sprite.getMaxV());
    // }
    //
    // protected static void innerBlit(int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
    //     BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
    //     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    //     bufferbuilder.vertex((double) x0, (double) y1, (double) z).uv(u0, v1).endVertex();
    //     bufferbuilder.vertex((double) x1, (double) y1, (double) z).uv(u1, v1).endVertex();
    //     bufferbuilder.vertex((double) x1, (double) y0, (double) z).uv(u1, v0).endVertex();
    //     bufferbuilder.vertex((double) x0, (double) y0, (double) z).uv(u0, v0).endVertex();
    //     bufferbuilder.building();
    //     // RenderSystem.enableAlphaTest();
    //     WorldVertexBufferUploader.draw(bufferbuilder);
    // }
}
