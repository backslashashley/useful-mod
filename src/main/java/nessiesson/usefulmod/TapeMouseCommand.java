package nessiesson.usefulmod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import nessiesson.usefulmod.ClientCommands.mixins.KeyBindingAccessor;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AbstractCommand;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.source.CommandSource;
import net.minecraft.util.math.BlockPos;

public class TapeMouseCommand extends AbstractCommand {

	private static final Map<String, KeyBinding> KEYBIND_ARRAY = KeyBindingAccessor.getAll();

	@Override
	public String getName() {
		return "tm";
	}

	@Override
	public String getUsage(CommandSource source) {
		return this.getName() + " <sync|keybinding name> [delay|off]";
	}

	@Override
	public void run(MinecraftServer server, CommandSource source, String[] args) throws CommandException {
	}

	@Override
	public List<String> getSuggestions(MinecraftServer server, CommandSource source, String[] args, BlockPos pos) {
		if (args.length == 1) {
			final List<String> list = new ArrayList<>(Collections.singletonList("off"));
			for (KeyBinding keyBinding : KEYBIND_ARRAY.values()) {
				if (keyBinding == null || keyBinding.getKeyCode() == Keyboard.KEY_NONE) {
					continue;
				}

				final String name = keyBinding.getName().replaceFirst("^key\\.", "");
				list.add(name);
			}

			return suggestMatching(args, list);
		}

		return super.getSuggestions(server, source, args, pos);
	}
}
