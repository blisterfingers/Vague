package villain.mc.vague.tileents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import villain.mc.vague.Init;
import villain.mc.vague.utils.MathHelper;
import villain.mc.vague.utils.RandomCollection;

public class TileEntityUncertaintyBorder extends TileEntityMultiblock {

	private static final int MAX_BORDER_SIZE = 16;
	private static final int ACTIONDELAY_MIN = 20;
	private static final int ACTIONDELAY_MAX = 60;
	
	private static final int ACTION_POP = 0;
	private static final int ACTION_SPAWNMOB = 1;
	private static final int ACTION_POTION = 2;
	private static final int ACTION_SPAWNITEM = 3;
	private static final int ACTION_LIGHTNING = 4;
	
	private static final double WEIGHT_POP = 10.0;
	private static final double WEIGHT_SPAWNMOB = 5.0;
	private static final double WEIGHT_POTION = 5.0;
	private static final double WEIGHT_SPAWNITEM = 2.0;
	private static final double WEIGHT_LIGHTNING = 0.5;
	
	// Multiblock Stuff
	private boolean isMaster = false;
	private int xLength, zLength;
	private boolean hasMaster = false;
	private int masterX, masterY, masterZ;
	
	// Update stuff
	private int count = 0;
	private int delay = ACTIONDELAY_MAX;
	private RandomCollection<Integer> weightCollection;
	
	public TileEntityUncertaintyBorder(){
		super();
	}
	
	@Override
	public void setWorldObj(World world) {
		super.setWorldObj(world);
		createWeightCollection();
	}
	
	@Override
	public boolean checkAndFormMultiblockStructure() {
		// Find the corner stone
		int xNegExtent = Integer.MAX_VALUE;
		int zNegExtent = Integer.MAX_VALUE;
		
		for(int x = 0; x < MAX_BORDER_SIZE; x++){
			for(int z = 0; z < MAX_BORDER_SIZE; z++){
				Block block = worldObj.getBlock(xCoord - x, yCoord, zCoord - z);
				if(block == Init.blockUncertaintyBorder){
					xNegExtent = xCoord - x;
					zNegExtent = zCoord - z;
				}
			}
		}
		
		// Run the check
		TileEntityUncertaintyBorder cornerstone = (TileEntityUncertaintyBorder)worldObj.getTileEntity(xNegExtent, yCoord, zNegExtent);
		if(cornerstone == null){
			//LogHelper.info("Could not find cornerstone");
			return false;
		}
		
		return cornerstone.checkMultiblockFromCornerstone();
	}
	
	public boolean checkMultiblockFromCornerstone(){
		// Check X wall
		int xLen = Integer.MAX_VALUE;
		for(int i = 0; i < MAX_BORDER_SIZE; i++){
			Block block = worldObj.getBlock(xCoord + i, yCoord, zCoord);
			if(block != Init.blockUncertaintyBorder){
				break;
			}
			xLen = i + 1;
		}
		
		int zLen = Integer.MAX_VALUE;
		for(int i = 0; i < MAX_BORDER_SIZE; i++){
			Block block = worldObj.getBlock(xCoord, yCoord, zCoord + i);
			if(block != Init.blockUncertaintyBorder){
				break;
			}
			zLen = i + 1;
		}
			
		// Check other X wall
		for(int i = 0; i < xLen; i++){
			Block block = worldObj.getBlock(xCoord + i, yCoord, zCoord + zLen - 1);
			if(block != Init.blockUncertaintyBorder){
				return false;
			}
		}
		
		// Check other Z wall
		for(int i = 0; i < zLen; i++){
			Block block = worldObj.getBlock(xCoord + xLen - 1, yCoord, zCoord + i);
			if(block != Init.blockUncertaintyBorder){
				return false;
			}
		}
		
		// set
		xLength = xLen;
		zLength = zLen;
				
		if(xLength < 3 || zLength < 3) return false;
		
		formMultiblockStructure();
		
		return true;
	}
	
