package nessiesson.usefulmod.ClientCommands;

import java.util.List;
import java.util.stream.IntStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.Command;
import net.minecraft.server.command.handler.CommandRegistry;
import net.minecraft.server.command.source.CommandSource;
import net.minecraft.text.Formatting;
import net.minecraft.text.TranslatableText;

import static net.minecraft.text.Formatting.GRAY;
import static net.minecraft.text.Formatting.RESET;

public class ClientCommandManager extends CommandRegistry {

	public static final ClientCommandManager INSTANCE = new ClientCommandManager();

	public String[] latestAutoComplete = null;

	@Override
	public int run(CommandSource source, String message) {
		message = message.trim();

		if (message.startsWith("/")) {
			message = message.substring(1);
		}

		final String[] temp = message.split(" ");
		final String[] args = new String[temp.length - 1];
		final String commandName = temp[0];
		System.arraycopy(temp, 1, args, 0, args.length);
		final Command command = getCommands().get(commandName);
		if (command == null) {
			return 0;
		}

		try {
			this.run(source, args, command, message);
		} catch (Throwable t) {
			final TranslatableText error = new TranslatableText("commands.generic.exception");
			error.getStyle().setColor(Formatting.RED);
			source.sendMessage(error);
		}

		return -1;
	}

	public void autoComplete(String leftOfCursor) {
		this.latestAutoComplete = null;

		if (leftOfCursor.charAt(0) == '/') {
			leftOfCursor = leftOfCursor.substring(1);

			final Minecraft mc = Minecraft.getInstance();
			if (mc.screen instanceof ChatScreen) {
				final List<String> commands = this.getSuggestions(mc.player, leftOfCursor, mc.player.getSourceBlockPos());
				if (!commands.isEmpty()) {
					if (leftOfCursor.indexOf(' ') == -1) {
						IntStream.range(0, commands.size()).forEach(s -> commands.set(s, GRAY + "/" + commands.get(s) + RESET));
					} else {
						IntStream.range(0, commands.size()).forEach(s -> commands.set(s, GRAY + commands.get(s) + RESET));
					}

					this.latestAutoComplete = commands.toArray(new String[0]);
				}
			}
		}
	}

	@Override
	protected MinecraftServer getServer() {
		return Minecraft.getInstance().getServer();
	}
}
