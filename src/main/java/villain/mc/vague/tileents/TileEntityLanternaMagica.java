package villain.mc.vague.tileents;

import java.util.ArrayList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import villain.mc.vague.data.BlockPos;
import villain.mc.vague.data.LanternaBlockData;
import villain.mc.vague.items.ItemLanternSlide;
import villain.mc.vague.utils.BlockHelper;
import villain.mc.vague.utils.LogHelper;

public class TileEntityLanternaMagica extends TileEntity {
	
	private static final int DEFAULT_SIZE = 5;	
	
	private static final ArrayList<BlockPlaceInfo> places = new ArrayList<BlockPlaceInfo>();
	
	private int projectionOffsetX = 0;
	private int projectionOffsetY = DEFAULT_SIZE / 2;
	private int projectionOffsetZ = 0;
	
	private ItemStack slideStack;
	
	private boolean recording = false;
	private int timeSinceRecordingBegan = 0;

	public TileEntityLanternaMagica(){
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	public boolean hasSlideStack(){
		return slideStack != null;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(hasSlideStack()){
			if(worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0){
				// Playback
				updatePlayback();
			}
			else if(!recording){
				// Recording
				recording = true;
				timeSinceRecordingBegan = 0;
			}
			else{
				updateRecording();			
			}
		}
	}
	
	private void updateRecording(){
		timeSinceRecordingBegan++;
		
		// Add any place events to this item if they're in our area
		if(places.size() > 0){			
			/*BlockPlaceInfo bpi = places.get(0);
			ArrayList<String> owners = ItemLanternSlide.getOwners(slideStack);
			// Is the player recorded in this slide the owner of this placement?
			if(owners.contains(bpi.playerName)){
				// Is this placement in this entity's zone
				if(bpi.x >= getZoneMinX() && bpi.x < getZoneMaxX() &&
						bpi.y >= getZoneMinY() && bpi.y < getZoneMaxY() &&
						bpi.z >= getZoneMinZ() && bpi.z < getZoneMaxZ()){
					
					// Remove the place from the list
					places.remove(0);
					
					// Add this placement to the recording
					ItemLanternSlide.addBlockData(slideStack, new LanternaBlockData(timeSinceRecordingBegan,
							bpi.blockName, bpi.x, bpi.y, bpi.z));
					
					// Log
					LogHelper.info("Block recorded.");
				}
			}*/
			
			// Get the next bpi
			BlockPlaceInfo bpi = places.get(0);
			
			// Is this placement in this entity's zone
			LogHelper.info("bpi.z: " + bpi.z + ", zoneMinZ: " + getZoneMinZ() + ", zoneMaxZ: " + getZoneMaxZ());
			
			if(bpi.x >= getZoneMinX() && bpi.x < getZoneMaxX() &&
					bpi.y >= getZoneMinY() && bpi.y < getZoneMaxY() &&
					bpi.z >= getZoneMinZ() && bpi.z < getZoneMaxZ()){
				
				// Remove the place from the list
				places.remove(0);
				
				// Add this placement to the recording
				ItemLanternSlide.addBlockData(slideStack, new LanternaBlockData(timeSinceRecordingBegan,
						bpi.blockName, bpi.x, bpi.y, bpi.z));
				
				// Log
				LogHelper.info("Block recorded.");
			}
		}
	}
	
	private void updatePlayback(){
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(slideStack != null){
			NBTTagCompound slideTag = new NBTTagCompound();
			slideStack.writeToNBT(slideTag);
			nbt.setTag("slide", slideTag);
			
			nbt.setBoolean("recording", recording);
			nbt.setInteger("recordingTime", timeSinceRecordingBegan);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		projectionOffsetZ = nbt.getInteger("projOffsetZ");
		if(nbt.hasKey("slide")){
			slideStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("slide"));
			
			recording = nbt.getBoolean("recording");
			timeSinceRecordingBegan = nbt.getInteger("recordingTime");
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		int minX = Math.min(xCoord, getZoneMinX());
		int minY = Math.min(yCoord, getZoneMinY());
		int minZ = Math.min(zCoord, getZoneMinZ());
		
		int maxX = Math.max(xCoord, getZoneMaxX());
		int maxY = Math.max(yCoord, getZoneMaxY());
		int maxZ = Math.max(zCoord, getZoneMaxZ());
		
		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	private void updateOffset(int metaDir){
		if(!hasSlideStack()) return;
		
		int size = ((ItemLanternSlide)slideStack.getItem()).getSize();
		
		switch(metaDir){
		case 0:
			projectionOffsetZ = -((size / 2) + 1);
			break;
		case 1:
			projectionOffsetX = (size / 2) + 1;
			break;
		case 2:
			projectionOffsetZ = (size / 2) + 1;
			break;
		case 3:
			projectionOffsetX = -((size / 2) + 1);
			break;
	}
	}

	public int getProjectionSize() {
		return hasSlideStack() ? ((ItemLanternSlide)slideStack.getItem()).getSize() : 0;
	}

	public int getProjectionOffsetX() {
		return projectionOffsetX;
	}

	public int getProjectonOffsetY() {
		return projectionOffsetY;
	}

	public int getProjectionOffsetZ() {
		return projectionOffsetZ;
	}
	
	public ItemStack getSlideStack(){
		return slideStack;
	}
	
	public void setSlideStack(ItemStack slideStack){
		this.slideStack = slideStack;
		updateOffset(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}
	
	public int getTimeSinceRecordingStarted(){
		return timeSinceRecordingBegan;
	}
	
	public int getZoneMinX(){
		return hasSlideStack() ? xCoord + projectionOffsetX - (((ItemLanternSlide)slideStack.getItem()).getSize() / 2) : 0;
	}
	
	public int getZoneMaxX(){
		return hasSlideStack() ? xCoord + projectionOffsetZ + (((ItemLanternSlide)slideStack.getItem()).getSize() / 2) + 1 : 0;
	}
	
	public int getZoneMinY(){
		return hasSlideStack() ? yCoord + projectionOffsetY - (((ItemLanternSlide)slideStack.getItem()).getSize() / 2) : 0;
	}
	
	public int getZoneMaxY(){
		return hasSlideStack() ? yCoord + projectionOffsetY + (((ItemLanternSlide)slideStack.getItem()).getSize() / 2) + 1 : 0;
	}
	
	public int getZoneMinZ(){
		return hasSlideStack() ? zCoord + projectionOffsetZ - (((ItemLanternSlide)slideStack.getItem()).getSize() / 2) : 0;
	}
	
	public int getZoneMaxZ(){
		return hasSlideStack() ? zCoord + projectionOffsetZ + (((ItemLanternSlide)slideStack.getItem()).getSize() / 2) + 1 : 0;
	}
	
	@SubscribeEvent
	public void playerInteractEvent(net.minecraftforge.event.entity.player.PlayerInteractEvent event){
		if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() instanceof ItemBlock &&
				event.action == Action.RIGHT_CLICK_BLOCK){
			// Player is placing a block
			BlockPos bPos = BlockHelper.blockPlaceNewPosition(event.x, event.y, event.z, event.face);
			BlockPlaceInfo bpi = new BlockPlaceInfo(bPos.getX(), bPos.getY(), bPos.getZ(), event.entityPlayer.getDisplayName(),				
					((ItemBlock)event.entityPlayer.getHeldItem().getItem()).field_150939_a.getUnlocalizedName());
				if(!places.contains(bpi)){
				places.add(bpi);
				LogHelper.info("new size: " + places.size());
			}
		}
	}
	
	@SubscribeEvent
	public void tickEvent(TickEvent.WorldTickEvent event){
		
	}
	
	private static class BlockPlaceInfo {
		public int x, y, z;
		public String playerName;
		public String blockName;
		public BlockPlaceInfo(int x, int y, int z, String playerName, String blockName){
			this.x = x;
			this.y = y;
			this.z = z;
			this.playerName = playerName;
			this.blockName = blockName;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof BlockPlaceInfo){
				BlockPlaceInfo toCompare = (BlockPlaceInfo)obj;
				if(x == toCompare.x && y == toCompare.y && z == toCompare.z &&
						playerName.equals(toCompare.playerName) && blockName.equals(toCompare.blockName)){
					return true;
				}
			}
			return false;
		}
	}
}