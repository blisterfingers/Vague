package villain.mc.vague.items;

import java.util.List;

import codechicken.core.ServerUtils;
import codechicken.lib.vec.Vector3;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import villain.mc.vague.Reference;
import villain.mc.vague.Vague;
import villain.mc.vague.net.MagnetCooldownUpdatePacket;
import villain.mc.vague.net.PacketHandler;
import villain.mc.vague.utils.EntityHelper;
import villain.mc.vague.utils.ItemNBTHelper;

public class ItemMagnet extends ItemBase {
	
	public static void addItemToCooldownList(ItemStack magnetStack, int itemID, long worldTime){
		// Ensure that the magnet has an NBT tag
		ItemNBTHelper.checkAndCreateNBT(magnetStack);
		
		// Get the list
		NBTTagList tagList = magnetStack.stackTagCompound.getTagList(TAG_COOLDOWNLIST_TAG, NBT.TAG_COMPOUND);
		
		// Add this item
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("id", itemID);
		tag.setLong("time", worldTime);
		
		// Add to list
		tagList.appendTag(tag);
		
		// Set
		magnetStack.stackTagCompound.setTag(TAG_COOLDOWNLIST_TAG, tagList);
	}
	
	private static final int DEFAULT_RADIUS = 10;
	private static final long TOSSED_COOLDOWN = 100l;
	private static final float PULL_POWER = 0.8f;
	
	private static final String TAG_ACTIVE_BOOL = "active";
	private static final String TAG_COOLDOWNLIST_TAG = "cooldownlist";
	public static final String TAG_BLACKLISTINVENTORY = "blacklistinventory";
	public static final String TAG_BLACKLISTFLAGS = "blacklistflags";

	private IIcon iconActive;
	
