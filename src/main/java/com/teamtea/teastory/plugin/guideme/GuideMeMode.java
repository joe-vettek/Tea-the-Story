package com.teamtea.teastory.plugin.guideme;

import com.teamtea.teastory.TeaStory;
import guideme.Guide;

public class GuideMeMode {
    public static Guide guide = null;

    public static void init() {
        guide = Guide.builder(TeaStory.rl("guide"))
                .build();
        // GuidesCommon.openGuide();
    }
}
