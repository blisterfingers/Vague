package villain.mc.vague.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import villain.mc.vague.inventory.InventoryMagnetBlacklist;
import villain.mc.vague.utils.LogHelper;

public class ContainerMagnet extends Container {

	private ItemStack magnetStack;
	private InventoryMagnetBlacklist invMagnet;
	private boolean dirty = false;
	
	public ContainerMagnet(InventoryPlayer inventoryPlayer, ItemStack magnetStack){
		super();
		
		this.magnetStack = magnetStack;
		
		LogHelper.info("Container constructor");
		
		// Get inventory from magnet
		invMagnet = new InventoryMagnetBlacklist(magnetStack);
		
		int slotID = 0;
				
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
		
		// Add slots for all blacklist items
		slotID = 0;
		int occupiedSlots = invMagnet.getNumberOfOccupiedSlots();
		LogHelper.info("Container open occupied slots: " + occupiedSlots);
		for(int i = 0; i < occupiedSlots; i++){
			addSlotToContainer(new Slot(invMagnet, slotID++, 8, 8 + (17 * i))); 
		}
		
		// Add a slot for the empty 'new item' slot
		addSlotToContainer(new Slot(invMagnet, slotID++, 8, 8 + (17 * occupiedSlots)));
		
		
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
	
	@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player) {
		dirty = true;
		return super.slotClick(slotID, buttonPressed, flag, player);
	}
		
	public boolean needsSaving(){
		return dirty;
	}
		
	public void save(){
		LogHelper.info("Saving magnet blacklist");
		
		if(!magnetStack.hasTagCompound()){
			magnetStack.setTagCompound(new NBTTagCompound());
		}
		
		if(!magnetStack.getTagCompound().hasKey("blacklist")){
			magnetStack.getTagCompound().setTag("blacklist", new NBTTagCompound());
		}
		
		invMagnet.writeToNBT(magnetStack.getTagCompound().getCompoundTag("blacklist"));
		LogHelper.info("SAVED!");
		dirty = false;
	}
	
	public void removeItem(int id){
		invMagnet.setInventorySlotContents(id, null);
	}
	
	public int getBlacklistItemCount(){
		return invMagnet.getNumberOfOccupiedSlots();
	}
	
	public ItemStack getBlacklistItem(int id){
		return invMagnet.getStackInSlot(id);
	}
}