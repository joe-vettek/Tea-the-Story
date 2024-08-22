package xueluoanping.teastory.handler;

import cloud.lemonslice.teastory.tag.NormalTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.entity.ScarecrowEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventsHandlers {
    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == ItemRegister.RICE_BALL.get()) {
            // event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }


    @SubscribeEvent
    public static void onMobSpawnEvent(MobSpawnEvent.FinalizeSpawn event) {
        if (
                event.getEntity().getType().is(NormalTags.Entities.BIRDS)
                && event.getEntity() instanceof PathfinderMob pathfinderMob) {

            // TeaStory.logger(event.getEntity());
            pathfinderMob.goalSelector.addGoal(0, new AvoidEntityGoal<>(
                    pathfinderMob, ScarecrowEntity.class, 6.0F, 1.0D, 1.2D
            ));
            // TeaStory.logger(event.getEntity().goalSelector);
            // event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }
}
