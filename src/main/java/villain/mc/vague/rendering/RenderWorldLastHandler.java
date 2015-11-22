package villain.mc.vague.rendering;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import villain.mc.vague.Init;
import villain.mc.vague.utils.ItemNBTHelper;

public class RenderWorldLastHandler {

	@SubscribeEvent
	public void renderWorldLast(RenderWorldLastHandler event){
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		renderSurveyToolBounds(event, player);
	}
	
	private void renderSurveyToolBounds(RenderWorldLastHandler event, EntityClientPlayerMP player){
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == Init.itemSurveyTool){
			boolean first = ItemNBTHelper.hasTag(player.getHeldItem(), "first");
			boolean second = ItemNBTHelper.hasTag(player.getHeldItem(), "second");
			
			if(first){
				// Render first box
			}
			
			if(second){
				// Render second box
			}
			
			if(first && second){
				// Render bounds
				
			}
		}
	}
}