package survivalblock.shield_surf.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.shield_surf.common.compat.config.ShieldSurfConfig;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;
import survivalblock.shield_surf.common.init.ShieldSurfEntityTypes;
import survivalblock.shield_surf.common.init.ShieldSurfGameRules;
import survivalblock.shield_surf.common.init.ShieldSurfSoundEvents;

public class ShieldSurf implements ModInitializer {
	public static final String MOD_ID = "shield_surf";
	public static final String FABRIC_SHIELD_LIB_ID = "fabricshieldlib";
	public static final Logger LOGGER = LoggerFactory.getLogger("Shield_Surf");
	public static boolean hasFabricShieldLib = false;
	public static boolean shouldDoConfig, configLoaded = false;

	@Override
	public void onInitialize() {
        //? if <1.21
		ShieldSurfEnchantments.init();
		ShieldSurfEntityTypes.init();
		ShieldSurfSoundEvents.init();
		ShieldSurfGameRules.init();
		if (!resetShouldDoConfig()) {
			LOGGER.warn("YACL is not installed, so Shield Surf's Config will not be accessible!");
		} else {
			configLoaded = ShieldSurfConfig.load();
			if (!configLoaded) {
				LOGGER.warn("Shield Surf Config could not be loaded!");
			}
		}
	}

	public static Identifier id(String value) {
		return /*? >=1.21.1 {*/ /*Identifier.of *//*?} else {*/ new Identifier /*?}*/(MOD_ID, value);
	}

	@SuppressWarnings("UnusedReturnValue")
    public static boolean resetHasFabricShieldLib() {
		hasFabricShieldLib = FabricLoader.getInstance().isModLoaded(FABRIC_SHIELD_LIB_ID);
		return hasFabricShieldLib;
	}

	public static boolean resetShouldDoConfig() {
		shouldDoConfig = FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3");
		return shouldDoConfig;
	}
}