	@Override
	protected void formMultiblockStructure() {
		// Set the cornerstone as master
		setAsMaster();
		
		// Set other blocks
		for(int i = 0; i < xLength; i++){
			TileEntityUncertaintyBorder tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(xCoord + i, yCoord, zCoord);
			tileEnt.setAsSlave(xCoord, yCoord, zCoord, xLength, zLength);
			worldObj.setBlockMetadataWithNotify(xCoord + i, yCoord, zCoord, 1, 3);
			
			tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(xCoord + i, yCoord, zCoord + zLength - 1);
			tileEnt.setAsSlave(xCoord, yCoord, zCoord, xLength, zLength);
			worldObj.setBlockMetadataWithNotify(xCoord + i, yCoord, zCoord + zLength - 1, 1, 3);
		}
		
		for(int i = 0; i < zLength; i++){
			TileEntityUncertaintyBorder tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(xCoord, yCoord, zCoord + i);
			tileEnt.setAsSlave(xCoord, yCoord, zCoord, xLength, zLength);
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord + i, 1, 3);
			
			tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(xCoord + xLength - 1, yCoord, zCoord + i);
			tileEnt.setAsSlave(xCoord, yCoord, zCoord, xLength, zLength);
			worldObj.setBlockMetadataWithNotify(xCoord + xLength - 1, yCoord, zCoord + i, 1, 3);
		}
	}	
	
	public void checkMultiblockAndDestroy(){
		for(int i = 0; i < xLength; i++){
			TileEntity tileEnt1 = worldObj.getTileEntity(masterX + i, masterY, masterZ);
			if(tileEnt1 == null || !(tileEnt1 instanceof TileEntityUncertaintyBorder)){
				destroyMultiblock();
				return;
			}
			
			TileEntity tileEnt2 = worldObj.getTileEntity(masterX + i, masterY, masterZ + zLength - 1);
			if(tileEnt2 == null || !(tileEnt2 instanceof TileEntityUncertaintyBorder)){
				destroyMultiblock();
				return;
			}
		}
		
		for(int i = 0; i < zLength; i++){
			TileEntity tileEnt1 = worldObj.getTileEntity(masterX, masterY, masterZ + i);
			if(tileEnt1 == null || !(tileEnt1 instanceof TileEntityUncertaintyBorder)){
				destroyMultiblock();
				return;
			}
			
			TileEntity tileEnt2 = worldObj.getTileEntity(masterX + xLength - 1, masterY, masterZ + i);
			if(tileEnt2 == null || !(tileEnt2 instanceof TileEntityUncertaintyBorder)){
				destroyMultiblock();
				return;
			}
		}
	}
	
	private void destroyMultiblock(){
		for(int i = 0; i < xLength; i++){
			TileEntityUncertaintyBorder tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(masterX + i, masterY, masterZ);
			if(tileEnt != null && (tileEnt.isMaster || tileEnt.isSlave())){
				tileEnt.clearMultiblock();
				worldObj.setBlockMetadataWithNotify(masterX + i, masterY, masterZ, 0, 3);
			}
			
			tileEnt = null;			
			tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(masterX + i, masterY, masterZ + zLength - 1);
			if(tileEnt != null && (tileEnt.isMaster || tileEnt.isSlave())){
				tileEnt.clearMultiblock();
				worldObj.setBlockMetadataWithNotify(masterX + i, masterY, masterZ + zLength - 1, 0, 3);
			}
		}
		
		for(int i = 0; i < zLength; i++){
			TileEntityUncertaintyBorder tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(masterX, masterY, masterZ + i);
			if(tileEnt != null && (tileEnt.isMaster || tileEnt.isSlave())){
				tileEnt.clearMultiblock();
				worldObj.setBlockMetadataWithNotify(masterX, masterY, masterZ + i, 0, 3);
			}
			
			tileEnt = null;			
			tileEnt = (TileEntityUncertaintyBorder)worldObj.getTileEntity(masterX + xLength - 1, masterY, masterZ + i);
			if(tileEnt != null && (tileEnt.isMaster || tileEnt.isSlave())){
				tileEnt.clearMultiblock();
				worldObj.setBlockMetadataWithNotify(masterX + xLength - 1, masterY, masterZ + i, 0, 3);
			}
		}
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(isMaster){
			count++;
			if(count >= delay){
				act();
				// Reset
				count = 0;
				delay = MathHelper.randomIntInRange(ACTIONDELAY_MIN, ACTIONDELAY_MAX);
			}
		}
	}
	
	private void act(){
		int action = weightCollection.next();
		switch(action){
			case ACTION_POP:
				pop();
				break;
			case ACTION_SPAWNMOB:
				spawnMob();
				break;
			case ACTION_POTION:
				potion();
				break;
			case ACTION_SPAWNITEM:
				spawnItem();
				break;
			case ACTION_LIGHTNING:
				spawnLightning();
				break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void pop(){
		List<EntityLivingBase> ents = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				xCoord + 1.0, yCoord + 1.0, zCoord + 1.0,
				xCoord + xLength - 1, yCoord + 3.0, zCoord + zLength - 1));
		for(EntityLivingBase ent : ents){
			if(ent instanceof EntityPlayer){
				EntityPlayerMP player = (EntityPlayerMP)ent;
				if(!player.capabilities.isCreativeMode){
					player.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(player.getEntityId(), 0.0, MathHelper.randomDoubleInRange(0.5, 2), 0.0));
				}
			}
			else {
				if(!ent.isDead){
					ent.addVelocity(0.0, MathHelper.randomDoubleInRange(0.5, 2), 0);
				}
			}
		}
	}
	
	private void spawnMob(){
		// TODO Check how many mobs are in the local area and don't spawn if > configurable amount
		
		@SuppressWarnings("unchecked")
		Iterator<EntityEggInfo> iterator = EntityList.entityEggs.values().iterator();
        List<Integer> ids=new ArrayList<Integer>();
        while (iterator.hasNext()){
            EntityEggInfo entityegginfo = iterator.next();
            ids.add(entityegginfo.spawnedID);
        }
        // shuffle and pick from IDs
        Collections.shuffle(ids);
        int id = ids.get(0);
        Entity entity = null;
        entity = EntityList.createEntityByID(id, worldObj);
                 
        if (entity != null && entity instanceof EntityLivingBase) {
                EntityLiving entityliving = (EntityLiving)entity;
                entity.setLocationAndAngles(
                		xCoord + 2 + MathHelper.randomDoubleInRange(0, xLength - 3), 
                		yCoord + 1.0, 
                		zCoord + 2 + MathHelper.randomDoubleInRange(0, zLength - 3), 
                		net.minecraft.util.MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360.0f), 0.0f);
                worldObj.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
        }
	}
	
	@SuppressWarnings("unchecked")
	private void potion(){
		List<EntityLivingBase> ents = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				xCoord + 1.0, yCoord + 1.0, zCoord + 1.0,
				xCoord + xLength - 1, yCoord + 3.0, zCoord + zLength - 1));
		if(ents != null && ents.size() > 0){
			Potion potion = null;
			// Try and find a potion, 15 times max
			for(int i = 0; i < 15; i++){
				potion = Potion.potionTypes[worldObj.rand.nextInt(Potion.potionTypes.length)];
				if(potion != null) break;
			}
			
			if(potion == null) return;
			
			EntityLivingBase ent = ents.get(worldObj.rand.nextInt(ents.size()));
			ent.addPotionEffect(new PotionEffect(potion.id, MathHelper.randomIntInRange(3, 10), 1, false));
		}
	}
	
	private void spawnItem(){
		// TODO check how many EntityItem's are in local are and don't spawn if > configurable amount
		/*OLD METHOD - Spawns any possible item (including ones we don't want such as blocks of portal / non meta items
		Object[] list = GameData.getItemRegistry().getKeys().toArray();
		int rand = worldObj.rand.nextInt(list.length);
		Item item = (Item)GameData.getItemRegistry().getObject(list[rand]);
		ItemStack newStack = new ItemStack(item);
		worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 2 + MathHelper.randomDoubleInRange(0, xLength - 3),
				yCoord + 1.0,
				zCoord + 2 + MathHelper.randomDoubleInRange(0, zLength - 3), newStack));*/
		
		ItemStack oneItem = ChestGenHooks.getOneItem(ChestGenHooks.DUNGEON_CHEST, worldObj.rand);
		worldObj.spawnEntityInWorld(new EntityItem(worldObj,
				xCoord + 2 + MathHelper.randomDoubleInRange(0, xLength - 3),
				yCoord + 1.0,
				zCoord + 2 + MathHelper.randomDoubleInRange(0, zLength - 3), 
				oneItem));
	}
	
	private void spawnLightning(){
		int ran = MathHelper.randomIntInRange(1, 5);
		for(int i = 0; i < ran; i++){
			worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, 
					xCoord + 2 + MathHelper.randomDoubleInRange(0, xLength - 3),
					yCoord + 1.0,
					zCoord + 2 + MathHelper.randomDoubleInRange(0, zLength - 3)));
		}
	}
	
	private void createWeightCollection(){
		weightCollection = new RandomCollection<Integer>(worldObj.rand);
		weightCollection.add(WEIGHT_POP, ACTION_POP);
		weightCollection.add(WEIGHT_SPAWNMOB, ACTION_SPAWNMOB);
		weightCollection.add(WEIGHT_POTION, ACTION_POTION);
		weightCollection.add(WEIGHT_SPAWNITEM, ACTION_SPAWNITEM);
		weightCollection.add(WEIGHT_LIGHTNING, ACTION_LIGHTNING);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isMaster", isMaster);
		nbt.setInteger("xLen", xLength);
		nbt.setInteger("zLen", zLength);
		nbt.setBoolean("hasMaster", hasMaster);
		if(hasMaster){
			nbt.setInteger("masterX", masterX);
			nbt.setInteger("masterY", masterY);
			nbt.setInteger("masterZ", masterZ);
		}		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		isMaster = nbt.getBoolean("isMaster");
		xLength = nbt.getInteger("xLen");
		zLength = nbt.getInteger("zLen");
		hasMaster = nbt.getBoolean("hasMaster");
		if(hasMaster){
			masterX = nbt.getInteger("masterX");
			masterY = nbt.getInteger("masterY");
			masterZ = nbt.getInteger("masterZ");
		}
	}
	
	public void clearMultiblock(){
		isMaster = false;
		hasMaster = false;
	}
	
	public boolean isMaster(){
		return isMaster;
	}
	
	public void setAsMaster(){
		isMaster = true;
		masterX = xCoord;
		masterY = yCoord;
		masterZ = zCoord;
	}
	
	public boolean isSlave(){
		return hasMaster;
	}
	
	public void setAsSlave(int masterX, int masterY, int masterZ, int xLength, int zLength){
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
		this.xLength = xLength;
		this.zLength = zLength;
		this.hasMaster = true;
	}

	public int getMasterX() {
		return masterX;
	}

	public int getMasterY() {
		return masterY;
	}

	public int getMasterZ() {
		return masterZ;
	}
}