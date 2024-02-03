package nessiesson.usefulmod;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nessiesson.usefulmod.config.Config;

import net.minecraft.entity.living.effect.StatusEffects;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.Box;

class StepAssistHelper {

	private boolean wasOnGround = false;

	void update(PlayerEntity player) {
		player.stepHeight = getStepAmount(player);
		this.stepDown(player);
		this.wasOnGround = player.onGround;
	}

	private void stepDown(PlayerEntity player) {
		if (!Config.UNSTEP_ASSIST.get() || !wasOnGround || player.onGround || player.velocityY > 0D) {
			return;
		}

		final Box range = player.getShape().grow(0D, -1.5D, 0D).shrink(0D, player.height, 0D);
		if (!player.world.getCollisions(range)) {
			return;
		}

		final List<Box> collisions = player.world.getCollisions(player, range);
		if (!collisions.isEmpty()) {
			player.teleport(player.x, Collections.max(collisions, Comparator.comparingDouble(f -> f.maxY)).maxY, player.z);
		}
	}

	private float getStepAmount(PlayerEntity player) {
		if (Config.STEP_ASSIST.get()) {
			return player.isSneaking() ? 0.9F : 1.5F;
		}

		if (!Config.JUMP_BOOST_STEP_ASSIST.get() || !player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
			return 0.6F;
		}

		// getActivePotionEffect() is never null since we checked for that just above with isPotionAction().
		//noinspection ConstantConditions
		return player.isSneaking() ? 0.9F : 1F + 0.25F * player.getEffectInstance(StatusEffects.JUMP_BOOST).getAmplifier();
	}
}
