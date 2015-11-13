package villain.mc.vague.container;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import villain.mc.vague.gui.GuiMagnet;
import villain.mc.vague.inventory.InventoryMagnetBlacklist;

public class ContainerMagnet extends Container {

	private InventoryMagnetBlacklist inventoryMagnet;
	
	private final ArrayList<Slot> blacklistSlots = new ArrayList<Slot>();
	
	public ContainerMagnet(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryMagnetBlacklist inventoryMagnet){
		super();
		
		this.inventoryMagnet = inventoryMagnet;
		
		int slotID = 0;
		// Hot bar slots
		for(int i = 0; i < 9; i++){
			addSlotToContainer(new Slot(inventoryPlayer, slotID++, 8 + i * 18, 169));
		}
		
		// Inventory Slots
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(inventoryPlayer, slotID++, 8 + j * 18, 111 + i * 18));
			}
		}
		
		// Add slots for all blacklist items
		slotID = 0;
		for(int i = 0; i < inventoryMagnet.getSizeInventory(); i++){
			int slotX = i < 5 ? 8 : i < 10 ? 89 : 8 - 1000;
			int slotY = 8 + (17 * (i % 5));
			blacklistSlots.add(addSlotToContainer(new Slot(inventoryMagnet, slotID++, slotX, slotY)));
		}
	}
	
	public void setPage(int page){
		for(int i = 0; i < blacklistSlots.size(); i++){
			int x;
			if(i >= GuiMagnet.SLOTS_PER_PAGE * page && i < (GuiMagnet.SLOTS_PER_PAGE * page) + GuiMagnet.SLOTS_PER_PAGE){
				x = i % GuiMagnet.SLOTS_PER_PAGE < 5 ? 8 : 89;
			}
			else {
				x = -1000;
			}
			
			blacklistSlots.get(i).xDisplayPosition = x;
			
			/*blacklistSlots.get(i).xDisplayPosition = 
					i >= (GuiMagnet.SLOTS_PER_PAGE * page) && 
					i < (GuiMagnet.SLOTS_PER_PAGE * page) + GuiMagnet.SLOTS_PER_PAGE ? 8 : 8 - 1000;*/
		}
	}
		
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return inventoryMagnet.isUseableByPlayer(entityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotID) {
		return null;
	}
	
	@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player) {
		// Prevent interacting with the item that opened the inventory
		if(slotID >= 0 && getSlot(slotID) != null && getSlot(slotID).getStack() == player.getHeldItem()){
			return null;
		}
		return super.slotClick(slotID, buttonPressed, flag, player);
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean backwards){
		boolean flag1 = false;
		int k = (backwards ? end - 1 : start);
		Slot slot;
		ItemStack itemStack1;
		
		if(stack.isStackable()){
			while(stack.stackSize > 0 && (!backwards && k < end || backwards && k >= start)){
				slot = (Slot)inventorySlots.get(k);
				itemStack1 = slot.getStack();
				
				if(!slot.isItemValid(stack)){
					k += (backwards ? -1 : 1);
					continue;
				}
				
				if(itemStack1 != null && itemStack1.getItem() == stack.getItem() &&
						(!stack.getHasSubtypes() || stack.getItemDamage() == itemStack1.getItemDamage()) && ItemStack.areItemStacksEqual(stack, itemStack1)){
					int l = itemStack1.stackSize + stack.stackSize;
					
					if(l <= stack.getMaxStackSize() && l <= slot.getSlotStackLimit()){
						stack.stackSize = 0;
						itemStack1.stackSize = 1;
						inventoryMagnet.markDirty();
						flag1 = true;
					}
					else if(itemStack1.stackSize < stack.getMaxStackSize() && l < slot.getSlotStackLimit()){
						stack.stackSize -= stack.getMaxStackSize() - itemStack1.stackSize;
						itemStack1.stackSize = stack.getMaxStackSize();
						inventoryMagnet.markDirty();
						flag1 = true;
					}
				}
				
				k += (backwards ? -1 : 1);
			}
		}
		
		if(stack.stackSize > 0){
			k = (backwards ? end - 1 : start);
			while(!backwards && k < end || backwards && k >= start){
				slot = (Slot)inventorySlots.get(k);
				itemStack1 = slot.getStack();
				
				if(!slot.isItemValid(stack)){
					k += (backwards ? -1 : 1);
					continue;
				}
				
				if(itemStack1 == null){
					int l = stack.stackSize;
					if(l <= slot.getSlotStackLimit()){
						slot.putStack(stack.copy());
						stack.stackSize = 0;
						inventoryMagnet.markDirty();
						flag1 = true;
						break;
					}
					else {
						putStackInSlot(k, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
						stack.stackSize -= slot.getSlotStackLimit();
						inventoryMagnet.markDirty();
						flag1 = true;
					}
				}
				
				k += (backwards ? -1 : 1);
			}
		}
		
		return flag1;
	}

	public InventoryMagnetBlacklist getMagnetInventory(){
		return inventoryMagnet;
	}
	
	public Slot getBlacklistSlot(int index){
		return blacklistSlots.get(index);
	}
}