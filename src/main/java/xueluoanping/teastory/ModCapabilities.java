package xueluoanping.teastory;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCapabilities {
    public static final DeferredRegister<DataComponentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, TeaStory.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> SIMPLE_FLUID=ATTACHMENT_TYPES.register(
            "fluid_data",
            ()->DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());
}
