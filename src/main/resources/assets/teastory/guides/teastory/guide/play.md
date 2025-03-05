---
navigation:
  title: "Play"
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

Due to significant changes in Tea the Story 2.0, this guide provides necessary explanations.

---

## Wild Crops

In the current version, seeds can no longer be obtained by breaking grass. Instead, you can find wild versions of crops or their seeds in nearby villages.

There are currently seven types of wild crops:
<ItemLink id="teastory:wild_rice" />
<ItemLink id="teastory:wild_tea_plant" />
<ItemLink id="teastory:wild_grape" />
<ItemLink id="teastory:wild_cucumber" />
<ItemLink id="teastory:wild_bitter_gourd" />
<ItemLink id="teastory:wild_chili" />
<ItemLink id="teastory:wild_chinese_cabbage" />

Wild rice typically grows near freshwater sources, while tea plants thrive in moist mountain biomes.

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

## Irrigation

Tea the Story introduces a unique farmland irrigation system: **aqueducts**.  
Aqueducts can transport water over long distances to irrigate farmland when connected to a water source block.

There are three types of aqueducts, each obtained by digging specific blocks with an **aqueduct shovel**:
1. **Dirt Aqueduct**: Short irrigation range.
2. **Cobblestone Aqueduct**: Longer irrigation range.
3. **Mossy Aqueduct**: Can connect to rice paddy blocks.

Use gravel to block water flow in aqueducts.

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

## Farming

Chinese cabbage and chili in Tea the Story follow standard farming mechanics, but other crops differ.

### Rice

Rice requires a complex planting process:
1. Plant rice grains on farmland to obtain seedlings.
2. Create a **rice paddy** by tilling farmland with an aqueduct shovel.
3. Connect the paddy to water via mossy aqueducts and plant seedlings in the flooded paddy.

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

### Tea

Tea plants must be grown on farmland. Use shears to harvest leaves from mature plants. To obtain tea seeds, apply bone meal to fully mature tea plants.

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

### Trellis Crops

Some crops require trellises to grow vertically. Place trellises on dirt blocks and plant the following at their base:
- Grapes
- Cucumbers
- Bitter Melons

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

### Watermelon Vines

Watermelons now grow on vines. If the vine lacks support below, the watermelon will drop.

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

## Bamboo Trays

Bamboo trays have four modes:
1. **Baking Mode**: Requires a fire source beneath.
2. **Indoor Mode**: Only works indoors.
3. **Outdoor Mode**: Only works outdoors.
4. **Rain Mode**: Activates during rainfall.

Check the GUI for real-time status. Note: More items in the tray increase processing time.

<GameScene>
<Block  id="teastory:bamboo_tray" />
<Block y="-1"  id="teastory:dirt_stove" p:lit="true" />
</GameScene>

Place a tray on a catapult board to enable **automated ejection mode**.

<GameScene>
<Block  x="2" y="1"id="teastory:stone_catapult_board_with_tray" />
<Block  x="2" id="minecraft:stone" />
<Block  x="1" id="minecraft:stone" />
<Block  y="1"id="teastory:stone_catapult_board_with_tray" p:enabled="true"/>
<Block  id="minecraft:stone" />
</GameScene>

---

## Cooking Rice

1. Grind rice grains into rice.
2. Wash the rice in a wooden barrel.
3. Cook the rice in an iron pot over a fire.
    - Hold at least 8 portions.
    - Add water, cover the pot, and heat with a fire source.

<GameScene>
<Block  id="teastory:saucepan" p:step="cooked"/>
<Block y="-1"  id="teastory:dirt_stove" p:lit="true" />
</GameScene>
---

## Brewing Tea

Start by boiling water in a kettle over a fire.


Check JEI recipes for fluid ratios. For example:
- A kettle with 2000mB (2 buckets) of boiling water requires **4+ green tea bags** to start brewing (500mB consumes 1 tea bag).

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

## Miscellaneous

### Holes

Random holes may appear in the world. After rain, bamboo grows from them.

<GameScene>
<Block  id="teastory:grass_block_with_hole" />
</GameScene>

### Hybridizable Flowers

Hyacinth, chrysanthemum, and zinnia can crossbreed when densely planted. Use bone meal to accelerate hybridization.

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