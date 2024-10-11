package com.teamtea.teastory.data.advancement;


import com.teamtea.teastory.TeaStory;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.tag.TeaTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class TeaAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

    public static String ROOT = "root";
    public static String AQUEDUCT_SHOVEL = "aqueduct_shovel";
    public static String MOSSY_COBBLESTONE_AQUEDUCT = "mossy_cobblestone_aqueduct";
    public static String AQUEDUCT = "aqueduct";

    public static String RICESeedlings = "rice_seedlings";
    public static String RICE = "rice";
    public static String PaddyField = "paddy_field";

    public static String TEA_LEAVES = "tea_leaves";

    public static MutableComponent getTittle(String name) {
        return Component.translatable("advancement.%s.%s".formatted(TeaStory.MODID, name));
    }

    public static MutableComponent getDescription(String name) {
        return Component.translatable("advancement.%s.%s.desc".formatted(TeaStory.MODID, name));
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(ItemRegister.TEA_LEAVES.get(),
                        getTittle(ROOT),
                        getDescription(ROOT),
                        ResourceLocation.parse("minecraft:textures/block/bricks.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("any", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(consumer, getNameId(ROOT));

        AdvancementHolder aqueduct_shovel = build(
                root, ItemRegister.WOODEN_AQUEDUCT_SHOVEL.get(), AQUEDUCT_SHOVEL, TeaTags.Items.AQUEDUCT_SHOVEL, consumer
        );

        AdvancementHolder aqueduct = build(
                aqueduct_shovel, BlockRegister.cobblestoneAqueduct.get(), AQUEDUCT, Tags.Items.COBBLESTONES, consumer
        );

        AdvancementHolder mossyCobblestoneAqueduct = build(
                aqueduct, BlockRegister.mossyCobblestoneAqueduct.get(), MOSSY_COBBLESTONE_AQUEDUCT, Tags.Items.COBBLESTONES_MOSSY, consumer
        );

        AdvancementHolder riceSeedlings = build(
                root, BlockRegister.riceSeedlings.get(), RICESeedlings, new ItemLike[]{BlockRegister.RiceSeedlingBlock.get()}, consumer
        );

        AdvancementHolder paddyField = build(
                riceSeedlings, BlockRegister.paddyField.get(), PaddyField, new ItemLike[]{}, consumer
        );

        AdvancementHolder getRice = build(
                paddyField, BlockRegister.RICE_GRAINS.get(), RICE, new ItemLike[]{BlockRegister.RICE_GRAINS.get()}, consumer
        );

        AdvancementHolder teaLeaves = build(
                root, Items.SHEARS, TEA_LEAVES, Tags.Items.TOOLS_SHEAR, consumer
        );
    }

    public AdvancementHolder build(AdvancementHolder parent,
                                   ItemLike icon,
                                   String name,
                                   ItemLike[] itemLikes,
                                   Consumer<AdvancementHolder> consumer


    ) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        getTittle(name),
                        getDescription(name),
                        ResourceLocation.parse("minecraft:textures/block/bricks.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("require", InventoryChangeTrigger.TriggerInstance.hasItems(
                        itemLikes
                ))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, getNameId(name));
    }

    public AdvancementHolder build(AdvancementHolder parent,
                                   ItemLike icon,
                                   String name,
                                   TagKey<Item> pTag,
                                   Consumer<AdvancementHolder> consumer


    ) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        getTittle(name),
                        getDescription(name),
                        ResourceLocation.parse("minecraft:textures/block/bricks.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("require", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(pTag))
                )
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, getNameId(name));
    }


    private String getNameId(String id) {
        return TeaStory.MODID + ":" + id;
    }
}
