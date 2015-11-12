package villain.mc.vague.net;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import villain.mc.vague.Reference;

public class PacketHandler {

	public static SimpleNetworkWrapper net;
	private static int nextPacketId = 0;
	
	public static void initPackets(){
		net = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toUpperCase());
		registerMessage(MagnetCooldownUpdatePacket.class, MagnetCooldownUpdatePacket.MagnetCooldownUpdateMessage.class, true, false);
		registerMessage(MagnetItemUpdatePacket.class, MagnetItemUpdatePacket.MagnetItemUpdateMessage.class, false, true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void registerMessage(Class packet, Class message, boolean client, boolean server){
		if(client) net.registerMessage(packet, message, nextPacketId, Side.CLIENT);
		if(server) net.registerMessage(packet, message, nextPacketId, Side.SERVER);
		nextPacketId++;
	}
}