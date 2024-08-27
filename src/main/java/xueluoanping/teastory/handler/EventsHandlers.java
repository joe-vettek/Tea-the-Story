package xueluoanping.teastory.handler;

import xueluoanping.teastory.tag.NormalTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import xueluoanping.teastory.ItemRegister;
import xueluoanping.teastory.entity.ScarecrowEntity;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class EventsHandlers {
    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == ItemRegister.RICE_BALL.get()) {
            // event.getToolTip().add(Component.translatable("info.teastory.tooltip.rice"));
        }
    }


    @SubscribeEvent
    public static void onMobSpawnEvent(EntityJoinLevelEvent event) {
        if (
                !event.loadedFromDisk()
                        && event.getEntity().getType().is(NormalTags.Entities.BIRDS)
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
