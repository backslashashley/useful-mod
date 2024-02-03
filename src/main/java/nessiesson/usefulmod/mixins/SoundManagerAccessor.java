package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.sound.system.SoundEngine;
import net.minecraft.client.sound.system.SoundManager;

@Mixin(SoundManager.class)
public interface SoundManagerAccessor {

	@Accessor("engine")
	SoundEngine getEngine();

}
