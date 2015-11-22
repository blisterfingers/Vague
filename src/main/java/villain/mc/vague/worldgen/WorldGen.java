package villain.mc.vague.worldgen;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
		}
	}	
	
	private void generateSurface(World world, Random random, int chunkX, int chunkZ){
		if(random.nextFloat() > 0.8){
			int x = chunkX + random.nextInt(16);
			int z = chunkZ + random.nextInt(16);
			new StructureGen().generate(world, random, x, world.getHeightValue(x, z), z);
		}
	}
}