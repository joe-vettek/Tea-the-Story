---
navigation:
    title: "Datapack"
    icon: "teastory:porcelain_teapot"
    parent: index.md
---

### Custom Tea Drink Effects
*JSON files must be placed under `data/<namespace>/teastory/drink_effect`. Ensure not to confuse teastory with the
namespace. The namespace should be your own modid or datapack ID, and the folder hierarchy must match exactly.*

To improve flexibility, tea drink effects are now configurable via datapacks.
The fluid in the teacup must have the `teastory:drink` tag to be recognized.
Remove comments when using. The configuration consists of two parts: fluid matching and effect definitions.
For tag matching, use "Tag", consistent with other recipe requirements.
If the amount of tea consumed is greater than the requirement, the effect time will be extended by an integer multiple; if it is less than the requirem

```json5
{
  "fluid": {
    "Amount": 250,
    // Required fluid amount (mB) to trigger the effect  
    "FluidName": "teastory:sugary_water"
    // Fluid ID  
  },
  "effects": [
    {
      "duration": 2,
      // Effect duration 
      "level": 2,
      // Effect level  
      "id": "minecraft:speed"
      // effect ID  
    }
  ]
}  
```

For NeoForge 1.21+: Fluid deserialization has changed. Use "components" for component-based matching, consistent with
recipe syntax.

```json5
{
  "fluid": {
    "amount": 250,
    // Required fluid amount (mB) to trigger the effect  
    "id": "teastory:sugary_water"
    // Fluid ID  
  },
  "effects": [
    {
      "duration": 2,
      // Effect duration
      "level": 2,
      // Effect level  
      "id": "minecraft:speed"
      // effect ID  
    }
  ]
}  
```

## Custom Scarecrow Repelling Targets

Add the `minecraft:birds` tag to any entity to make it repelled by scarecrows.

<GameScene>
<Block  id="teastory:scarecrow" p:half="upper"/>
<Block  y="-1" id="teastory:scarecrow" p:half="lower"/>
</GameScene>