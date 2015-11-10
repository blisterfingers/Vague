package villain.mc.vague.tileents;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import villain.mc.vague.blocks.BlockTipharesPlant;
import villain.mc.vague.utils.LogHelper;

public class TileEntityTipharesPlant extends TileEntity {
	
	private final int RADIUS = 32;

	private boolean isMaster;
	private boolean hasMaster;
	private int masterX, masterY, masterZ;
	
	private NoiseGeneratorOctaves noiseGen;
	
	// Test
	private boolean genned = false;
	
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
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(isMaster && metadata >= BlockTipharesPlant.STAGES - 1 && !genned){
				LogHelper.info("upd");
				
				// Do it one big lump!
				double[] noise = noiseGen.generateNoiseOctaves(null, 0, 0, 0, RADIUS * 2, RADIUS * 2, RADIUS * 2, 1, 1, 1);
				
				double min = Double.MAX_VALUE;
				double max = -Double.MAX_VALUE;
				
				// Loop to find min/max values
				for(int y = 0; y < RADIUS * 2; y++){
					for(int x = 0; x < RADIUS * 2; x++){
						for(int z = 0; z < RADIUS * 2; z++){
							double n = noise[x * y * z];
							if(n < min) min = n;
							if(n > max) max = n;
						}
					}
				}
				
				// Loop again
				Block[] blocks = new Block[(RADIUS * 2) * (RADIUS * 2) * (RADIUS * 2)];
				for(int y = 0; y < RADIUS * 2; y++){
					for(int x = 0; x < RADIUS * 2; x++){
						for(int z = 0; z < RADIUS * 2; z++){
							blocks[x * y * z] = worldObj.getBlock(xCoord - RADIUS + x, yCoord - RADIUS + y, zCoord - RADIUS + z);
						}
					}
				}
				
				for(int y = 0; y < RADIUS * 2; y++){
					for(int x = 0; x < RADIUS * 2; x++){
						for(int z = 0; z < RADIUS * 2; z++){
							double value = normalise(noise[x * y * z], min, max);
							
							// multiply by distance to centre
							double centreXMul = ((((double)x - (double)RADIUS) / (double)RADIUS) + 1.0) / 2.0;
							double centreYMul = ((((double)y - (double)RADIUS) / (double)RADIUS) + 1.0) / 2.0;
							double centreZMul = ((((double)z - (double)RADIUS) / (double)RADIUS) + 1.0) / 2.0;
							
							double centreMul = (centreXMul + centreYMul + centreZMul) / 3.0;
							
							//LogHelper.info(centreXMul + ", " + centreYMul + ", " + centreZMul);
							
							//double density = value * centreFalloff;
							
							//LogHelper.info("value: " + value + ", centreFalloff: " + centreFalloff + ", dens: "  + density);
							double density = value * (1.0 - centreMul);
							//LogHelper.info(density);
							
							if(value > 0.2){
								// Shunt this one
								int wx = xCoord - RADIUS + x;
								int wy = yCoord - RADIUS + y;
								int wz = zCoord - RADIUS + z;
								
								if(blocks[x * y * z] != Blocks.air){
									worldObj.setBlock(wx, wy + 64, wz, blocks[x * y * z]);
									worldObj.setBlockToAir(wx, wy, wz);
									
									//LogHelper.info("Moving " + wx + "/" + wy + "/" + wz + " to " + wx + ", " + (wy + 64) + ", " + wz);
								}
							}
						}
					}
				}
				
				// done
				genned = true;
			}
		}
	}
	
	private double normalise(double value, double min, double max){
		return (value - min) / (max - min);
	}
		
	public void setAsMaster(){
		isMaster = true;
		
		noiseGen = new NoiseGeneratorOctaves(worldObj.rand, 8);
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