package xueluoanping.teastory;

import cloud.lemonslice.teastory.entity.SeatEntity;
import net.minecraft.world.entity.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.teastory.entity.ScarecrowEntity;


public final class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TeaStory.MODID);
    public static RegistryObject<EntityType<Entity>> SEAT_TYPE = ENTITY_TYPE_DEFERRED_REGISTER.register("seat",
            () -> EntityType.Builder.of((type, world) -> new SeatEntity(world), MobCategory.MISC).sized(0F, 0F).build("seat"));

    public static RegistryObject<EntityType<ScarecrowEntity>> SCARECROW_TYPE =ENTITY_TYPE_DEFERRED_REGISTER.register("scarecrow",
            () -> EntityType.Builder.<ScarecrowEntity>of(ScarecrowEntity::new, MobCategory.MISC).sized(0.1F, 0.1F).clientTrackingRange(10).build("scarecrow"));

}