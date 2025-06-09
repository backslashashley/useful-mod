package nessiesson.usefulmod.config;

import net.minecraft.client.resource.language.I18n;

import net.ornithemc.osl.config.api.ConfigManager;
import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;

public class Config extends BaseConfig {

	static final String GROUP = "UsefulMod";

	public static final BooleanOption ALWAYS_DAY = new BooleanOption("alwaysDay", null, false);
	public static final BooleanOption ALWAYS_NIGHT = new BooleanOption("alwaysNight", null, false);
	public static final BooleanOption ALWAYS_PICK_BLOCK_MAX_STACK = new BooleanOption("alwaysPickBlockMaxStack", null, false);
	public static final BooleanOption ALWAYS_RENDER_BLOCK_ENTITIES = new BooleanOption("alwaysRenderBlockEntities", null, false);
	public static final BooleanOption ALWAYS_SINGLE_PLAYER_CHEATS = new BooleanOption("alwaysSingleplayerCheats", null, false);
	public static final BooleanOption CLICK_BLOCK_MINING = new BooleanOption("clickBlockMining", null, false);
	public static final BooleanOption CLIENT_ENTITY_UPDATES = new BooleanOption("clientEntityUpdates", null, true);
	public static final BooleanOption CRAFTING_HAX = new BooleanOption("craftingHax", null, false);
	public static final BooleanOption DEATH_LOCATION = new BooleanOption("deathLocation", null, false);
	public static final BooleanOption EXTENDED_CHAT = new BooleanOption("extendedChat", null, false);
	public static final BooleanOption FIX_BLOCK_36_PARTICLES = new BooleanOption("fixBlock36Particles", null, false);
	public static final BooleanOption FLIGHT_INERTIA_CANCELLATION = new BooleanOption("flightInertiaCancellation", null, false);
	public static final BooleanOption INSTANT_DOUBLE_RETRACTION = new BooleanOption("instantDoubleRetraction", null, false);
	public static final BooleanOption JUMP_BOOST_STEP_ASSIST = new BooleanOption("jumpBoostStepAssist", null, false);
	public static final BooleanOption MINING_GHOST_BLOCK_FIX = new BooleanOption("miningGhostBlockFix", null, false);
	public static final BooleanOption NO_FALL = new BooleanOption("noFall", null, false);
	public static final BooleanOption RESPAWN_ON_DEATH = new BooleanOption("respawnOnDeath", null, false);
	public static final BooleanOption SHOW_BEACON_RANGE = new BooleanOption("showBeaconRange", null, false);
	public static final BooleanOption SHOW_BLOCK_BREAKING_PARTICLES = new BooleanOption("showBlockBreakingParticles", null, true);
	public static final BooleanOption SHOW_CENTERED_PLANTS = new BooleanOption("showCenteredPlants", null, false);
	public static final BooleanOption SHOW_CLEAR_LAVA = new BooleanOption("showClearLava", null, false);
	public static final BooleanOption SHOW_DEATH_ANIMATION = new BooleanOption("showDeathAnimation", null, true);
	public static final BooleanOption SHOW_EXPLOSION = new BooleanOption("showExplosion", null, false);
	public static final BooleanOption SHOW_GUI_BACKGROUND = new BooleanOption("showGuiBackGround", null, true);
	public static final BooleanOption SHOW_HAND = new BooleanOption("", null, true);
	public static final BooleanOption SHOW_IDEAL_TOOL_MARKER = new BooleanOption("showIdealToolMarker", null, false);
	public static final BooleanOption SHOW_INSANE_BLOCK_BREAKING_PARTICLES = new BooleanOption("showInsaneBlockBreakingParticles", null, false);
	public static final BooleanOption SHOW_ITEM_ATTRIBUTES = new BooleanOption("showItemAttributes", null, true);
	public static final BooleanOption SHOW_ITEM_fRAME_FRAME = new BooleanOption("showItemFrameFrame", null, true);
	public static final BooleanOption SHOW_ONE_BOSS_BAR = new BooleanOption("showOneBossBar", null, false);
	public static final BooleanOption SHOW_POTION_SHIFT = new BooleanOption("showPotionShift", null, true);
	public static final BooleanOption SHOW_RAIN = new BooleanOption("showRain", null, true);
	public static final BooleanOption SHOW_RAINBOW_LEAVES = new BooleanOption("showRainbowLeaves", null, false);
	public static final BooleanOption SHOW_SERVER_NAMES = new BooleanOption("showServerNames", null, true);
	public static final BooleanOption SHOW_SMOOTH_WATER_LIGHTING = new BooleanOption("showSmoothWaterLighting", null, false);
	public static final BooleanOption SHOW_SNEAK_EYEHEIGHT = new BooleanOption("showSneakEyeHeight", null, false);
	public static final BooleanOption SHOW_SNOW_DRIP_PARTICLES = new BooleanOption("showSnowDripParticles", null, false);
	public static final BooleanOption SHOW_TEAM_MENU = new BooleanOption("showTeamMenu", null, true);
	public static final BooleanOption SHULKER_BOX_DISPLAY = new BooleanOption("shulkerBoxDisplay", null, false);
	public static final BooleanOption SMOOTH_ITEM_MOVEMENT = new BooleanOption("smoothItemMovement", null, false);
	public static final BooleanOption SORT_ENCHANTMENT_TOOLTIP = new BooleanOption("sortEnchantmentTooltip", null, false);
	public static final BooleanOption STEP_ASSIST = new BooleanOption("stepAssist", null, false);
	public static final BooleanOption UNSTEP_ASSIST = new BooleanOption("unstepAssist", null, false);
	public static final BooleanOption TEMP_BLACK_SKY = new BooleanOption("zzTempBlackSky", null, false);
	public static final BooleanOption TEMP_MASA_FOV_FIX = new BooleanOption("zzTempMasaFoVFix", null, false);