	public ItemMagnet(){
		super("magnet", 1, true);
		MinecraftForge.EVENT_BUS.register(this);		
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		checkAndCreateStackTag(itemStack);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "magnet-default");
		iconActive = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "magnet-active");
	}
	
	@Override
	public IIcon getIcon(ItemStack itemStack, int pass) {
		checkAndCreateStackTag(itemStack);
		return itemStack.stackTagCompound.getBoolean(TAG_ACTIVE_BOOL) ? iconActive : itemIcon;
	}
	
	@Override
	public IIcon getIcon(ItemStack itemStack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		checkAndCreateStackTag(itemStack);
		return itemStack.stackTagCompound.getBoolean(TAG_ACTIVE_BOOL) ? iconActive : itemIcon;
	}
	
	@Override
	public IIcon getIconIndex(ItemStack itemStack) {
		checkAndCreateStackTag(itemStack);
		return itemStack.stackTagCompound.getBoolean(TAG_ACTIVE_BOOL) ? iconActive : itemIcon;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		if(entityPlayer.isSneaking()){
			if(!world.isRemote){
				entityPlayer.openGui(Vague.instance, Reference.GUIs.MAGNET.ordinal(), world, 0, 0, 0);
			}
		}
		else {
			// Toggle state
			toggleActive(itemStack);
		}
		return itemStack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean b) {
		super.onUpdate(itemStack, world, entity, i, b);
		
		checkAndCreateStackTag(itemStack);
		boolean active = itemStack.stackTagCompound.getBoolean(TAG_ACTIVE_BOOL);
		if(active){
			// Find all items within range.
			@SuppressWarnings("unchecked")
			List<EntityItem> entitiesWithinAABB = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					entity.posX - DEFAULT_RADIUS, entity.posY - DEFAULT_RADIUS,	entity.posZ - DEFAULT_RADIUS, 
					entity.posX + DEFAULT_RADIUS, entity.posY + DEFAULT_RADIUS, entity.posZ + DEFAULT_RADIUS));
			
			// Get player's position
			Vector3 playerPos = new Vector3(entity.posX, entity.posY -(world.isRemote ? 1.62 : 0) + 0.75,
					entity.posZ);
			
			for(EntityItem entItem : entitiesWithinAABB){
				if(canPull(itemStack, entItem)){					
					EntityHelper.setEntityMotionFromVector(entItem, playerPos, PULL_POWER);
				}
			}
		}
		
		// Remove old items from the list
		removeOldItemsFromCooldownList(itemStack, world.getWorldTime());
		
		// GUI Update
		/*if(!world.isRemote && entity instanceof EntityPlayer){
			EntityPlayerMP player = (EntityPlayerMP)entity;
			if(player.openContainer != null && player.openContainer instanceof ContainerMagnet && ((ContainerMagnet)player.openContainer).needsSaving()){
				((ContainerMagnet)player.openContainer).save();
			}
		}*/
	}
	
	private boolean canPull(ItemStack magnetStack, EntityItem item){
		if(item.isDead || item.age < 20){
			return false;
		}
		
		// Does this magnet have nbt?
		if(magnetStack.stackTagCompound == null){
			return true;
		}
		
		// Does this magnet have a cooldown list?
		NBTTagList cooldownList = magnetStack.stackTagCompound.getTagList(TAG_COOLDOWNLIST_TAG, NBT.TAG_COMPOUND);
		//NBTTagList blackList = magnetStack.stackTagCompound.getTagList(TAG_BLACKLIST_TAG, NBT.TAG_COMPOUND);
				
		// TODO Check blacklist
		
		// If this item's UUID is in the list, it can't be pulled yet.
		for(int i = 0; i < cooldownList.tagCount(); i++){
			NBTTagCompound compound = cooldownList.getCompoundTagAt(i);
			int id = compound.getInteger("id");
			if(item.getEntityId() == id){
				return false;
			}
		}
				
		return true;
	}
	
	private void checkAndCreateStackTag(ItemStack itemStack){
		if(itemStack.stackTagCompound == null){
			itemStack.stackTagCompound = new NBTTagCompound();
			itemStack.stackTagCompound.setBoolean(TAG_ACTIVE_BOOL, false);
		}
	}
	
	private void toggleActive(ItemStack itemStack){
		checkAndCreateStackTag(itemStack);
		itemStack.stackTagCompound.setBoolean(TAG_ACTIVE_BOOL, !itemStack.stackTagCompound.getBoolean(TAG_ACTIVE_BOOL));
	}
	
	// Events
	
	@SubscribeEvent
	public void itemToss(ItemTossEvent event){
		// Only fires server side
		
		// If this player has a magnet
		ItemStack magnetStack = null;
		int magnetSlot = -1;
		for(int i = 0; i < event.player.inventory.mainInventory.length; i++){
			if(event.player.inventory.mainInventory[i] != null && event.player.inventory.mainInventory[i].getItem() == this){
				magnetSlot = i;
				magnetStack = event.player.inventory.mainInventory[i];
			}
		}
		
		if(magnetStack == null){
			// Player doesn't have a magnet
			return;
		}
		
		// Add item to magnet's cooldown list
		addItemToCooldownList(magnetStack, event.entityItem.getEntityId(), event.player.worldObj.getWorldTime());
		
		// Update client's magnet nbt
		EntityPlayerMP playerMP = ServerUtils.getPlayer(event.player.getDisplayName());
		PacketHandler.net.sendTo(new MagnetCooldownUpdatePacket.MagnetCooldownUpdateMessage(magnetSlot, event.entityItem.getEntityId()), playerMP);
	}
	
	private void removeOldItemsFromCooldownList(ItemStack magnetStack, long worldTime){
		if(magnetStack.stackTagCompound == null) return;
		
		NBTTagList tagList = magnetStack.stackTagCompound.getTagList(TAG_COOLDOWNLIST_TAG, NBT.TAG_COMPOUND);
		
		// Check through items
		for(int i = 0; i < tagList.tagCount(); i++){
			NBTTagCompound compound = tagList.getCompoundTagAt(i);
			long time = compound.getLong("time");
			if(worldTime - time > TOSSED_COOLDOWN){
				tagList.removeTag(i);
			}
		}
	}
}