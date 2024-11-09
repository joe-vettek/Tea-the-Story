package xueluoanping.teastory.data.advancement;


import cloud.lemonslice.teastory.tag.TeaTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.registry.BlockEntityRegister;
import xueluoanping.teastory.registry.BlockRegister;
import xueluoanping.teastory.registry.ItemRegister;


import java.util.function.Consumer;

public class TeaAdvancementGenerator implements AdvancementSubProvider {

    public static String ROOT = "root";
    public static String AQUEDUCT_SHOVEL = "aqueduct_shovel";
    public static String MOSSY_COBBLESTONE_AQUEDUCT = "mossy_cobblestone_aqueduct";
    public static String AQUEDUCT = "aqueduct";

    public static String RICESeedlings = "rice_seedlings";
    public static String PaddyField = "paddy_field";
    public static String RICE = "rice";
    public static String WASH_RICE = "wash_rice";
    public static String COOK_RICE = "cook_rice";


    public static String TEA_LEAVES = "tea_leaves";

    public static MutableComponent getTittle(String name) {
        return Component.translatable("advancement.%s.%s".formatted(TeaStory.MODID, name));
    }

    public static MutableComponent getDescription(String name) {
        return Component.translatable("advancement.%s.%s.desc".formatted(TeaStory.MODID, name));
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement()
                .display(ItemRegister.TEA_LEAVES.get(),
                        getTittle(ROOT),
                        getDescription(ROOT),
                        new ResourceLocation("minecraft:textures/block/bricks.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("any", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .requirements(RequirementsStrategy.AND)
                .save(consumer, getNameId(ROOT));

        Advancement aqueduct_shovel = build(
                root, ItemRegister.WOODEN_AQUEDUCT_SHOVEL.get(), AQUEDUCT_SHOVEL, TeaTags.Items.AQUEDUCT_SHOVEL, consumer
        );

        Advancement aqueduct = build(
                aqueduct_shovel, BlockRegister.cobblestoneAqueduct.get(), AQUEDUCT, Tags.Items.COBBLESTONE, consumer
        );

        Advancement mossyCobblestoneAqueduct = build(
                aqueduct, BlockRegister.mossyCobblestoneAqueduct.get(), MOSSY_COBBLESTONE_AQUEDUCT, Tags.Items.COBBLESTONE_MOSSY, consumer
        );

        Advancement riceSeedlings = build(
                root, BlockRegister.riceSeedlings.get(), RICESeedlings, new ItemLike[]{BlockRegister.RiceSeedlingBlock.get()}, consumer
        );

        Advancement paddyField = build(
                riceSeedlings, BlockRegister.paddyField.get(), PaddyField, new ItemLike[]{}, consumer
        );

        Advancement getRice = build(
                paddyField, BlockRegister.RICE_GRAINS.get(), RICE, new ItemLike[]{BlockRegister.RICE_GRAINS.get()}, consumer
        );

        Advancement washRice = build(
                getRice, BlockEntityRegister.WOODEN_BARREL_ITEM.get(), WASH_RICE, new ItemLike[]{BlockEntityRegister.WOODEN_BARREL_ITEM.get()}, consumer
        );

        Advancement cookRice = build(
                washRice, BlockRegister.saucepan_ITEM.get(), COOK_RICE, new ItemLike[]{BlockRegister.saucepan_ITEM.get()}, consumer
        );

        Advancement teaLeaves = build(
                root, Items.SHEARS, TEA_LEAVES, Tags.Items.SHEARS, consumer
        );
    }

    public Advancement build(Advancement parent,
                                   ItemLike icon,
                                   String name,
                                   ItemLike[] itemLikes,
                                   Consumer<Advancement> consumer


    ) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        getTittle(name),
                        getDescription(name),
                        new ResourceLocation("minecraft:textures/block/bricks.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("require", InventoryChangeTrigger.TriggerInstance.hasItems(
                        itemLikes
                ))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, getNameId(name));
    }

    public Advancement build(Advancement parent,
                                   ItemLike icon,
                                   String name,
                                   TagKey<Item> pTag,
                                   Consumer<Advancement> consumer


    ) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(icon,
                        getTittle(name),
                        getDescription(name),
                        new ResourceLocation("minecraft:textures/block/bricks.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("require", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(pTag).build()
                        )
                )
                .requirements(RequirementsStrategy.OR)
                .save(consumer, getNameId(name));
    }


    private String getNameId(String id) {
        return TeaStory.MODID + ":game/" + id;
    }
    
}
