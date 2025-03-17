package xueluoanping.teastory.registry;

import cloud.lemonslice.teastory.item.AqueductShovelItem;
import li.cil.manual.api.ManualModel;
import li.cil.manual.api.prefab.provider.NamespaceDocumentProvider;
import li.cil.manual.api.prefab.provider.NamespacePathProvider;
import li.cil.manual.api.provider.DocumentProvider;
import li.cil.manual.api.provider.PathProvider;
import li.cil.manual.api.util.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.teastory.TeaStory;
import xueluoanping.teastory.manual.TManual;

// 可以写成非static
public class ManualRegistry {

    public static final DeferredRegister<ManualModel> MANUALS = DeferredRegister.create(Constants.MANUAL_REGISTRY, TeaStory.MODID);
    public static final RegistryObject<TManual> MANUAL = MANUALS.register("manual", TManual::new);

    public static final DeferredRegister<DocumentProvider> DOCUMENT_PROVIDERS = DeferredRegister.create(Constants.DOCUMENT_PROVIDER_REGISTRY, TeaStory.MODID);
    public static final RegistryObject<DocumentProvider> DOCUMENT_PROVIDER = DOCUMENT_PROVIDERS.register("manual_provider", () ->
            new NamespaceDocumentProvider(TeaStory.MODID, "doc"));

    public static final DeferredRegister<PathProvider> PATH_PROVIDERS = DeferredRegister.create(Constants.PATH_PROVIDER_REGISTRY, TeaStory.MODID);
    public static final RegistryObject<PathProvider> PATH_PROVIDER = PATH_PROVIDERS.register("manual_path_provider", () ->
            new NamespacePathProvider(TeaStory.MODID, false));

    public static RegistryObject<Item> MANUAL_ITEM = null;

}
