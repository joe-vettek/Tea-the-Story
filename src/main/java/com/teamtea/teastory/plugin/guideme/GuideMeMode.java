package com.teamtea.teastory.plugin.guideme;

import com.teamtea.teastory.TeaStory;
import guideme.Guide;
import guideme.GuideItemSettings;
import guideme.Guides;
import guideme.GuidesCommon;
import guideme.scene.BlockImageTagCompiler;

public class GuideMeMode {
    public static Guide guide = null;

    public static void init() {
        guide = Guide.builder(TeaStory.rl("guide"))

                .build();
        // GuidesCommon.openGuide();
    }
}
