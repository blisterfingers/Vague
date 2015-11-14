package villain.mc.vague.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import villain.mc.vague.entities.EntityGrenade;
import villain.mc.vague.rendering.RenderGrenade;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();

		// Rendering
		registerRenderers();
	}
	
	private static void registerRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
	}
}