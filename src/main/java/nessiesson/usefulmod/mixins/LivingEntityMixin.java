package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.mob.hostile.boss.WitherEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Unique
	private double usefulmodD0;
	@Unique
	private double usefulmodD1;
	@Unique
	private double usefulmodD2;

	private LivingEntityMixin(World world) {
		super(world);
	}

	@Inject(
		method = "tickAi",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/LivingEntity;setPosition(DDD)V"
		),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void keepCopies(CallbackInfo ci, double d0, double d1, double d2, double d3) {
		this.usefulmodD0 = d0;
		this.usefulmodD1 = d1;
		this.usefulmodD2 = d2;
	}

	@Redirect(
		method = "tickAi",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/living/LivingEntity;setPosition(DDD)V"
		)
	)
	private void fixSquidAndWitherMovement(LivingEntity entity, double x, double y, double z) {
		if (entity instanceof WitherEntity) {
			entity.move(MoverType.SELF, this.usefulmodD0 - this.x, this.usefulmodD1 - this.y, this.usefulmodD2 - this.z);
		} else {
			entity.setPosition(x, y, z);
		}
	}
}
