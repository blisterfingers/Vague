package villain.mc.vague.utils;

public class GuiHelper {

	public static boolean isPointInArea(int pointX, int pointY, int areaX, int areaY, int areaWidth, int areaHeight) {
		return pointX >= areaX && pointX < areaX + areaWidth && pointY >= areaY && pointY < areaY + areaHeight;
	}
}