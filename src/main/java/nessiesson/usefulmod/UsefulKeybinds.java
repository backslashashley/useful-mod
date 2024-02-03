package nessiesson.usefulmod;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import nessiesson.usefulmod.config.Config;
import nessiesson.usefulmod.mixins.SoundManagerAccessor;
import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.Option;
import net.ornithemc.osl.config.api.config.option.group.OptionGroup;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;

public class UsefulKeybinds {

	public static final String CATEGORY = "UsefulMod";

	public static final List<KeyBinding> CONFIG = new ArrayList<>();
	public static final List<TimedKeyBinding> TAPE_MOUSEABLE = new ArrayList<>();

	public static final KeyBinding HIGHLIGHT_ENTITIES = new KeyBinding("key.usefulmod.highlight_entities", Keyboard.KEY_LMENU, CATEGORY);
	public static final KeyBinding RELOAD_AUDIO_ENGINE = new KeyBinding("key.usefulmod.reload_audio", Keyboard.KEY_B, CATEGORY);

	public static void init() {
		// options
		for (OptionGroup group : Config.getInstance().getGroups()) {
			for (Option option : group.getOptions()) {
				CONFIG.add(new KeyBinding(option.getName(), Keyboard.KEY_NONE, group.getName()));
			}
		}

		KeyBindingEvents.REGISTER_KEYBINDS.register(registry -> {
			for (KeyBinding keybind : CONFIG) {
				registry.register(keybind);
			}

			registry.register(HIGHLIGHT_ENTITIES);
			registry.register(RELOAD_AUDIO_ENGINE);
		});
		MinecraftClientEvents.TICK_END.register(minecraft -> {
			if (minecraft.world == null) {
				return;
			}

			final Config config = Config.getInstance();
			final OptionGroup group = config.getGroup(CATEGORY);

			for (KeyBinding key : CONFIG) {
				if (key.consumeClick()) {
					final Option o = group.getOption(key.getName());
					final BooleanOption option = (BooleanOption) o;
					final boolean state = option.get();

					option.set(!state);
					minecraft.gui.setOverlayMessage(String.format("%s %s.", option.getName(), state ? "enabled" : "disabled"), false);
				}
			}

			if (RELOAD_AUDIO_ENGINE.consumeClick()) {
				((SoundManagerAccessor) minecraft.getSoundManager()).getEngine().reload();
				UsefulMod.debugFeedBack();
			}
		});
	}
}
