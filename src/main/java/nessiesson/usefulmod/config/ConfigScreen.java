package nessiesson.usefulmod.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.resource.language.I18n;

import net.ornithemc.osl.config.api.config.option.group.OptionGroup;

public class ConfigScreen extends Screen {

	private static final int DONE = 200;

	private final Screen parent;
	private final OptionGroup group;

	private String title = "Config";
	private EntryListWidget list;

	public ConfigScreen(Screen parent) {
		this(parent, Config.getInstance().getGroup(Config.GROUP));
	}

	public ConfigScreen(Screen parent, OptionGroup group) {
		this.parent = parent;
		this.group = group;
	}

	@Override
	public void init() {
		this.title = Config.GROUP;

		this.buttons.clear();
		this.buttons.add(new ButtonWidget(DONE, this.width / 2 - 100, this.height - 27, I18n.translate("gui.done")));

		this.list = new ConfigListWidget(this.minecraft, this.width, this.height, 32, this.height - 32, 25, this.group);
	}

	@Override
	public void handleMouse() {
		super.handleMouse();
		this.list.handleMouse();
	}

	@Override
	protected void keyPressed(char chr, int key) {
		if (key == 1) {
			// this.minecraft.options.save();
			// TODO: save usefulmod config
		}
		super.keyPressed(chr, key);
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		if (!button.active) {
			return;
		}
		if (button.id == DONE) {
			// this.minecraft.options.save();
			// TODO: save usefulemod config
			this.minecraft.openScreen(this.parent);
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		this.renderBackground();
		this.list.render(mouseX, mouseY, tickDelta);
		this.drawCenteredString(this.textRenderer, this.title, this.width / 2, 5, 0xFFFFFF);
		super.render(mouseX, mouseY, tickDelta);
	}
}
