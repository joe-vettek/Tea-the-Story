package xueluoanping.teastory;

import cloud.lemonslice.teastory.blockentity.StoveTileEntity;
import cloud.lemonslice.teastory.entity.SeatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public final class EntityTypeRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TeaStory.MODID);
    public static RegistryObject<EntityType<Entity>> SEAT_TYPE = ENTITY_TYPE_DEFERRED_REGISTER.register("seat",
            () -> EntityType.Builder.of((type, world) -> new SeatEntity(world), MobCategory.MISC).sized(0F, 0F).build("seat"));

}