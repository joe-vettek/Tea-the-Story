---
navigation:
  title: "游玩"
  icon: "teastory:tea_leaves"

item_ids:
  - teastory:wild_rice
  - teastory:wild_tea_plant
  - teastory:wild_grape
  - teastory:wild_cucumber
  - teastory:wild_bitter_gourd
  - teastory:wild_chili
  - teastory:wild_chinese_cabbage
  - teastory:tea_leaves
  - teastory:rice_grains
  - teastory:rice_ball
---

由于茶风2.0有很大的变化，因此这里写了一个必要说明。

---

## 野生作物

目前版本已经不能再通过打草获得种子，可以发现它们的野生版本，或者在附近的村庄里发现它们的种子。

目前一共有:
<ItemLink id="teastory:wild_rice" />
<ItemLink id="teastory:wild_tea_plant" />
<ItemLink id="teastory:wild_grape" />
<ItemLink id="teastory:wild_cucumber" />
<ItemLink id="teastory:wild_bitter_gourd" />
<ItemLink id="teastory:wild_chili" />
<ItemLink id="teastory:wild_chinese_cabbage" />

其中，野生稻往往生长在淡水流域附近，茶树则多生长在湿润的山坡群系。

<GameScene>
    <Block id="teastory:wild_rice" />
    <Block id="teastory:wild_tea_plant" x="0" z="2"/>
    <Block id="teastory:wild_grape" x="2" />
    <Block id="teastory:wild_cucumber" x="2" z="2"/>
    <Block id="teastory:wild_bitter_gourd" x="4" />
    <Block id="teastory:wild_chili" x="4" z="2" />
    <Block id="teastory:wild_chinese_cabbage" x="6" />
    <Block y="-1" x="0" z="0" id="minecraft:mud" />
     <Block y="-1" x="0" z="1" id="minecraft:mud" />
 <Block y="-1" x="0" z="2" id="minecraft:moss_block" />
 <Block y="-1" x="1" z="0" id="minecraft:dirt" />
 <Block y="-1" x="1" z="1" id="minecraft:dirt" />
 <Block y="-1" x="1" z="2" id="minecraft:dirt" />
 <Block y="-1" x="2" z="0" id="minecraft:dirt" />
 <Block y="-1" x="2" z="1" id="minecraft:dirt" />
<Block y="-1" x="2" z="2" id="minecraft:dirt" />
 <Block y="-1" x="3" z="0" id="minecraft:dirt" />
 <Block y="-1" x="3" z="1" id="minecraft:dirt" />
<Block y="-1" x="3" z="2" id="minecraft:dirt" />
 <Block y="-1" x="4" z="0" id="minecraft:dirt" />
 <Block y="-1" x="4" z="1" id="minecraft:grass_block" />
<Block y="-1" x="4" z="2" id="minecraft:grass_block" />
 <Block y="-1" x="5" z="0" id="minecraft:grass_block" />
 <Block y="-1" x="5" z="1" id="minecraft:grass_block" />
<Block y="-1" x="5" z="2" id="minecraft:grass_block" />
 <Block y="-1" x="6" z="0" id="minecraft:grass_block" />
 <Block y="-1" x="6" z="1" id="minecraft:grass_block" />
<Block y="-1" x="6" z="2" id="minecraft:grass_block" />
</GameScene>

---

## 灌溉

茶风提供了一种特别的耕地灌溉方式，沟渠。
沟渠可以在连接水源方块后自动远距离传输水分给土地。
目前有三种类型，需要用沟渠铲挖掘对应方块得到。
其中，土沟渠的灌溉距离较短，而圆石沟渠灌溉的距离较长，苔石渠口则可以连接稻田方块。
使用沙砾可以阻止沟渠水的流动。
<GameScene>
<Block  x="2" id="teastory:mossy_cobblestone_aqueduct"   />
<Block  x="1" id="teastory:mossy_cobblestone_aqueduct"  />
<Block  x="-1" id="teastory:cobblestone_aqueduct"  />
<Block   id="teastory:cobblestone_aqueduct" p:waterlogged="true" p:blocked="true"/>
<Block  z="-1" id="teastory:cobblestone_aqueduct" p:waterlogged="true" />
<Block  z="-2" id="teastory:dirt_aqueduct" p:waterlogged="true" />
<Block  z="-3" id="minecraft:water"  />
</GameScene>


