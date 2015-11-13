package villain.mc.vague.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import villain.mc.vague.Reference.GUIs;
import villain.mc.vague.container.ContainerMagnet;
import villain.mc.vague.inventory.InventoryMagnetBlacklist;
import villain.mc.vague.utils.LogHelper;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == GUIs.MAGNET.ordinal()){
			return new ContainerMagnet(player, player.inventory, new InventoryMagnetBlacklist(player.getHeldItem()));
		}
		else {
			LogHelper.warn("Can't open GUI.. id not recognised.");
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == GUIs.MAGNET.ordinal()){
			return new GuiMagnet(new ContainerMagnet(player, player.inventory, new InventoryMagnetBlacklist(player.getHeldItem())), player.getHeldItem());
		}
		else {
			LogHelper.warn("Can't open GUI.. id not recognised.");
		}
		return null;
	}
}