package xueluoanping.teastory;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContent {
    @SubscribeEvent
    public static void creativeModeTabRegister(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(new ResourceLocation(TeaStory.MODID, TeaStory.MODID),
                    CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegister.TEA_LEAVES.get()))
                            .title(Component.translatable("itemGroup." + TeaStory.MODID + ".core"))
                            .displayItems((params, output) -> {
                                BlockRegister.ModItems.getEntries().forEach((reg) -> {
                                    output.accept(new ItemStack(reg.get()));
                                });
                                ItemRegister.ModItems.getEntries().forEach((reg) -> {
                                    output.accept(new ItemStack(reg.get()));
                                });
                                FluidRegistry.ITEMS.getEntries().forEach((reg) -> {
                                    output.accept(new ItemStack(reg.get()));
                                });
                                TileEntityTypeRegistry.ModItems.getEntries().forEach((reg) -> {
                                    output.accept(new ItemStack(reg.get()));
                                });
                                ItemRegister.PORCELAIN_CUP_DRINK.get().fillItemGroup(output);
                                ItemRegister.BOTTLE_DRINK.get().fillItemGroup(output);
                                TileEntityTypeRegistry.IRON_KETTLE_ITEM.get().fillItemGroup(output);
                                TileEntityTypeRegistry.PORCELAIN_TEAPOT.get().fillItemGroup(output);

                                BlockRegister.CHRYSANTHEMUM.get().fillItemGroup(output);
                                BlockRegister.HYACINTH.get().fillItemGroup(output);
                                BlockRegister.ZINNIA.get().fillItemGroup(output);
                            })
                            .build());
        });
    }
}
