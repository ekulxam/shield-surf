package survivalblock.shield_surf.common.compat;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import survivalblock.shield_surf.common.ShieldSurf;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static survivalblock.shield_surf.common.ShieldSurf.FABRIC_SHIELD_LIB_ID;

public class ShieldSurfMixinConfigPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        ShieldSurf.resetShouldDoConfig();
        if (!ShieldSurf.resetHasFabricShieldLib()) {
            ShieldSurf.LOGGER.debug("No mods with mod id \"" + FABRIC_SHIELD_LIB_ID + "\" have been found. Mixins for FabricShieldLib will not be loaded.");
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (targetClassName.contains(FABRIC_SHIELD_LIB_ID)) {
            if (mixinClassName.contains("devfix")) {
                return ShieldSurf.hasFabricShieldLib && FabricLoader.getInstance().isDevelopmentEnvironment();
            }
            return ShieldSurf.hasFabricShieldLib;
        }
        if (targetClassName.contains("config") && mixinClassName.contains("config")) {
            return ShieldSurf.shouldDoConfig;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    /**
     * Returns all enabled mixins for this version
     */
    @Override
    public List<String> getMixins() {
        return Arrays.asList(
            "fabricshieldlib.expulsion.FabricShieldItemMixin",
            "fabricshieldlib.orbit.FabricShieldItemMixin",
            "fabricshieldlib.shieldsurf.FabricShieldItemMixin",
            "vanilla.aegis.LivingEntityMixin",
            "vanilla.aegis.PlayerEntityMixin",
            "vanilla.clockwork.LivingEntityMixin",
            "vanilla.expulsion.LivingEntityMixin",
            "vanilla.expulsion.ShieldItemMixin",
            "vanilla.orbit.PlayerEntityMixin",
            "vanilla.orbit.ShieldItemMixin",
            "vanilla.rebound.LivingEntityMixin",
            "vanilla.rebound.MinecraftServerAccessor",
            "vanilla.rebound.TridentEntityAccessor",
            "vanilla.shieldsurf.ServerPlayNetworkHandlerMixin",
            "vanilla.shieldsurf.ShieldItemMixin",
            "vanilla.vanillachanges.enchantableshields.ItemMixin",
            "vanilla.vanillachanges.enchantableshields.ShieldItemMixin"
        );
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
