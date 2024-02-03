package nessiesson.usefulmod.config;

import net.minecraft.client.gui.widget.ButtonWidget;

import net.ornithemc.osl.config.api.config.option.BooleanOption;

public class ConfigToggleWidget extends ButtonWidget {

	private final BooleanOption option;

	public ConfigToggleWidget(int id, int x, int y, String message) {
		this(id, x, y, null, message);
	}

	public ConfigToggleWidget(int id, int x, int y, BooleanOption option, String message) {
		super(id, x, y, 150, 20, message);
		this.option = option;
	}

	public BooleanOption getOption() {
		return this.option;
	}
}
