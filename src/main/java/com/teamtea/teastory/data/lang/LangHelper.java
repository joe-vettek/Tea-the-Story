package com.teamtea.teastory.data.lang;

import com.teamtea.teastory.TeaStory;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class LangHelper extends LanguageProvider {
    private final ExistingFileHelper helper;
    private final PackOutput output;


    public LangHelper(PackOutput output, ExistingFileHelper helper, String modid, String locale) {
        super(output, modid, locale);
        this.output = output;
        this.helper = helper;
        this.modid = modid;
        this.locale = locale;
    }


    public void addTittle(String name, String s) {
        add("advancement.%s.%s".formatted(TeaStory.MODID, name), s);
    }

    public void addDescription(String name, String s) {
        add("advancement.%s.%s.desc".formatted(TeaStory.MODID, name), s);
    }

    // There is a lot of code here that is redundant, but indispensable. In order to make corrections
    protected abstract void addTranslations();

    private final String locale;
    public final String modid;

}