---

## 耕种

茶风的白菜和辣椒与普通作物的耕种方式一致，但剩余的有所不同。

### 水稻

水稻是一种特别的作物，种植步骤较为繁琐。

首先需要将稻谷种植在耕地上，获得秧苗。
随后需要搭建稻田，稻田由耕地用沟渠铲开垦，并需要沟渠引水，并用渠口方块连接。
将水稻秧苗种植在有水的稻田中，就可以慢慢长成了。

<GameScene>
    <Block id="teastory:rice_plant" p:age="7"/>
    <Block id="teastory:rice_plant" x="-1" p:age="5"/>
    <Block id="teastory:rice_plant" x="1" p:age="2"/>
    <Block y="-1" id="teastory:paddy_field" p:waterlogged="true" />
    <Block y="-1" x="-1" id="teastory:paddy_field" p:waterlogged="true" />
    <Block y="-1" x="1" id="teastory:paddy_field" p:waterlogged="true" />
    <Block y="-1" z="-1" id="teastory:mossy_cobblestone_aqueduct" p:waterlogged="true" />
    <Block y="-1" z="-2" id="teastory:cobblestone_aqueduct" p:waterlogged="true" />
    <Block y="-1" z="-3" id="minecraft:water"  />
    <Block  x="-1" z="-1" id="teastory:rice_seedling" p:age="3" />
<Block  x="1" z="-1" id="teastory:rice_seedling" />
    <Block y="-1" x="-1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="1" z="-1" id="minecraft:farmland" p:moisture="7" />
</GameScene>

### 茶

茶需要种植在耕地方块上，用剪刀在成熟的茶树上可以收获茶叶。如果需要茶籽，则需要骨粉继续催熟。

<GameScene>
    <Block id="teastory:tea_plant" p:age="11"/>
    <Block id="teastory:tea_plant" x="-1" p:age="9"/>
    <Block id="teastory:tea_plant" x="1" p:age="9"/>
 <Block id="teastory:tea_plant" z="-1" p:age="5"/>
    <Block  x="-1" z="-1" id="teastory:tea_plant" p:age="3" />
<Block  x="1" z="-1" id="teastory:tea_plant" />
    <Block y="-1" x="-1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  x="1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  z="-1" id="minecraft:farmland" p:moisture="7" />
</GameScene>

### 棚架作物

部分作物生长需要棚架，棚架需要放在泥土上，随后在根部种植，作物会向上蔓延，目前可以种植葡萄、黄瓜、苦瓜三种作物。

<GameScene>
    <Block id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/>
    <Block y="1" id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/>
    <Block y="2" id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/> 
    <Block y="2" id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/> 
    <Block y="2" x="-1"id="teastory:minecraft.oak_trellis_with_grape_vine"  p:west="true"/> 
    <Block y="2" x="-2"id="teastory:minecraft.oak_trellis_with_grape_vine"  p:west="true"/> 
    <Block y="1" x="-2" id="teastory:grape_plant" p:age="4" />
<Block id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/>
    <Block y="1" x="-3"id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/>
    <Block y="2" x="-3"id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/> 
    <Block y="0" x="-3"id="teastory:minecraft.oak_trellis_with_grape_vine" p:post="true" p:up="true"/>
<Block y="-1"  id="minecraft:grass_block" />
<Block y="-1"  x="-3"id="minecraft:grass_block" />
</GameScene>

### 西瓜藤

茶风中对西瓜的种植有所改动，西瓜将生长西瓜藤蔓，并生长西瓜在瓜田中。
当西瓜藤下方没有支撑物时，西瓜将会掉落。

