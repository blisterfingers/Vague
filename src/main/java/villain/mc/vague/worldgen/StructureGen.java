package villain.mc.vague.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class StructureGen extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, int i, int j, int k) {
		
		
		//check that each corner is one of the valid spawn blocks
		if(!locationIsValidSpawn(world, i, j, k) || !locationIsValidSpawn(world, i + 10, j, k) || !locationIsValidSpawn(world, i + 10, j, k + 12) || !locationIsValidSpawn(world, i, j, k + 12))
		{
			return false;
		}

		k = k - 10;
		i = i - 10;

		this.setBlock(world, i + 0, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 0, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 0, j + 9, k + 1, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 0, j + 9, k + 2, Blocks.dark_oak_stairs, 7);
		this.setBlock(world, i + 0, j + 9, k + 8, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 0, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 0, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 0, j + 10, k + 3, Blocks.dark_oak_stairs, 7);
		this.setBlock(world, i + 0, j + 10, k + 7, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 0, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 0, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 0, j + 11, k + 4, Blocks.dark_oak_stairs, 7);
		this.setBlock(world, i + 0, j + 11, k + 6, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 0, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 0, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 0, j + 12, k + 5, Blocks.dark_oak_stairs, 4);
		this.setBlock(world, i + 0, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 0, j + 13, k + 5, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 1, j + 0, k + 2, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 1, j + 0, k + 3, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 1, j + 0, k + 4, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 1, j + 0, k + 5, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 1, j + 0, k + 6, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 1, j + 0, k + 7, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 1, j + 0, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 1, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 4, k + 3, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 4, k + 4, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 4, k + 5, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 4, k + 6, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 4, k + 7, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 5, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 5, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 5, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 5, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 5, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 5, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 5, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 6, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 6, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 6, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 6, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 6, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 6, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 6, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 7, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 7, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 7, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 7, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 7, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 7, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 7, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 8, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 8, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 8, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 8, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 8, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 8, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 8, k + 8, Blocks.sandstone, 0);
		this.setBlock(world, i + 1, j + 9, k + 1, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 1, j + 9, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 3, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 4, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 5, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 6, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 7, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 1, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 1, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 1, j + 10, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 10, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 10, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 10, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 10, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 1, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 1, j + 11, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 11, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 11, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 1, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 1, j + 12, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 1, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 1, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 2, j + 0, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 2, j + 0, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 0, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 0, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 0, k + 6, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 0, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 0, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 2, j + 1, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 1, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 1, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 1, k + 6, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 1, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 2, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 2, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 2, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 2, k + 6, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 2, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 3, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 3, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 3, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 3, k + 6, Blocks.cobblestone, 0);
		this.setBlock(world, i + 2, j + 3, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 2, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 2, j + 4, k + 3, Blocks.planks, 1);
		this.setBlock(world, i + 2, j + 4, k + 4, Blocks.planks, 1);
		this.setBlock(world, i + 2, j + 4, k + 5, Blocks.planks, 1);
		this.setBlock(world, i + 2, j + 4, k + 6, Blocks.planks, 1);
		this.setBlock(world, i + 2, j + 4, k + 7, Blocks.planks, 1);
		this.setBlock(world, i + 2, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 2, j + 5, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 5, k + 7, Blocks.piston, 1);
		this.setBlock(world, i + 2, j + 5, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 6, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 6, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 7, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 7, k + 7, Blocks.chest, 2);
		this.setBlock(world, i + 2, j + 7, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 8, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 8, k + 7, Blocks.wooden_slab, 0);
		this.setBlock(world, i + 2, j + 8, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 2, j + 9, k + 0, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 2, j + 9, k + 1, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 2, j + 9, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 2, j + 9, k + 3, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 2, j + 9, k + 4, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 2, j + 9, k + 6, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 2, j + 9, k + 7, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 2, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 2, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 2, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 2, j + 10, k + 3, Blocks.chest, 3);
		this.setBlock(world, i + 2, j + 10, k + 7, Blocks.chest, 2);
		this.setBlock(world, i + 2, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 2, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 2, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 2, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 2, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 2, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 3, j + 0, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 3, j + 0, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 0, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 0, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 3, j + 1, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 1, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 2, k + 2, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 3, j + 2, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 2, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 3, k + 1, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 3, j + 3, k + 2, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 3, j + 3, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 3, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 3, j + 4, k + 0, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 4, k + 1, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 4, k + 3, Blocks.planks, 1);
		this.setBlock(world, i + 3, j + 4, k + 4, Blocks.planks, 1);
		this.setBlock(world, i + 3, j + 4, k + 6, Blocks.planks, 1);
		this.setBlock(world, i + 3, j + 4, k + 7, Blocks.planks, 1);
		this.setBlock(world, i + 3, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 5, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 5, k + 7, Blocks.crafting_table, 0);
		this.setBlock(world, i + 3, j + 5, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 6, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 6, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 7, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 7, k + 7, Blocks.air, 2);
		this.setBlock(world, i + 3, j + 7, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 8, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 8, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 8, k + 7, Blocks.wooden_slab, 0);
		this.setBlock(world, i + 3, j + 8, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 3, j + 9, k + 0, Blocks.dark_oak_stairs, 5);
		this.setBlock(world, i + 3, j + 9, k + 1, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 9, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 9, k + 3, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 3, j + 9, k + 4, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 3, j + 9, k + 5, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 3, j + 9, k + 6, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 3, j + 9, k + 7, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 3, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 3, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 3, j + 10, k + 0, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 3, j + 10, k + 1, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 3, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 3, j + 10, k + 3, Blocks.chest, 3);
		this.setBlock(world, i + 3, j + 10, k + 7, Blocks.chest, 2);
		this.setBlock(world, i + 3, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 3, j + 10, k + 11, Blocks.iron_bars, 0);
		this.setBlock(world, i + 3, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 3, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 3, j + 11, k + 10, Blocks.iron_bars, 0);
		this.setBlock(world, i + 3, j + 11, k + 11, Blocks.iron_bars, 0);
		this.setBlock(world, i + 3, j + 11, k + 12, Blocks.iron_bars, 0);
		this.setBlock(world, i + 3, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 3, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 3, j + 12, k + 10, Blocks.stone_slab, 0);
		this.setBlock(world, i + 3, j + 12, k + 11, Blocks.stone_slab, 0);
		this.setBlock(world, i + 3, j + 12, k + 12, Blocks.stone_slab, 0);
		this.setBlock(world, i + 3, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 4, j + 0, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 4, j + 0, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 0, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 0, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 4, j + 1, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 1, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 2, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 2, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 3, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 3, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 4, k + 1, Blocks.planks, 0);
		this.setBlock(world, i + 4, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 4, j + 4, k + 3, Blocks.planks, 1);
		this.setBlock(world, i + 4, j + 4, k + 4, Blocks.planks, 1);
		this.setBlock(world, i + 4, j + 4, k + 5, Blocks.planks, 1);
		this.setBlock(world, i + 4, j + 4, k + 6, Blocks.planks, 1);
		this.setBlock(world, i + 4, j + 4, k + 7, Blocks.planks, 1);
		this.setBlock(world, i + 4, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 4, j + 5, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 4, j + 5, k + 7, Blocks.furnace, 2);
		this.setBlock(world, i + 4, j + 5, k + 8, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 5, k + 9, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 5, k + 10, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 6, k + 1, Blocks.glass_pane, 0);
		this.setBlock(world, i + 4, j + 6, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 4, j + 6, k + 10, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 6, k + 11, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 7, k + 1, Blocks.glass_pane, 0);
		this.setBlock(world, i + 4, j + 7, k + 7, Blocks.chest, 2);
		this.setBlock(world, i + 4, j + 7, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 4, j + 7, k + 11, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 8, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 4, j + 8, k + 2, Blocks.redstone_lamp, 0);
		this.setBlock(world, i + 4, j + 8, k + 7, Blocks.wooden_slab, 0);
		this.setBlock(world, i + 4, j + 8, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 4, j + 8, k + 11, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 9, k + 1, Blocks.planks, 0);
		this.setBlock(world, i + 4, j + 9, k + 3, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 4, j + 9, k + 4, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 4, j + 9, k + 5, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 4, j + 9, k + 6, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 4, j + 9, k + 7, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 4, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 4, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 4, j + 9, k + 11, Blocks.wool, 0);
		this.setBlock(world, i + 4, j + 10, k + 0, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 4, j + 10, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 4, j + 10, k + 2, Blocks.glowstone, 0);
		this.setBlock(world, i + 4, j + 10, k + 7, Blocks.bookshelf, 0);
		this.setBlock(world, i + 4, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 4, j + 10, k + 10, Blocks.iron_bars, 0);
		this.setBlock(world, i + 4, j + 10, k + 11, Blocks.cobblestone, 0);
		this.setBlock(world, i + 4, j + 10, k + 12, Blocks.iron_bars, 0);
		this.setBlock(world, i + 4, j + 11, k + 0, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 4, j + 11, k + 1, Blocks.stone_slab, 3);
		this.setBlock(world, i + 4, j + 11, k + 2, Blocks.stone_slab, 3);
		this.setBlock(world, i + 4, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 4, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 4, j + 11, k + 10, Blocks.iron_bars, 0);
		this.setBlock(world, i + 4, j + 11, k + 12, Blocks.iron_bars, 0);
		this.setBlock(world, i + 4, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 4, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 4, j + 12, k + 10, Blocks.stone_slab, 0);
		this.setBlock(world, i + 4, j + 12, k + 11, Blocks.stone_slab, 8);
		this.setBlock(world, i + 4, j + 12, k + 12, Blocks.stone_slab, 0);
		this.setBlock(world, i + 4, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 5, j + 0, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 5, j + 0, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 0, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 0, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 5, j + 1, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 1, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 2, k + 2, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 5, j + 2, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 2, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 3, k + 1, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 5, j + 3, k + 2, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 5, j + 3, k + 3, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 3, k + 7, Blocks.cobblestone, 0);
		this.setBlock(world, i + 5, j + 4, k + 0, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 4, k + 1, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 4, k + 3, Blocks.planks, 1);
		this.setBlock(world, i + 5, j + 4, k + 4, Blocks.planks, 1);
		this.setBlock(world, i + 5, j + 4, k + 5, Blocks.planks, 1);
		this.setBlock(world, i + 5, j + 4, k + 6, Blocks.planks, 1);
		this.setBlock(world, i + 5, j + 4, k + 7, Blocks.planks, 1);
		this.setBlock(world, i + 5, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 5, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 5, k + 7, Blocks.cauldron, 3);
		this.setBlock(world, i + 5, j + 5, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 6, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 6, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 7, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 7, k + 7, Blocks.air, 2);
		this.setBlock(world, i + 5, j + 7, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 8, k + 1, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 8, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 8, k + 7, Blocks.wooden_slab, 0);
		this.setBlock(world, i + 5, j + 8, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 5, j + 9, k + 0, Blocks.dark_oak_stairs, 4);
		this.setBlock(world, i + 5, j + 9, k + 1, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 9, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 9, k + 3, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 5, j + 9, k + 4, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 5, j + 9, k + 5, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 5, j + 9, k + 6, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 5, j + 9, k + 7, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 5, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 5, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 5, j + 10, k + 0, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 5, j + 10, k + 1, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 5, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 5, j + 10, k + 3, Blocks.bookshelf, 0);
		this.setBlock(world, i + 5, j + 10, k + 5, Blocks.bed, 3);
		this.setBlock(world, i + 5, j + 10, k + 7, Blocks.chest, 2);
		this.setBlock(world, i + 5, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 5, j + 10, k + 11, Blocks.iron_bars, 0);
		this.setBlock(world, i + 5, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 5, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 5, j + 11, k + 10, Blocks.iron_bars, 0);
		this.setBlock(world, i + 5, j + 11, k + 11, Blocks.iron_bars, 0);
		this.setBlock(world, i + 5, j + 11, k + 12, Blocks.iron_bars, 0);
		this.setBlock(world, i + 5, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 5, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 5, j + 12, k + 10, Blocks.stone_slab, 0);
		this.setBlock(world, i + 5, j + 12, k + 11, Blocks.stone_slab, 0);
		this.setBlock(world, i + 5, j + 12, k + 12, Blocks.stone_slab, 0);
		this.setBlock(world, i + 5, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 6, j + 0, k + 2, Blocks.stone_brick_stairs, 2);
		this.setBlock(world, i + 6, j + 0, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 0, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 0, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 0, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 0, k + 8, Blocks.stone_brick_stairs, 3);
		this.setBlock(world, i + 6, j + 1, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 1, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 1, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 1, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 2, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 2, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 2, k + 5, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 2, k + 6, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 2, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 3, k + 3, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 3, k + 4, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 3, k + 5, Blocks.glowstone, 0);
		this.setBlock(world, i + 6, j + 3, k + 6, Blocks.cobblestone, 0);
		this.setBlock(world, i + 6, j + 3, k + 7, Blocks.stonebrick, 0);
		this.setBlock(world, i + 6, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 6, j + 4, k + 3, Blocks.planks, 1);
		this.setBlock(world, i + 6, j + 4, k + 4, Blocks.planks, 1);
		this.setBlock(world, i + 6, j + 4, k + 5, Blocks.planks, 1);
		this.setBlock(world, i + 6, j + 4, k + 6, Blocks.planks, 1);
		this.setBlock(world, i + 6, j + 4, k + 7, Blocks.planks, 1);
		this.setBlock(world, i + 6, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 6, j + 5, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 5, k + 7, Blocks.piston, 1);
		this.setBlock(world, i + 6, j + 5, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 6, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 6, k + 7, Blocks.cake, 0);
		this.setBlock(world, i + 6, j + 6, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 7, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 7, k + 7, Blocks.chest, 2);
		this.setBlock(world, i + 6, j + 7, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 8, k + 2, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 8, k + 7, Blocks.wooden_slab, 0);
		this.setBlock(world, i + 6, j + 8, k + 8, Blocks.sandstone, 1);
		this.setBlock(world, i + 6, j + 9, k + 0, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 6, j + 9, k + 1, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 6, j + 9, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 6, j + 9, k + 3, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 6, j + 9, k + 4, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 6, j + 9, k + 5, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 6, j + 9, k + 6, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 6, j + 9, k + 7, Blocks.double_wooden_slab, 1);
		this.setBlock(world, i + 6, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 6, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 6, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 6, j + 10, k + 3, Blocks.bookshelf, 0);
		this.setBlock(world, i + 6, j + 10, k + 4, Blocks.crafting_table, 0);
		this.setBlock(world, i + 6, j + 10, k + 5, Blocks.bed, 11);
		this.setBlock(world, i + 6, j + 10, k + 6, Blocks.bookshelf, 0);
		this.setBlock(world, i + 6, j + 10, k + 7, Blocks.bookshelf, 0);
		this.setBlock(world, i + 6, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 6, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 6, j + 11, k + 6, Blocks.air, 10);
		this.setBlock(world, i + 6, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 6, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 6, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 6, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 7, j + 0, k + 2, Blocks.stone_brick_stairs, 2);
		this.setBlock(world, i + 7, j + 0, k + 3, Blocks.stone_brick_stairs, 1);
		this.setBlock(world, i + 7, j + 0, k + 4, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 7, j + 0, k + 5, Blocks.dark_oak_stairs, 1);
		this.setBlock(world, i + 7, j + 0, k + 7, Blocks.stone_brick_stairs, 1);
		this.setBlock(world, i + 7, j + 0, k + 8, Blocks.stone_brick_stairs, 1);
		this.setBlock(world, i + 7, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 4, k + 3, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 4, k + 4, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 4, k + 5, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 4, k + 6, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 4, k + 7, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 5, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 5, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 5, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 5, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 5, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 5, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 5, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 6, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 6, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 6, k + 4, Blocks.glass_pane, 0);
		this.setBlock(world, i + 7, j + 6, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 6, k + 6, Blocks.glass_pane, 0);
		this.setBlock(world, i + 7, j + 6, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 6, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 7, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 7, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 7, k + 4, Blocks.glass_pane, 0);
		this.setBlock(world, i + 7, j + 7, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 7, k + 6, Blocks.glass_pane, 0);
		this.setBlock(world, i + 7, j + 7, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 7, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 8, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 8, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 8, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 8, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 8, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 8, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 8, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 1, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 7, j + 9, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 3, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 4, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 5, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 6, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 7, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 7, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 7, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 7, j + 10, k + 3, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 10, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 10, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 10, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 10, k + 7, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 7, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 7, j + 11, k + 4, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 11, k + 5, Blocks.glass_pane, 0);
		this.setBlock(world, i + 7, j + 11, k + 6, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 7, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 7, j + 12, k + 5, Blocks.sandstone, 1);
		this.setBlock(world, i + 7, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 7, j + 13, k + 5, Blocks.stone_slab, 3);
		this.setBlock(world, i + 8, j + 4, k + 2, Blocks.planks, 0);
		this.setBlock(world, i + 8, j + 4, k + 8, Blocks.planks, 0);
		this.setBlock(world, i + 8, j + 9, k + 1, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 8, j + 9, k + 2, Blocks.dark_oak_stairs, 7);
		this.setBlock(world, i + 8, j + 9, k + 5, Blocks.dark_oak_stairs, 5);
		this.setBlock(world, i + 8, j + 9, k + 8, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 8, j + 9, k + 9, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 8, j + 10, k + 2, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 8, j + 10, k + 7, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 8, j + 10, k + 8, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 8, j + 11, k + 3, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 8, j + 11, k + 6, Blocks.dark_oak_stairs, 6);
		this.setBlock(world, i + 8, j + 11, k + 7, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 8, j + 12, k + 4, Blocks.dark_oak_stairs, 2);
		this.setBlock(world, i + 8, j + 12, k + 5, Blocks.dark_oak_stairs, 5);
		this.setBlock(world, i + 8, j + 12, k + 6, Blocks.dark_oak_stairs, 3);
		this.setBlock(world, i + 8, j + 13, k + 5, Blocks.dark_oak_stairs, 0);
		this.setBlock(world, i + 9, j + 9, k + 5, Blocks.wooden_slab, 8);
		this.setBlock(world, i + 9, j + 10, k + 5, Blocks.fence, 0);
		this.setBlock(world, i + 10, j + 4, k + 5, Blocks.glowstone, 0);
		this.setBlock(world, i + 10, j + 5, k + 5, Blocks.fence, 0);
		this.setBlock(world, i + 10, j + 6, k + 5, Blocks.fence, 0);
		this.setBlock(world, i + 10, j + 7, k + 5, Blocks.fence, 0);
		this.setBlock(world, i + 10, j + 8, k + 5, Blocks.fence, 0);
		this.setBlock(world, i + 10, j + 9, k + 5, Blocks.fence, 0);
		this.setBlock(world, i + 10, j + 10, k + 5, Blocks.fence, 0);
		world.setBlockMetadataWithNotify(i + 2, j + 5, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 2, j + 6, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 2, j + 7, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 2, j + 8, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 2, j + 9, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 3, j + 0, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 3, j + 1, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 3, j + 2, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 3, j + 3, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 3, j + 4, k + 5, 5, 2);
		world.setBlockMetadataWithNotify(i + 4, j + 9, k + 2, 11, 2);
		world.setBlockMetadataWithNotify(i + 4, j + 10, k + 3, 13, 2);
		world.setBlockMetadataWithNotify(i + 4, j + 11, k + 11, 5, 2);
		world.setBlockMetadataWithNotify(i + 6, j + 0, k + 6, 2, 2);
		world.setBlockMetadataWithNotify(i + 6, j + 1, k + 6, 8, 2);
		this.setBlock(world, i + 6, j + 11, k + 4, Blocks.stone_pressure_plate, 0);
		world.setBlockMetadataWithNotify(i + 7, j + 3, k + 5, 15, 2);

		return true;
	}
	
	public void setBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		Block b1 = world.getBlock(x, y, z);

		if(b1.isAir(world, x, y, z) || b1.isLeaves(world, x, y, z))
		{
			world.setBlock(x, y, z, block, metadata, 2);
		}
	}
	
	protected Block[] getValidSpawnBlocks() {
		return new Block[] {
			Blocks.grass,
			Blocks.dirt
		};
	}

	public boolean locationIsValidSpawn(World world, int i, int j, int k){
		int distanceToAir = 0;
		Block check = world.getBlock(i, j, k);

		while (check != Blocks.air){
			if (distanceToAir > 3){
				return false;
			}

			distanceToAir++;
			check = world.getBlock(i, j + distanceToAir, k);
		}

		j += distanceToAir - 1;

		Block block = world.getBlock(i, j, k);
		Block blockAbove = world.getBlock(i, j+1, k);
		Block blockBelow = world.getBlock(i, j-1, k);
		
		for (Block x : getValidSpawnBlocks()){
			if (blockAbove != Blocks.air){
				return false;
			}
			if (block == x){
				return true;
			}else if (block == Blocks.snow && blockBelow == x){
				return true;
			}
		}
		
		return false;
	}
}