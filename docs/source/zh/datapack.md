
## 自定义茶水效果

*注意json文件需要放在`data/<命名空间>/teastory/drink_effect`下，注意不要混淆teastory和命名空间，命名空间部分写你自己的modid或者datapack的id，文件夹关系需要保持一致。*

由于有朋友希望能让茶水效果定义更加灵活，我将其改写为了数据包。使用时注意不写注释。
茶杯的流体需要有`teastory:drink`标签才能引用。
分为两个部分,一个是流体匹配部分，一个是效果列表。
如果需要标签匹配可以写"Tag"，这与一些配方中要求一致。
如果饮用的茶水量大于要求，那么效果时间会按整数倍延长，如果小于则不生效。

```json5
{
  "fluid": {
    "Amount": 250,
    // 触发需要的流体量（mB）
    "FluidName": "teastory:sugary_water"
    // 流体ID
  },
  "effects": [
    {
      "duration": 2,
      // 效果持续时间
      "level": 2,
      // 效果等级
      "potion": "minecraft:speed"
      // 药水效果ID
    }
  ]
}
```
1.21的neoforge版本对流体部分的反序列化做了变动。
如果需要组件匹配可以写"components"，这与一些配方中要求一致。

```json5
{
  "fluid": {
    "amount": 250,
    // 触发需要的流体量（mB）
    "id": "teastory:sugary_water"
    // 流体ID
  },
  "effects": [
    {
      "duration": 2,
      // 效果持续时间
      "level": 2,
      // 效果等级
      "potion": "minecraft:speed"
      // 药水效果ID
    }
  ]
}
```
## 自定义稻草人驱逐对象

对对应实体加上`minecraft:birds`即可。