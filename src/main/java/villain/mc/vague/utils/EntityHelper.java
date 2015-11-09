package villain.mc.vague.utils;

import codechicken.lib.vec.Vector3;
import net.minecraft.entity.Entity;

public class EntityHelper {

	public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosition, float modifier){
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosition.copy().subtract(entityVector);
		
		if(finalVector.mag() > 1){
			finalVector.normalize();
		}
		
		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}
	
}