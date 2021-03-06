package villain.mc.vague;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import villain.mc.vague.gui.GuiHandler;
import villain.mc.vague.net.PacketHandler;
import villain.mc.vague.proxy.IProxy;
import villain.mc.vague.worldgen.WorldGen;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Vague {

	@Mod.Instance
	public static Vague instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		// Proxy
		proxy.preInit();
		
		// Register
		Init.initAll();
		
		// GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		// Net
		PacketHandler.initPackets();
		
		// WorldGen
		GameRegistry.registerWorldGenerator(new WorldGen(), 128);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		proxy.postInit();
	}
}