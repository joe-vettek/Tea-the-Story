package cloud.lemonslice.teastory.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TeaFluidType extends FluidType {

    private final ResourceLocation STILL_TEXTURE;
    private final ResourceLocation FLOWING_TEXTURE;
    private ResourceLocation overlayTexture;
    private int colourTint;

    public TeaFluidType(Properties properties) {
        this(properties, FluidRegistry.WATER_STILL_TEXTURE, FluidRegistry.WATER_FLOW_TEXTURE);
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
                return colourTint > 0 ? colourTint : IClientFluidTypeExtensions.super.getTintColor();
            }
        });
    }
}
