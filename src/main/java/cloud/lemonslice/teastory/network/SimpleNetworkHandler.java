package cloud.lemonslice.teastory.network;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import xueluoanping.teastory.TeaStory;

import java.util.function.Function;

public final class SimpleNetworkHandler
{
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(TeaStory.MODID, "main"))
            .networkProtocolVersion(() -> TeaStory.NETWORK_VERSION)
            .serverAcceptedVersions(TeaStory.NETWORK_VERSION::equals)
            .clientAcceptedVersions(TeaStory.NETWORK_VERSION::equals)
            .simpleChannel();

    public static void init()
    {
        int id = 0;
        registerMessage(id++, SolarTermsMessage.class, SolarTermsMessage::new);
    }

    private static <T extends INormalMessage> void registerMessage(int index, Class<T> messageType, Function<FriendlyByteBuf, T> decoder)
    {
        CHANNEL.registerMessage(index, messageType, INormalMessage::toBytes, decoder, (message, context) ->
        {
            message.process(context);
            context.get().setPacketHandled(true);
        });
    }
}
