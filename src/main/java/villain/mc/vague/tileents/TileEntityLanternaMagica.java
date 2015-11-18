package villain.mc.vague.tileents;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
	private static final int MINIMUM_SIZE = 1;
	private static final int MAXIMUM_SIZE = 64;	
	private static final int MAX_OFFSET = 16;	
	
	private static final ArrayList<BlockPlaceInfo> places = new ArrayList<BlockPlaceInfo>();
	
	private int projectionHeight = DEFAULT_SIZE;
	private int projectionWidth = DEFAULT_SIZE;
	private int projectionDepth = DEFAULT_SIZE;
	private int projectionOffsetX = 0;
	private int projectionOffsetY = DEFAULT_SIZE / 2;
	private int projectionOffsetZ = 0;
	
	private ItemStack slideStack;
	
	private boolean recording = false;
	private int timeSinceRecordingBegan = 0;

	public TileEntityLanternaMagica(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void expand(ForgeDirection dir){
		switch(dir){
			case NORTH:
				projectionDepth += 2;
				break;
			case SOUTH:
				projectionDepth -= 2;
				break;
			case EAST:
				projectionWidth -= 2;
				break;
			case WEST:
				projectionWidth += 2;
				break;
			case UP:
				projectionHeight += 2;
				break;
			case DOWN:
				projectionHeight -= 2;
				break;
			case UNKNOWN:
				// No-Op
				break;
		}
		
		if(projectionWidth < MINIMUM_SIZE) projectionWidth = MINIMUM_SIZE;
		else if(projectionWidth > MAXIMUM_SIZE) projectionWidth = MAXIMUM_SIZE;
		
		if(projectionHeight < MINIMUM_SIZE) projectionHeight = MINIMUM_SIZE;
		else if(projectionHeight > MAXIMUM_SIZE) projectionHeight = MAXIMUM_SIZE;
		
		if(projectionDepth < MINIMUM_SIZE) projectionDepth = MINIMUM_SIZE;
		else if(projectionDepth > MAXIMUM_SIZE) projectionDepth = MAXIMUM_SIZE;
	}
	
	public void shift(ForgeDirection dir){
		switch(dir){
			case NORTH:
				projectionOffsetZ -= 1;
				break;
			case SOUTH:
				projectionOffsetZ += 1;
				break;
			case EAST:
				projectionOffsetX += 1;
				break;
			case WEST:
				projectionOffsetX -= 1;
				break;
			case UP:
				projectionOffsetY += 1;
				break;
			case DOWN:
				projectionOffsetY -= 1;
				break;
			case UNKNOWN:
				// No-Op
				break;
		}
		
		if(projectionOffsetX > MAX_OFFSET) projectionOffsetX = MAX_OFFSET;
		else if(projectionOffsetX < -MAX_OFFSET) projectionOffsetX = -MAX_OFFSET;
		
		if(projectionOffsetY > MAX_OFFSET) projectionOffsetY = MAX_OFFSET;
		else if(projectionOffsetY < -MAX_OFFSET) projectionOffsetY = -MAX_OFFSET;
		
		if(projectionOffsetZ > MAX_OFFSET) projectionOffsetZ = MAX_OFFSET;
		else if(projectionOffsetZ < -MAX_OFFSET) projectionOffsetZ = -MAX_OFFSET;
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
		//LogHelper.info("places size: " +places.size());
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
		nbt.setInteger("projHeight", projectionHeight);
		nbt.setInteger("projWidth", projectionWidth);
		nbt.setInteger("projDepth", projectionDepth);
		nbt.setInteger("projOffsetX", projectionOffsetX);
		nbt.setInteger("projOffsetY", projectionOffsetY);
		nbt.setInteger("projOffsetZ", projectionOffsetZ);
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
		projectionHeight = nbt.getInteger("projHeight");
		projectionWidth = nbt.getInteger("projWidth");
		projectionDepth = nbt.getInteger("projDepth");
		projectionOffsetX = nbt.getInteger("projOffsetX");
		projectionOffsetY = nbt.getInteger("projOffsetY");
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

	public int getProjectionHeight() {
		return projectionHeight;
	}

	public int getProjectionWidth() {
		return projectionWidth;
	}

	public int getProjectionDepth() {
		return projectionDepth;
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
	
	public void setInitialDirection(int metaDir){
		switch(metaDir){
			case 0:
				projectionOffsetZ = -((DEFAULT_SIZE / 2) + 1);
				break;
			case 1:
				projectionOffsetX = (DEFAULT_SIZE / 2) + 1;
				break;
			case 2:
				projectionOffsetZ = (DEFAULT_SIZE / 2) + 1;
				break;
			case 3:
				projectionOffsetX = -((DEFAULT_SIZE / 2) + 1);
				break;
		}
	}
	
	public ItemStack getSlideStack(){
		return slideStack;
	}
	
	public void setSlideStack(ItemStack slideStack){
		this.slideStack = slideStack;
	}
	
	public int getTimeSinceRecordingStarted(){
		return timeSinceRecordingBegan;
	}
	
	public int getZoneMinX(){
		return xCoord + projectionOffsetX - (projectionWidth / 2);
	}
	
	public int getZoneMaxX(){
		return xCoord + projectionOffsetZ + (projectionWidth / 2) + 1;
	}
	
	public int getZoneMinY(){
		return yCoord + projectionOffsetY - (projectionHeight / 2);
	}
	
	public int getZoneMaxY(){
		return yCoord + projectionOffsetY + (projectionHeight / 2) + 1;
	}
	
	public int getZoneMinZ(){
		return zCoord + projectionOffsetZ - (projectionDepth / 2);
	}
	
	public int getZoneMaxZ(){
		return zCoord + projectionOffsetZ + (projectionDepth / 2) + 1;
	}
	
	@SubscribeEvent
	public void playerInteractEvent(net.minecraftforge.event.entity.player.PlayerInteractEvent event){
		if(!event.world.isRemote){
			if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() instanceof ItemBlock &&
					event.action == Action.RIGHT_CLICK_BLOCK){
				// Player is placing a block
				BlockPos bPos = BlockHelper.blockPlaceNewPosition(event.x, event.y, event.z, event.face);
				BlockPlaceInfo bpi = new BlockPlaceInfo(bPos.getX(), bPos.getY(), bPos.getZ(), event.entityPlayer.getDisplayName(),				
						((ItemBlock)event.entityPlayer.getHeldItem().getItem()).field_150939_a.getUnlocalizedName());
					if(!places.contains(bpi)){
					places.add(bpi);
				}
			}
		}
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