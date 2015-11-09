package villain.mc.vague.blocks.tests;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import villain.mc.vague.Reference;
import villain.mc.vague.blocks.BlockBase;

public class BlockNumBlock extends BlockBase {

	private IIcon[] icons;
	
	public BlockNumBlock(){
		super("numblock", Material.carpet, true);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[6];
		
		for(int i = 0; i < 6; i++){
			icons[i] = iconRegister.registerIcon(Reference.RESOURCE_PREFIX + "numblock-" + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta){
		return icons[side];
	}
}