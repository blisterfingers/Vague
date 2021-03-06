package villain.mc.vague.utils;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Schematic {

	private NBTTagList tileEntities;
	private short width;
	private short height;
	private short length;
	private byte[] blocks;
	private byte[] data;
	private HashMap<Integer, String> mappings;
	
	public Schematic(NBTTagCompound mappings, NBTTagList tileEntities, short width, short height, short length, byte[] blocks, byte[] data){
		this.tileEntities = tileEntities;
		this.width = width;
		this.height = height;
		this.length = length;
		this.blocks = blocks;
		this.data = data;
		
		processMappings(mappings);
	}
	
	private void processMappings(NBTTagCompound compound){
		
	}
	

	public NBTTagList getTileEntities() {
		return tileEntities;
	}

	public short getWidth() {
		return width;
	}

	public short getHeight() {
		return height;
	}

	public short getLength() {
		return length;
	}

	public byte[] getBlocks() {
		return blocks;
	}

	public byte[] getData() {
		return data;
	}
}