package com.teamtea.teastory.item;


import com.teamtea.teastory.config.ServerConfig;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;

public class FertilizerItem extends Item {
    public FertilizerItem(Item.Properties name) {
        super(name);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (ServerConfig.Agriculture.useAshAsBoneMeal.get()) {
            return Items.BONE_MEAL.useOn(context);
        }
        return InteractionResult.PASS;
    }
}
