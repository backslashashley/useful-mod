package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MovingBlockEntity;

@Mixin(MovingBlockEntity.class)
public class MovingBlockEntityMixin extends BlockEntity {

	@Unique
	private static final float USEFULMOD_MATH_NEXT_DOWN_OF_ONE = Math.nextDown(1F);

	@Shadow
	private float progress;

	/**
	 * @author nessie
	 * @reason lazy
	 */
	@Overwrite
	public float getProgress(float tickDelta) {
		if (this.removed && Math.abs(this.progress - 1F) < 1E-5F) {
			return USEFULMOD_MATH_NEXT_DOWN_OF_ONE;
		}

		return Math.min(1F, (2F * this.progress + tickDelta) / 3F);
	}
}