<GameScene>
    <Block id="teastory:watermelon_vine" p:age="7"/>
    <Block id="teastory:watermelon_vine" x="-1" p:age="6"/>
    <Block id="teastory:watermelon_vine" x="1" p:age="7"/>
 <Block id="teastory:watermelon_vine" z="-1" p:age="5"/>
    <Block  x="-1" z="-1" id="teastory:watermelon_vine" p:age="3" />
<Block  x="1" z="-1" id="teastory:watermelon_vine" />
    <Block y="-1" x="-1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  x="1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  x="2" id="minecraft:sticky_piston" p:facing="west" p:extended="true"/>
</GameScene>

---

## 竹匾

竹匾有四种模式。烘焙模式需要底下有灶火。室内模式和室外模式则需要分别在室内和室外。淋雨模式需要在雨水中。
实时状态可以打开GUI查看。注意放入的物品越多，消耗的时间越长。
<GameScene>
<Block  id="teastory:bamboo_tray" />
<Block y="-1"  id="teastory:dirt_stove" p:lit="true" />
</GameScene>
在弹射板上放置竹匾可以进入自动化弹出模式。

<GameScene>
<Block  x="2" y="1"id="teastory:stone_catapult_board_with_tray" />
<Block  x="2" id="minecraft:stone" />
<Block  x="1" id="minecraft:stone" />
<Block  y="1"id="teastory:stone_catapult_board_with_tray" p:enabled="true"/>
<Block  id="minecraft:stone" />
</GameScene>

---

## 煮饭

稻谷碾成后，需要在木桶中清洗,随后在铁锅中煮熟。
注意至少需要手持八份，加水和用灶火烧熟，并盖上盖子。

<GameScene>
<Block  id="teastory:saucepan" p:step="cooked"/>
<Block y="-1"  id="teastory:dirt_stove" p:lit="true" />
</GameScene>

## 泡茶

一切泡茶的起点都是烧一壶开水，这需要灶火。

注意查看JEI配方说明中的流体数量，如水壶中有2B的开水，那么配料区需要放置四份以上的绿茶包才能开始泡绿茶，因为500mB的开水就要消耗一份绿茶包。

<GameScene>
<Block  id="teastory:iron_kettle" p:facing="north"/>
<Block y="-1"  id="teastory:dirt_stove" p:lit="true" />
<Block  x="-1" id="teastory:drink_maker" p:facing="south" p:left="true"/>
<Block  x="-2" id="teastory:drink_maker" p:facing="south" p:left="false"/>
<Block y="-1" x="-1" id="teastory:wooden_frame"/>
<Block y="-1" x="-2" id="teastory:wooden_frame" />
<Block  x="-3" id="teastory:wooden_tray" p:cup="3" p:drink="3"/>
<Block y="-1" x="-3" id="teastory:wooden_frame" />
</GameScene>

---

## 其他

### 坑

在世界上偶然可以发现这些坑，他们会在雨后生长出竹子。

<GameScene>
<Block  id="teastory:grass_block_with_hole" />
</GameScene>

### 可杂交的花

风信子、菊花、百日菊这三种花可以同种间杂交，试试密植时骨粉繁殖他们。

<GameScene>
    <Block id="teastory:chrysanthemum" p:color="orange"/>
    <Block id="teastory:chrysanthemum" x="-1" p:color="red"/>
    <Block id="teastory:chrysanthemum" x="1" p:color="yellow"/>
 <Block id="teastory:hyacinth" z="-1" p:color="pink"/>
    <Block  x="-1" z="-1" id="teastory:zinnia" p:color="blue" />
<Block  x="1" z="-1" id="teastory:zinnia" p:color="purple"/>
    <Block y="-1" x="-1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="1" z="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  id="minecraft:farmland" p:moisture="7" />
<Block y="-1" x="-1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  x="1" id="minecraft:farmland" p:moisture="7" />
<Block y="-1"  z="-1" id="minecraft:farmland" p:moisture="7" />
</GameScene>


