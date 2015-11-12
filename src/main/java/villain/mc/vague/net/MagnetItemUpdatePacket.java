package villain.mc.vague.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import villain.mc.vague.container.ContainerMagnet;
import villain.mc.vague.inventory.InventoryMagnetBlacklist;
import villain.mc.vague.net.MagnetItemUpdatePacket.MagnetItemUpdateMessage;

public class MagnetItemUpdatePacket implements IMessageHandler<MagnetItemUpdateMessage, IMessage>{

	@Override
	public IMessage onMessage(MagnetItemUpdateMessage message, MessageContext ctx) {
		if(ctx.side.isServer()){
			InventoryMagnetBlacklist inventory = ((ContainerMagnet)Minecraft.getMinecraft().thePlayer.openContainer).getMagnetInventory();
			inventory.setUseMeta(message.inventorySlot, message.useMeta);
			inventory.setUseNBT(message.inventorySlot, message.useNBT);
		}
		
		// return no message
		return null;
	}
	
	public static class MagnetItemUpdateMessage implements IMessage {
		
		private int inventorySlot;
		private boolean useMeta, useNBT;
		
		public MagnetItemUpdateMessage(){ }
		
		public MagnetItemUpdateMessage(int inventorySlot, boolean useMeta, boolean useNBT){
			this.inventorySlot = inventorySlot;
			this.useMeta = useMeta;
			this.useNBT = useNBT;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			inventorySlot = buf.readInt();
			useMeta = buf.readBoolean();
			useNBT = buf.readBoolean();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(inventorySlot);
			buf.writeBoolean(useMeta);
			buf.writeBoolean(useNBT);
		}
	}
}