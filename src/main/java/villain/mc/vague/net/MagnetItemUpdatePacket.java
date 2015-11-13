package villain.mc.vague.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import villain.mc.vague.items.ItemMagnet;
import villain.mc.vague.net.MagnetItemUpdatePacket.MagnetItemUpdateMessage;
import villain.mc.vague.utils.ItemNBTHelper;

public class MagnetItemUpdatePacket implements IMessageHandler<MagnetItemUpdateMessage, IMessage>{

	@Override
	public IMessage onMessage(MagnetItemUpdateMessage message, MessageContext ctx) {
		if(ctx.side.isServer()){
			ItemStack magnetStack = ctx.getServerHandler().playerEntity.getHeldItem();
			
			NBTTagCompound flags = ItemNBTHelper.getCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS);
			flags.setBoolean("slot" + message.inventorySlot + "meta", message.useMeta);
			flags.setBoolean("slot" + message.inventorySlot + "nbt", message.useNBT);
			flags.setBoolean("slot" + message.inventorySlot + "trash", message.useTrash);
			ItemNBTHelper.setCompound(magnetStack, ItemMagnet.TAG_BLACKLISTFLAGS, flags);
		}
		
		// return no message
		return null;
	}
	
	public static class MagnetItemUpdateMessage implements IMessage {
		
		private int inventorySlot;
		private boolean useMeta, useNBT, useTrash;
		
		public MagnetItemUpdateMessage(){ }
		
		public MagnetItemUpdateMessage(int inventorySlot, boolean useMeta, boolean useNBT, boolean useTrash){
			this.inventorySlot = inventorySlot;
			this.useMeta = useMeta;
			this.useNBT = useNBT;
			this.useTrash = useTrash;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			inventorySlot = buf.readInt();
			useMeta = buf.readBoolean();
			useNBT = buf.readBoolean();
			useTrash = buf.readBoolean();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(inventorySlot);
			buf.writeBoolean(useMeta);
			buf.writeBoolean(useNBT);
			buf.writeBoolean(useTrash);
		}
	}
}