package villain.mc.vague.container;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import villain.mc.vague.items.ItemMagnet;
import villain.mc.vague.utils.LogHelper;
import villain.mc.vague.utils.MagnetBlackListItem;

public class ContainerMagnet extends Container {

	private ItemStack magnetStack;
	private ArrayList<MagnetBlackListItem> blackListItems;
	
	public ContainerMagnet(InventoryPlayer inventoryPlayer, int magnetSlot){
		super();
		
		// Find magnetStack
		magnetStack = inventoryPlayer.getStackInSlot(magnetSlot);
		
		int slotID = 0;
		
		// Blacklist
		NBTTagList tagList = magnetStack.stackTagCompound.getTagList(ItemMagnet.TAG_BLACKLIST_TAG, NBT.TAG_COMPOUND);
		blackListItems = new ArrayList<MagnetBlackListItem>();
		for(int i = 0; i < tagList.tagCount(); i++){
			// Item
			MagnetBlackListItem item = new MagnetBlackListItem(tagList.getCompoundTagAt(i));
			blackListItems.add(item);
			
			// Slot
			addSlotToContainer(new Slot(null, i, 8, 8 + (i * 17)));
			
		}
		
		// Hot bar slots
		for(int i = 0; i < 9; i++){
			addSlotToContainer(new Slot(inventoryPlayer, slotID++, 8 + i * 18, 142));
		}
		
		// Inventory Slots
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(inventoryPlayer, slotID++, 8 + j * 18, 84 + i * 18));
			}
		}
	}
	
	public MagnetBlackListItem addBlackListItem() {
		// Item
		MagnetBlackListItem item = new MagnetBlackListItem();
		blackListItems.add(item);
		
		// Slot
		//addSlotToContainer(new GhostSlot(null, blackListSlots.size() + 1, 8, blackListSlots.size() * 17));
		
		return item;
	}
	
	public void removeBlackListItem(int i){
		blackListItems.remove(i);
		LogHelper.info("Item removed");
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return true;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		super.addCraftingToCrafters(iCrafting);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int newValue) {
		super.updateProgressBar(id, newValue);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotID) {
		return null;
	}
	
	public int getBlacklistItemCount(){
		return blackListItems.size();
	}
	
	public MagnetBlackListItem getBlackListItem(int i){
		return blackListItems.get(i);
	}

	
}