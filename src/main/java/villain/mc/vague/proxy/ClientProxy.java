package villain.mc.vague.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import villain.mc.vague.Init;
import villain.mc.vague.entities.EntityGrenade;
import villain.mc.vague.rendering.entities.RenderGrenade;
import villain.mc.vague.rendering.tiles.RendererLanternaMagica;
import villain.mc.vague.tileents.TileEntityLanternaMagica;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();

		// Rendering
		registerRenderers();
	}
	
	private static void registerRenderers(){
		// Entities
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
		
		// TileEntities
		RendererLanternaMagica lanternaRenderer = new RendererLanternaMagica();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLanternaMagica.class, lanternaRenderer);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Init.blockLanternaMagica), lanternaRenderer);
	}
}