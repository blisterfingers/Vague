package villain.mc.vague.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import villain.mc.vague.utils.LogHelper;

public class InventoryMagnetBlacklist implements IInventory {

	public static final int NUM_SLOTS = 20;

	private String name = "Magnet Blacklist";
	private ItemStack[] inventory = new ItemStack[NUM_SLOTS];
	private ItemStack magnetStack;

	public InventoryMagnetBlacklist(ItemStack magnetStack) {
		super();
		
		this.magnetStack = magnetStack;

		// Read inventory contents from NBT
		if(!magnetStack.hasTagCompound()){
			magnetStack.setTagCompound(new NBTTagCompound());
		}
		readFromNBT(magnetStack.getTagCompound());
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
				markDirty();
			}
			else {
				inventory[slot] = null;
			}
		}
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		inventory[slot] = null;
		return stack;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()){
			stack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}
	
	@Override
	public String getInventoryName() {
		return name;
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void markDirty() {
		for(int i = 0; i < getSizeInventory(); i++){
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0){
				inventory[i] = null;
			}
		}
		
		writeToNBT(magnetStack.getTagCompound());
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
	
	public void readFromNBT(NBTTagCompound nbt){
		NBTTagList tagList = nbt.getTagList("ItemInventory", NBT.TAG_COMPOUND);
		
		for(int i = 0; i < tagList.tagCount(); i++){
			NBTTagCompound compound = tagList.getCompoundTagAt(i);
			int slot = compound.getInteger("slot");
			
			if(slot >= 0 && slot < getSizeInventory()){
				inventory[slot] = ItemStack.loadItemStackFromNBT(compound);				
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		NBTTagList tagList = new NBTTagList();
		
		for(int i = 0; i < getSizeInventory(); i++){
			if(getStackInSlot(i) != null){
				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("slot", i);
				getStackInSlot(i).writeToNBT(compound);				
				tagList.appendTag(compound);
			}
		}
		nbt.setTag("ItemInventory", tagList);
	}
}