package nessiesson.usefulmod.ClientCommands.mixins;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.options.KeyBinding;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {

	@Accessor("ALL")
	static Map<String, KeyBinding> getAll() {
		throw new UnsupportedClassVersionError("Requires Java 8");
	}
}
