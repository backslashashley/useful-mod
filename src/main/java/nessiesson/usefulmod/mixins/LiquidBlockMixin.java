package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.Block;
import net.minecraft.block.LiquidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin extends Block {

	private LiquidBlockMixin(Material material) {
		super(material);
	}

	@Override
	public boolean isTranslucent(BlockState state) {
		return Config.SHOW_SMOOTH_WATER_LIGHTING.get() || super.isTranslucent(state);
	}
}
