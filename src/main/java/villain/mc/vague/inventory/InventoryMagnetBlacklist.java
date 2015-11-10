package villain.mc.vague.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import villain.mc.vague.utils.LogHelper;

public class InventoryMagnetBlacklist implements IInventory {

	public static final int NUM_SLOTS = 64;

	private String name = "Blacklist";
	private ItemStack[] inventory = new ItemStack[NUM_SLOTS];

	public InventoryMagnetBlacklist(ItemStack magnetStack) {
		super();

		// Read inventory contents from NBT
		LogHelper.info("Loading inventory...");
		if(magnetStack.hasTagCompound()){
			if(magnetStack.getTagCompound().hasKey("blacklist")){
				readFromNBT(magnetStack.getTagCompound().getCompoundTag("blacklist"));
			}
			else {
				LogHelper.info("Loading inventory: Magnet stack's tag doesn't have 'blacklist' entry.");
			}
		}
		else {
			LogHelper.info("Loading inventory: Magnet stack doesn't have tag");
		}
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount){
		ItemStack stack = getStackInSlot(slot);
		if(stack != null){
			if(stack.stackSize > amount){
				stack = stack.splitStack(amount);
				if(stack.stackSize == 0){
					setInventorySlotContents(slot, null);
				}
			}
			else {
				setInventorySlotContents(slot, null);
			}
			onInventoryChanged();
		}
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if(stack != null){
			setInventorySlotContents(slot, null);
		}
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()){
			stack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
	
	@Override
	public String getInventoryName() {
		return name;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return true;
	}
	
	@Override
	public void openInventory() {
		// NO-OP
	}
	
	@Override
	public void closeInventory() {
		// NO-OP
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public void markDirty() {
		// noop
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		LogHelper.info("Loading inventory.");
		NBTTagList tagList = nbt.getTagList("ItemInventory", NBT.TAG_COMPOUND);
		LogHelper.info("tag count: " + tagList);
		for(int i = 0; i < tagList.tagCount(); i++){
			NBTTagCompound compound = tagList.getCompoundTagAt(i);
			int b0 = compound.getInteger("slot");
			if(b0 >= 0 && b0 < getSizeInventory()){
				setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(compound));
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		LogHelper.info("Saving inventory");
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++){
			// only write stacks taht contain items
			if(getStackInSlot(i) != null){
				LogHelper.info("Found " + getStackInSlot(i).getDisplayName() + " in slot " + i);
				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("slot", i);
				getStackInSlot(i).writeToNBT(compound);
				tagList.appendTag(compound);
			}
		}
		LogHelper.info("Saving tag count: " + tagList.tagCount());
		nbt.setTag("ItemInventory", tagList);
	}
	
	private void onInventoryChanged(){
		for(int i = 0; i < getSizeInventory(); i++){
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0){
				setInventorySlotContents(i, null);
			}
		}
	}
	
	public int getNumberOfOccupiedSlots(){
		int count = 0;
		for(int i = 0; i < NUM_SLOTS; i++){
			if(getStackInSlot(i) != null){
				count++;
			}
		}
		return count;
	}
	
	public boolean addItem(ItemStack itemStack){
		int slot = findFirstOpenSlot();
		if(slot != -1){
			setInventorySlotContents(slot, itemStack);
			return true;
		}
		return false;
	}
	
	private int findFirstOpenSlot(){
		for(int i = 0; i < NUM_SLOTS; i++){
			if(getStackInSlot(i) == null) return i;
		}
		return -1;
	}
}