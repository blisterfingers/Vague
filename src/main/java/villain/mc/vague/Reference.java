package villain.mc.vague;

public class Reference {

	public static final String MOD_ID = "vague";
	public static final String MOD_NAME = "V.A.G.U.E";
	public static final String MOD_VERSION = "@VERSION@";
	
	public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
	
	public static final String CLIENT_PROXY_CLASS = "villain.mc.vague.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "villain.mc.vague.proxy.ServerProxy";
	
	public enum GUIs {
		MAGNET
	}
	
	public static final String GUIIMAGE_MAGNET = "textures/gui/magnet.png";
}