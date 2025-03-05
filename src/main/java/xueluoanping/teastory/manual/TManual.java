package xueluoanping.teastory.manual;

import li.cil.manual.api.content.Document;
import li.cil.manual.api.prefab.Manual;
import net.minecraft.client.Minecraft;

import java.util.LinkedHashSet;
import java.util.Optional;

public class TManual extends Manual {

    @Override
    public Optional<Document> documentFor(String path) {
        String language = Minecraft.getInstance().getLanguageManager().getSelected();
        Optional<Document> document = this.documentFor(path.replace("%LANGUAGE%", language), language, new LinkedHashSet());
        return document.isPresent() ? document : this.documentFor(path.replace("%LANGUAGE%", "en_us"), "en_us", new LinkedHashSet());

    }


}
