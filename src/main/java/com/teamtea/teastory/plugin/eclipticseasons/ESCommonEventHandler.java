package com.teamtea.teastory.plugin.eclipticseasons;

import com.teamtea.eclipticseasons.api.constant.crop.CropHumidityInfo;
import com.teamtea.eclipticseasons.api.constant.crop.CropHumidityType;
import com.teamtea.eclipticseasons.api.constant.crop.CropSeasonInfo;
import com.teamtea.eclipticseasons.api.constant.crop.CropSeasonType;
import com.teamtea.eclipticseasons.common.core.crop.CropInfoManager;
import com.teamtea.teastory.block.crops.TrellisWithVineBlock;
import com.teamtea.teastory.plugin.CompatManager;
import com.teamtea.teastory.variant.Planks;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

public class ESCommonEventHandler {
    public static ESCommonEventHandler INSTANCE = new ESCommonEventHandler();


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTagsUpdated(TagsUpdatedEvent tagsUpdatedEvent) {
        if (CompatManager.enable.getAsBoolean()) {
            for (Planks.PlankHolders value : Planks.TrellisBlockMap.values()) {
                for (TrellisWithVineBlock trellisWithVineBlock : value.trellisWithVineBlocks()) {
                    CropHumidityInfo humidityInfo = CropInfoManager.getHumidityInfo(trellisWithVineBlock.getVineType().getFruit());
                    CropHumidityType cropHumidityType0 = null;
                    for (CropHumidityType cropHumidityType : CropHumidityType.values()) {
                        if (cropHumidityType.getInfo() == humidityInfo) {
                            cropHumidityType0 = cropHumidityType;
                        }
                    }
                    if (cropHumidityType0 != null)
                        CropInfoManager.registerCropHumidityInfo(trellisWithVineBlock, cropHumidityType0, true);


                    CropSeasonInfo cropSeasonInfo = CropInfoManager.getSeasonInfo(trellisWithVineBlock.getVineType().getFruit());
                    CropSeasonType cropSeasonType0 = null;
                    for (CropSeasonType cropSeasonType : CropSeasonType.values()) {
                        if (cropSeasonType.getInfo() == cropSeasonInfo) {
                            cropSeasonType0 = cropSeasonType;
                        }
                    }
                    if (cropSeasonType0 != null)
                        CropInfoManager.registerCropSeasonInfo(trellisWithVineBlock, cropSeasonType0, true);

                }
            }

        }
    }


}
