package villain.mc.vague.tileents;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import villain.mc.vague.Init;
import villain.mc.vague.utils.LogHelper;

public class TileEntityTipharesPlant extends TileEntity {
	
	private static final int RADIUS = 16;
	private static final int STEPS_PER_TICK_PHASE_01 = 1000;
	private static final int STEPS_PER_TICK_PHASE_2 = 25;

	private boolean isMaster;
	private boolean hasMaster;
	private int masterX, masterY, masterZ;
	
	private double[] noise;
	private double noiseMin, noiseMax;
	private int genX, genY, genZ, genPhase = -1, genMinus, genHeight;
	private Block[] blocks;
	private int[] metas;
	
	public TileEntityTipharesPlant(){
		super();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isMaster", isMaster);
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
		hasMaster = nbt.getBoolean("hasMaster");
		if(hasMaster){
			masterX = nbt.getInteger("masterX");
			masterY = nbt.getInteger("masterY");
			masterZ = nbt.getInteger("masterZ");			
		}
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote){
			if(isMaster && genPhase != -1){
				genUpdate();
			}
		}
	}
	
	private void startGenning(){
		// Check the height of the current plant stack
		Block b = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		genHeight = 1;
		while(b == Init.blockTipharesPlant){
			genHeight++;
			b = worldObj.getBlock(xCoord, yCoord + genHeight, zCoord);
		}
		
		blocks = new Block[(RADIUS * 2) * (RADIUS * 2) * (RADIUS * 2)];
		metas = new int[(RADIUS * 2) * (RADIUS * 2) * (RADIUS * 2)];
		genX = -RADIUS;
		genY = -RADIUS;
		genZ = -RADIUS;
		genMinus = 0;
		genPhase = 0;
	}
	
	private void genUpdate(){
		if(noise == null){
			noise = new NoiseGeneratorOctaves(worldObj.rand, 8).generateNoiseOctaves(null, xCoord, yCoord, zCoord, RADIUS * 2, RADIUS * 2, RADIUS * 2, 1, 1, 1);
		}
		
		for(int i = 0; i < (genPhase == 0 || genPhase == 1 ? STEPS_PER_TICK_PHASE_01 : STEPS_PER_TICK_PHASE_2); i++){
			switch(genPhase){
				case 0:
					LogHelper.info("phase 0: " + genX + ", " + genY + ", " + genZ);
					double n = noise[(genX + RADIUS) + ((genY + RADIUS) * (RADIUS * 2)) + ((genZ + RADIUS) * (RADIUS * 2) * (RADIUS * 2))];
					if(n < noiseMin) noiseMin = n;
					if(n > noiseMax) noiseMax = n;
					
					blocks[(genX + RADIUS) + ((genY + RADIUS) * (RADIUS * 2)) + ((genZ + RADIUS) * (RADIUS * 2) * (RADIUS * 2))] = 
							worldObj.getBlock(xCoord + genX, yCoord + genY, zCoord + genZ);
					metas[(genX + RADIUS) + ((genY + RADIUS) * (RADIUS * 2)) + ((genZ + RADIUS) * (RADIUS * 2) * (RADIUS * 2))] =
							worldObj.getBlockMetadata(xCoord + genX, yCoord + genY, zCoord + genZ);
					
					genX++;
					if(genX >= RADIUS){
						genX = -RADIUS;
						genZ++;
						if(genZ >= RADIUS){
							genZ = -RADIUS;
							genY++;
							if(genY >= RADIUS){
								genPhase = 1;
								genZ = -RADIUS;
								genY = -RADIUS;
								genZ = -RADIUS;
							}
						}
					}
					break;					
					
				case 1:
					genMinus++;
					double falloff = (double)(genY + RADIUS) / (double)RADIUS;
					double dist = 1.0 - (Math.abs(Math.sqrt(genY * genY)) / RADIUS);
					double on = noise[(genY + RADIUS) * (RADIUS * 2)];
					double n2 = normalise(on, noiseMin, noiseMax);
					if(n2 * dist * falloff > 0.1){
						genPhase = 2;
						genY = -RADIUS;
						break;
					}
					
					genY++;
					if(genY >= RADIUS){
						genPhase = 2;
						genY = -RADIUS;
					}
					
					break;
					
				case 2:
					double dist2 = 1.0 - (Math.abs(Math.sqrt(genX * genX + genY * genY + genZ * genZ)) / RADIUS);
					double on2 = noise[(genX + RADIUS) + ((genY + RADIUS) * (RADIUS * 2)) + ((genZ + RADIUS) * (RADIUS * 2) * (RADIUS * 2))];
					double n3 = normalise(on2, noiseMin, noiseMax);
					double falloff2 = (double)(genY + RADIUS) / (double)RADIUS;
					if(n3 * dist2 * falloff2 > 0.1){
						Block b = blocks[(genX + RADIUS) + ((genY + RADIUS) * (RADIUS * 2)) + ((genZ + RADIUS) * (RADIUS * 2) * (RADIUS * 2))];
						int meta = metas[(genX + RADIUS) + ((genY + RADIUS) * (RADIUS * 2)) + ((genZ + RADIUS) * (RADIUS * 2) * (RADIUS * 2))];
						TileEntity tileEnt = worldObj.getTileEntity(xCoord + genX, yCoord + genY, zCoord + genZ);
						if(b != null && b != Init.blockTipharesPlant && tileEnt == null){
							worldObj.setBlock(xCoord + genX, yCoord + genY + genHeight + RADIUS - genMinus, zCoord + genZ, b);
							worldObj.setBlockMetadataWithNotify(xCoord + genX, yCoord + genY + genHeight + RADIUS - genMinus, zCoord + genZ, meta, 3);
						}
					}
					
					genX++;
					if(genX >= RADIUS){
						genX = -RADIUS;
						genZ++;
						if(genZ >= RADIUS){
							genZ = -RADIUS;
							genY++;
							if(genY >= RADIUS){
								genFinish();
							}
						}
					}
					break;
			}
		}
	}
	
	private void genFinish(){
		genPhase = -1;
		worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
	}
	
	private double normalise(double value, double min, double max){
		return (((value - min) * (1f - 0f)) / (max - min)) + 0f;
	}
	
	public void applyDiamond(){
		startGenning();
	}
		
	public void setAsMaster(){
		isMaster = true;
	}
	
	public boolean isMaster(){
		return isMaster;
	}
	
	public void setAsSlave(int masterX, int masterY, int masterZ){
		this.hasMaster = true;
		this.masterX = masterX;
		this.masterY = masterY;
		this.masterZ = masterZ;
	}
	
	public boolean isSlave(){
		return hasMaster && !isMaster;
	}
	
	public int getMasterX(){
		return masterX;
	}
	
	public int getMasterY(){
		return masterY;
	}
	
	public int getMasterZ(){
		return masterZ;
	}
}