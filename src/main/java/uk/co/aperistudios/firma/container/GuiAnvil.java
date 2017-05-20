package uk.co.aperistudios.firma.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import uk.co.aperistudios.firma.FirmaMod;
import uk.co.aperistudios.firma.blocks.tileentity.AnvilTileEntity;

public class GuiAnvil extends GuiFirmaBase {
	public static final int guiwidth = 100, guiheight = 100;
	private AnvilTileEntity te;

	public GuiAnvil(EntityPlayer player, World world, AnvilTileEntity ate) {
		super(new ContainerAnvil(player.inventory, world, ate), guiwidth, guiheight); // TODO
		// Fix
		// width/height
		// to
		// texture
		tex = new ResourceLocation(FirmaMod.MODID + ":textures/gui/guismithing.png");
		this.te = ate;
	}

	@Override
	public void drawForeground(int let, int top) {

	}

}