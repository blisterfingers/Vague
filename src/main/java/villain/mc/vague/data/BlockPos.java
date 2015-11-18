package villain.mc.vague.data;

public class BlockPos {

	private int x;
	private int y;
	private int z;
	
	public BlockPos(){ }
	
	public BlockPos(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockPos(BlockPos other){
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}
	
	public BlockPos copy(){
		return new BlockPos(this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}