package com.teamtea.teastory.data.lang;


import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.teamtea.teastory.registry.BlockRegister;
import com.teamtea.teastory.registry.EntityRegister;
import com.teamtea.teastory.registry.ItemRegister;
import com.teamtea.teastory.TeaStory;


public class Lang_ZH extends LangHelper {
    public Lang_ZH(PackOutput gen, ExistingFileHelper helper) {
        super(gen, helper, TeaStory.MODID, "zh_cn");
    }


    @Override
    protected void addTranslations() {
        add(EntityRegister.SCARECROW_TYPE.get(), "稻草人");

        add(BlockRegister.stone_campfire.get(), "石沿营火");
        add(BlockRegister.cobblestoneAqueduct.get(), "石制水渠");
        add(BlockRegister.mossyCobblestoneAqueduct.get(), "苔石渠口");

        add(BlockRegister.RiceSeedlingBlock.get(), "稻谷");
        add(BlockRegister.ricePlant.get(), "水稻秧苗");
        add(BlockRegister.TEA_SEEDS.get(), "茶籽");
        add(BlockRegister.CHILI_PLANT.get(), "辣椒种子");
        add(BlockRegister.CHILI.get(), "辣椒");
        add(BlockRegister.CHINESE_CABBAGE_PLANT.get(), "白菜种子");
        add(BlockRegister.CHINESE_CABBAGE.get(), "白菜");
        add(BlockRegister.GRAPE.get(), "葡萄");
        add(BlockRegister.CUCUMBER.get(), "黄瓜");
        add(BlockRegister.BITTER_GOURD.get(), "苦瓜");
        add(BlockRegister.WOODEN_BOWL_ITEM.get(), "木碗");

        add(BlockRegister.WILD_RICE.get(), "野生水稻");
        add(BlockRegister.WILD_CHINESE_CABBAGE.get(), "野生白菜");
        add(BlockRegister.WILD_CHILI.get(), "野生辣椒");
        add(BlockRegister.WILD_CUCUMBER.get(), "野生黄瓜");
        add(BlockRegister.WILD_BITTER_GOURD.get(), "野生苦瓜");

        add(ItemRegister.NETHER_WART_RICE_BOWL.get(), "地狱疣饭");
        add(ItemRegister.SPICY_BEEF_RICE_BOWL.get(), "香辣牛肉饭");
        add(ItemRegister.BEEF_RICE_BOWL.get(), "牛肉饭");
        add(ItemRegister.RICE_BOWL.get(), "米饭");
        add(ItemRegister.PICKLED_CABBAGE_WITH_FISH.get(), "酸菜鱼");
        add(ItemRegister.STEAMED_CHINESE_CABBAGE.get(), "蒸白菜");
        add(ItemRegister.HONEY_BITTER_GOURD.get(), "苦瓜作乐");
        add(ItemRegister.SHREDDED_CUCUMBER_SALAD.get(), "黄瓜沙拉");
        add(ItemRegister.PORK_BAOZI.get(), "猪肉包子");
        add(ItemRegister.BEEF_BURGER.get(), "牛肉堡");
        add(ItemRegister.CHICKEN_BURGER.get(), "鸡肉堡");

        add("itemGroup.teastory.core", "茶风·纪事·核心");
        add("itemGroup.teastory.drink", "茶风·纪事·茶饮");
        add("item.teastory.ash", "灰烬");
        add("item.teastory.dried_beetroot", "甜菜根干");
        add("item.teastory.dried_carrot", "胡萝卜干");
        add("item.teastory.beef_jerky", "牛肉干");
        add("item.teastory.pork_jerky", "猪肉干");
        add("item.teastory.chicken_jerky", "鸡肉干");
        add("item.teastory.rabbit_jerky", "兔肉干");
        add("item.teastory.mutton_jerky", "羊肉干");
        add("item.teastory.clay_teapot", "粘土壶坯");
        add("item.teastory.clay_cup", "粘土杯坯");
        add("item.teastory.porcelain_cup", "白瓷杯");
        add("item.teastory.porcelain_cup_drink", "装有饮料的白瓷杯");
        add("item.teastory.bottle", "水杯");
        add("item.teastory.bottle_drink", "装有饮料的水杯");
        add("item.teastory.boiling_water_bucket", "开水桶");
        add("item.teastory.hot_water_80_bucket", "热水（80℃）桶");
        add("item.teastory.hot_water_60_bucket", "热水（60℃）桶");
        add("item.teastory.warm_water_bucket", "温水桶");
        add("item.teastory.sugary_water_bucket", "糖水桶");
        add("item.teastory.weak_green_tea_bucket", "淡绿茶桶");
        add("item.teastory.weak_black_tea_bucket", "淡红茶桶");
        add("item.teastory.weak_white_tea_bucket", "淡白茶桶");
        add("item.teastory.green_tea_bucket", "绿茶桶");
        add("item.teastory.black_tea_bucket", "红茶桶");
        add("item.teastory.white_tea_bucket", "白茶桶");
        add("item.teastory.strong_green_tea_bucket", "浓绿茶桶");
        add("item.teastory.strong_black_tea_bucket", "浓红茶桶");
        add("item.teastory.strong_white_tea_bucket", "浓白茶桶");
        add("item.teastory.apple_juice_bucket", "苹果汁桶");
        add("item.teastory.sugar_cane_juice_bucket", "甘蔗汁桶");
        add("item.teastory.carrot_juice_bucket", "胡萝卜汁桶");
        add("item.teastory.grape_juice_bucket", "葡萄汁桶");
        add("item.teastory.cucumber_juice_bucket", "黄瓜汁桶");
        add("item.teastory.bamboo_plank", "竹板");
        add("item.teastory.tea_seeds", "茶籽");
        add("item.teastory.tea_leaves", "绿茶鲜叶");
        add("item.teastory.tea_residues", "茶叶渣");
        add("item.teastory.green_tea_leaves", "绿茶干叶");
        add("item.teastory.black_tea_leaves", "红茶干叶");
        add("item.teastory.white_tea_leaves", "白茶干叶");
        add("item.teastory.empty_tea_bag", "空茶包");
        add("item.teastory.green_tea_bag", "绿茶茶包");
        add("item.teastory.black_tea_bag", "红茶茶包");
        add("item.teastory.rice_grains", "稻谷");
        add("item.teastory.rice_seedlings", "水稻秧苗");
        add("item.teastory.rice", "大米");
        add("item.teastory.washed_rice", "淘好的米");
        add("item.teastory.grapes", "葡萄");
        add("item.teastory.cucumber", "黄瓜");
        add("item.teastory.wet_straw", "鲜稻草");
        add("item.teastory.dry_straw", "干稻草");
        add("item.teastory.crushed_straw", "稻草碎");
        add("item.teastory.bamboo_charcoal", "竹炭");
        add("item.teastory.honeycomb_briquette", "蜂窝煤");
        add("item.teastory.wooden_aqueduct_shovel", "木沟渠铲");
        add("item.teastory.stone_aqueduct_shovel", "石沟渠铲");
        add("item.teastory.gold_aqueduct_shovel", "金沟渠铲");
        add("item.teastory.iron_aqueduct_shovel", "铁沟渠铲");
        add("item.teastory.diamond_aqueduct_shovel", "钻石沟渠铲");
        add("item.teastory.iron_sickle", "铁镰刀");
        add("item.teastory.raisins", "紫葡萄干");
        add("item.teastory.rice_ball", "饭团");
        add("item.teastory.rice_ball_with_kelp", "速食饭团");
        add("item.teastory.shennong_chi", "神农尺");
        add("item.teastory.saucepan_lid", "锅盖");
        add("item.teastory.picking_tea", "音乐唱片");
        add("item.teastory.picking_tea.desc", "采茶舞曲");
        add("item.teastory.spring_festival_overture", "音乐唱片");
        add("item.teastory.spring_festival_overture.desc", "李焕之 - 春节序曲");
        add("item.teastory.flowers_moon", "音乐唱片");
        add("item.teastory.flowers_moon.desc", "花好月圆");
        add("item.teastory.moving_up", "音乐唱片");
        add("item.teastory.moving_up.desc", "步步高");
        add("item.teastory.joyful", "音乐唱片");
        add("item.teastory.joyful.desc", "喜洋洋");
        add("item.teastory.dancing_golden_snake", "音乐唱片");
        add("item.teastory.dancing_golden_snake.desc", "金蛇狂舞");
        add("item.teastory.green_willow", "音乐唱片");
        add("item.teastory.green_willow.desc", "杨柳青");
        add("item.teastory.purple_bamboo_melody", "音乐唱片");
        add("item.teastory.purple_bamboo_melody.desc", "紫竹调");
        add("item.teastory.welcome_march", "音乐唱片");
        add("item.teastory.welcome_march.desc", "欢迎进行曲");
        add("block.teastory.wooden_frame", "木制框架");
        add("block.teastory.dirt_stove", "土灶");
        add("block.teastory.stone_stove", "石灶");
        add("block.teastory.bamboo_tray", "竹匾");
        add("block.teastory.drink_maker", "沏茶台");
        add("block.teastory.porcelain_teapot", "白瓷壶");
        add("block.teastory.wild_tea_plant", "野生茶树");
        add("block.teastory.bamboo_door", "竹门");
        add("block.teastory.bamboo_glass_door", "竹玻璃门");
        add("block.teastory.bamboo_table", "竹桌");
        add("block.teastory.wooden_table", "木桌");
        add("block.teastory.stone_chair", "石凳");
        add("block.teastory.bamboo_lantern", "竹灯");
        add("block.teastory.bamboo_chair", "竹椅");
        add("block.teastory.wooden_chair", "木椅");
        add("block.teastory.stone_table", "石桌");
        add("block.teastory.chrysanthemum", "菊花");
        add("block.teastory.hyacinth", "风信子");
        add("block.teastory.zinnia", "百日菊");
        add("block.teastory.stone_catapult_board", "石制弹射板");
        add("block.teastory.bamboo_catapult_board", "竹制弹射板");
        add("block.teastory.iron_catapult_board", "铁制弹射板");
        add("block.teastory.stone_catapult_board_with_tray", "自动化竹匾");
        add("block.teastory.grass_block_with_hole", "坑");
        add("block.teastory.filter_screen", "滤网");
        add("block.teastory.bamboo_lattice", "竹篱笆");
        add("block.teastory.wooden_tray", "木制托盘");
        add("block.teastory.fresh_bamboo_wall", "青竹墙");
        add("block.teastory.dried_bamboo_wall", "干竹墙");
        add("block.teastory.iron_kettle", "铁壶");
        add("block.teastory.wild_grape", "野葡萄");
        add("block.teastory.instrument_shelter", "百叶箱");
        add("block.teastory.dirt_aqueduct", "土制灌溉渠");
        add("block.teastory.dirt_aqueduct_pool", "土制灌溉渠口");
        add("block.teastory.paddy_field", "稻田");
        add("block.teastory.scarecrow", "稻草人");
        add("block.teastory.dry_haystack", "干稻草垛");
        add("block.teastory.wet_haystack", "鲜稻草垛");
        add("block.teastory.stone_mill", "石磨");
        add("block.teastory.stone_roller", "石碾");
        add("block.teastory.wooden_barrel", "木桶");
        add("block.teastory.saucepan", "煮锅");
        add("fluid_type.teastory.boiling_water", "开水");
        add("fluid_type.teastory.hot_water_80", "热水（80℃）");
        add("fluid_type.teastory.hot_water_60", "热水（60℃）");
        add("fluid_type.teastory.warm_water", "温水");
        add("fluid_type.teastory.sugary_water", "糖水");
        add("fluid_type.teastory.weak_green_tea", "淡绿茶");
        add("fluid_type.teastory.weak_black_tea", "淡红茶");
        add("fluid_type.teastory.weak_white_tea", "淡白茶");
        add("fluid_type.teastory.green_tea", "绿茶");
        add("fluid_type.teastory.black_tea", "红茶");
        add("fluid_type.teastory.white_tea", "白茶");
        add("fluid_type.teastory.strong_green_tea", "浓绿茶");
        add("fluid_type.teastory.strong_black_tea", "浓红茶");
        add("fluid_type.teastory.strong_white_tea", "浓白茶");
        add("fluid_type.teastory.apple_juice", "苹果汁");
        add("fluid_type.teastory.sugar_cane_juice", "甘蔗汁");
        add("fluid_type.teastory.carrot_juice", "胡萝卜汁");
        add("fluid_type.teastory.grape_juice", "葡萄汁");
        add("fluid_type.teastory.cucumber_juice", "黄瓜汁");
        add("container.teastory.stove", "炉灶");
        add("container.teastory.bamboo_tray", "竹匾");
        add("container.teastory.drink_maker", "沏茶台");
        add("container.teastory.stone_mill", "石磨");
        add("container.teastory.stone_roller", "石碾");
        add("effect.teastory.agility", "云身");
        add("effect.teastory.life_drain", "生命汲取");
        add("effect.teastory.photosynthesis", "光合作用");
        add("effect.teastory.defence", "金钟罩");
        add("effect.teastory.excitement", "提神");
        add("info.teastory.bamboo_tray.mode.in_rain", "淋雨模式");
        add("info.teastory.bamboo_tray.mode.outdoors", "户外模式");
        add("info.teastory.bamboo_tray.mode.indoors", "室内模式");
        add("info.teastory.bamboo_tray.mode.bake", "烘焙模式");
        add("info.teastory.bamboo_tray.mode.process", "加工模式");
        add("info.teastory.drink_maker", "沏茶");
        add("info.teastory.stone_mill", "研磨");
        add("info.teastory.bed.excited", "你现在太兴奋了，以致于无法入睡。");
        add("info.teastory.hyb.flower.color.yellow", "黄色");
        add("info.teastory.hyb.flower.color.red", "红色");
        add("info.teastory.hyb.flower.color.blue", "蓝色");
        add("info.teastory.hyb.flower.color.white", "白色");
        add("info.teastory.hyb.flower.color.pink", "粉色");
        add("info.teastory.hyb.flower.color.gold", "金色");
        add("info.teastory.hyb.flower.color.black", "黑色");
        add("info.teastory.hyb.flower.color.orange", "橙色");
        add("info.teastory.tooltip.drink_maker.help.1", "沏茶原料槽");
        add("info.teastory.tooltip.drink_maker.help.2", "残渣槽");
        add("info.teastory.tooltip.drink_maker.warn.1", "无法找到匹配配方！");
        add("info.teastory.tooltip.drink_maker.warn.2", "标红格子内原料数量不足。");
        add("info.teastory.tooltip.iron_kettle.to_fill", "右击水面装水");
        add("info.teastory.tooltip.iron_kettle.to_boil", "放置于炉灶上加热");
        add("info.teastory.record", "您还未安装唱片资源包");
        add("commands.teastory.solar.set", "已将节气天数设置为第%s天");
        add("death.attack.boiling", "%1$s被开水烫伤了");
        add("misc.block.teastory.trellis_suffix", "棚架");
    }


}
