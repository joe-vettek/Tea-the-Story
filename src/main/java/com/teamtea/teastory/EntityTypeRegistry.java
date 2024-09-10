package com.teamtea.teastory;

import com.teamtea.teastory.entity.SeatEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.teamtea.teastory.entity.ScarecrowEntity;


public final class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, TeaStory.MODID);
    public static DeferredHolder<EntityType<?>, EntityType<Entity>> SEAT_TYPE = ENTITY_TYPE_DEFERRED_REGISTER.register("seat",
            () -> EntityType.Builder.of((type, world) -> new SeatEntity(world), MobCategory.MISC).sized(0F, 0F).build("seat"));

    public static DeferredHolder<EntityType<?>, EntityType<ScarecrowEntity>> SCARECROW_TYPE =ENTITY_TYPE_DEFERRED_REGISTER.register("scarecrow",
            () -> EntityType.Builder.<ScarecrowEntity>of(ScarecrowEntity::new, MobCategory.MISC).sized(0.1F, 0.1F).clientTrackingRange(10).build("scarecrow"));

}