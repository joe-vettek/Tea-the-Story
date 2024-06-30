package xueluoanping.teastory.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import xueluoanping.teastory.FluidRegister;

import java.util.function.Consumer;

public class TeaFluidType extends FluidType {

    private final ResourceLocation STILL_TEXTURE;
    private final ResourceLocation FLOWING_TEXTURE;
    private int colourTint;

    public TeaFluidType(Properties properties) {
        this(properties, FluidRegister.WATER_STILL_TEXTURE, FluidRegister.WATER_FLOW_TEXTURE);
    }

    public TeaFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties);
        this.STILL_TEXTURE = stillTexture;
        this.FLOWING_TEXTURE = flowingTexture;
    }


    public TeaFluidType color(int colourTint) {
        this.colourTint = colourTint;
        return this;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }


            @Override
            public int getTintColor() {
                // return colourTint > 0 ? colourTint : IClientFluidTypeExtensions.super.getTintColor();
                return colourTint;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                int color = this.getTintColor();
                var Red = ((float) (((color >> 16) & 255) / 255.0));
                var Green = ((float) (((color >> 8) & 255) / 255.0));
                var Blue = ((float) ((color & 255) / 255.0));
                return new Vector3f(Red, Green, Blue);

            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                // RenderSystem.setShaderFogShape(FogShape.CYLINDER);
                int color = this.getTintColor();
                var alpha = ((float) (((color >> 24) & 255) / 255.0));
                RenderSystem.setShaderFogStart(0.125F);
                RenderSystem.setShaderFogEnd(2.0F+3.0F*(1-alpha));
            }
        });
    }
}