	@Override
	public String getNamespace() {
		return "usefulmod";
	}

	@Override
	public String getName() {
		return "UsefulMod";
	}

	@Override
	public String getSaveName() {
		return "usefulmod.json";
	}

	@Override
	public ConfigScope getScope() {
		return ConfigScope.GLOBAL;
	}

	@Override
	public LoadingPhase getLoadingPhase() {
		return LoadingPhase.START;
	}

	@Override
	public FileSerializerType<?> getType() {
		return SerializerTypes.JSON;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public void init() {
		this.registerOptions(
			GROUP,
			ALWAYS_DAY,
			ALWAYS_NIGHT,
			ALWAYS_PICK_BLOCK_MAX_STACK,
			ALWAYS_RENDER_BLOCK_ENTITIES,
			ALWAYS_SINGLE_PLAYER_CHEATS,
			CLICK_BLOCK_MINING,
			CLIENT_ENTITY_UPDATES,
			CRAFTING_HAX,
			DEATH_LOCATION,
			EXTENDED_CHAT,
			FIX_BLOCK_36_PARTICLES,
			FLIGHT_INERTIA_CANCELLATION,
			INSTANT_DOUBLE_RETRACTION,
			JUMP_BOOST_STEP_ASSIST,
			MINING_GHOST_BLOCK_FIX,
			NO_FALL,
			RESPAWN_ON_DEATH,
			SHOW_BEACON_RANGE,
			SHOW_BLOCK_BREAKING_PARTICLES,
			SHOW_CENTERED_PLANTS,
			SHOW_CLEAR_LAVA,
			SHOW_DEATH_ANIMATION,
			SHOW_EXPLOSION,
			SHOW_GUI_BACKGROUND,
			SHOW_HAND,
			SHOW_IDEAL_TOOL_MARKER,
			SHOW_INSANE_BLOCK_BREAKING_PARTICLES,
			SHOW_ITEM_ATTRIBUTES,
			SHOW_ITEM_fRAME_FRAME,
			SHOW_ONE_BOSS_BAR,
			SHOW_POTION_SHIFT,
			SHOW_RAIN,
			SHOW_RAINBOW_LEAVES,
			SHOW_SERVER_NAMES,
			SHOW_SMOOTH_WATER_LIGHTING,
			SHOW_SNEAK_EYEHEIGHT,
			SHOW_SNOW_DRIP_PARTICLES,
			SHOW_TEAM_MENU,
			SHULKER_BOX_DISPLAY,
			SMOOTH_ITEM_MOVEMENT,
			SORT_ENCHANTMENT_TOOLTIP,
			STEP_ASSIST,
			UNSTEP_ASSIST,
			TEMP_BLACK_SKY,
			TEMP_MASA_FOV_FIX
		);
	}

	private static Config instance;

	public static void register() {
		if (instance == null) {
			ConfigManager.register(instance = new Config());
		}
	}

	public static Config getInstance() {
		return instance;
	}

	public static String getValueAsString(BooleanOption option) {
		String s = I18n.translate(option.getName()) + ": ";
		if (option.get()) {
			s += I18n.translate("options.on");
		} else {
			s += I18n.translate("options.off");
        }
        return s;
	}
}
