package villain.mc.vague.utils;

import java.util.Random;

public class MathHelper {

	public static final Random random = new Random();
	
	public static int randomIntInRange(int min, int max){
		return min + (random.nextInt(max - min + 1));
	}
	
	public static float randomFloatInRange(float min, float max){
		return min + (random.nextFloat() * (max - min + 1f));
	}
	
	public static double randomDoubleInRange(double min, double max){
		return min + (random.nextDouble() * (max - min + 1.0));
	}
}