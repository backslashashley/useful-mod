package nessiesson.usefulmod.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.EntryListWidget;

import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.Option;
import net.ornithemc.osl.config.api.config.option.group.OptionGroup;

public class ConfigListWidget extends EntryListWidget {

	private final List<Entry> entries = Lists.newArrayList();

	public ConfigListWidget(Minecraft minecraft, int width, int height, int yStart, int yEnd, int entryHeight, OptionGroup group) {
		super(minecraft, width, height, yStart, yEnd, entryHeight);

		this.centerAlongY = false;

		List<BooleanOption> options = new ArrayList<>();
		for (Option option : group.getOptions()) {
			if (option instanceof BooleanOption) {
				options.add((BooleanOption) option);
			} else {
				throw new IllegalStateException("this config list widget only supports toggles!");
			}
		}

		for (int i = 0; i < options.size(); i += 2) {
			BooleanOption leftOption = options.get(i);
			BooleanOption rightOption = i < options.size() - 1 ? options.get(i + 1) : null;

			ConfigToggleWidget leftButton = this.createWidget(minecraft, i, width / 2 - 155, 0, leftOption);
			ConfigToggleWidget rightButton = this.createWidget(minecraft, i + 1, width / 2 - 155 + 160, 0, rightOption);

			this.entries.add(new Entry(leftButton, rightButton));
		}
	}

	private ConfigToggleWidget createWidget(Minecraft minecraft, int id, int x, int y, BooleanOption option) {
		if (option == null) {
			return null;
		}

		return new ConfigToggleWidget(id, x, y, option, Config.getValueAsString(option));
	}

	@Override
	public Entry getEntry(int i) {
		return this.entries.get(i);
	}

	@Override
	protected int size() {
		return this.entries.size();
	}

	@Override
	public int getRowWidth() {
		return 400;
	}

	@Override
	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 32;
	}

	public class Entry implements EntryListWidget.Entry {

		private final ConfigToggleWidget left;
		private final ConfigToggleWidget right;

		public Entry(ConfigToggleWidget left, ConfigToggleWidget right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public void render(int index, int x, int y, int width, int height, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			if (this.left != null) {
				this.left.y = y;
				this.left.render(ConfigListWidget.this.minecraft, mouseX, mouseY, tickDelta);
			}
			if (this.right != null) {
				this.right.y = y;
				this.right.render(ConfigListWidget.this.minecraft, mouseX, mouseY, tickDelta);
			}
		}

		@Override
		public boolean mouseClicked(int index, int mouseX, int mouseY, int button, int entryMouseX, int entryMouseY) {
			if (this.left.isMouseOver(ConfigListWidget.this.minecraft, mouseX, mouseY)) {
				this.left.getOption().set(!this.left.getOption().get());
				this.left.message = Config.getValueAsString(this.left.getOption());
				return true;
			}
			if (this.right != null && this.right.isMouseOver(ConfigListWidget.this.minecraft, mouseX, mouseY)) {
				this.right.getOption().set(!this.right.getOption().get());
				this.right.message = Config.getValueAsString(this.right.getOption());
				return true;
			}
			return false;
		}

		@Override
		public void mouseReleased(int index, int mouseX, int mouseY, int button, int entryMouseX, int entryMouseY) {
			if (this.left != null) {
				this.left.mouseReleased(mouseX, mouseY);
			}
			if (this.right != null) {
				this.right.mouseReleased(mouseX, mouseY);
			}
		}

		@Override
		public void renderOutOfBounds(int index, int x, int y, float tickDelta) {
		}
	}
}
