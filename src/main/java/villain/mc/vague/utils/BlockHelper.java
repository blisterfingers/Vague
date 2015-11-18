package villain.mc.vague.utils;

import net.minecraftforge.common.util.ForgeDirection;
import villain.mc.vague.data.BlockPos;

public class BlockHelper {

	public static BlockPos blockPlaceNewPosition(int x, int y, int z, int side){
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return new BlockPos(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
	}
}