package villain.mc.vague.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import villain.mc.vague.Init;
import villain.mc.vague.items.ItemMagnet;
import villain.mc.vague.net.MagnetUpdatePacket.MagnetUpdateMessage;
import villain.mc.vague.utils.LogHelper;

public class MagnetUpdatePacket implements IMessageHandler<MagnetUpdateMessage, IMessage> {
	
	@Override
	public IMessage onMessage(MagnetUpdateMessage message, MessageContext ctx) {
		if(ctx.side.isClient()){
			ItemStack slotStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[message.magnetSlot];
			if(slotStack == null || slotStack.getItem() != Init.itemMagnet){
				// TODO WARNING
				LogHelper.warn("Could not find client player's magnet slot.");
				return null;
			}
			
			// Add information to magnet
			ItemMagnet.addItemToCooldownList(slotStack, message.itemID, Minecraft.getMinecraft().theWorld.getWorldTime());
		}
		
		// Return no message
		return null;
	}
	
	public static class MagnetUpdateMessage implements IMessage {
		
		public int magnetSlot;
		public int itemID;
		
		public MagnetUpdateMessage(){
			
		}
		
		public MagnetUpdateMessage(int magnetSlot, int itemID){
			this.magnetSlot = magnetSlot;
			this.itemID = itemID;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			magnetSlot = buf.readInt();
			itemID = buf.readInt();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(magnetSlot);
			buf.writeInt(itemID);
		}
	}	
}