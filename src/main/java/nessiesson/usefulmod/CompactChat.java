package nessiesson.usefulmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.text.Formatting;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

// Inspired/stolen by https://github.com/Sk1erLLC/CompactChat
public class CompactChat {

	private Text lastMessage = new LiteralText("");
	private int amount;
	private int line;

	public boolean onChat(final Text message) {
		final ChatGui chat = Minecraft.getInstance().gui.getChat();
		if (message.getString().equals(this.lastMessage.getString())) {
			chat.removeMessage(this.line);
			final Text component = new LiteralText(String.format(" (%d)", ++this.amount));
			component.setStyle(new Style().setColor(Formatting.GRAY));
			message.append(component);
		} else {
			this.amount = 1;
			this.lastMessage = message;
		}

		chat.addMessage(message, ++this.line);
		if (this.line > 256) {
			this.line = 0;
		}

		return true;
	}
}
