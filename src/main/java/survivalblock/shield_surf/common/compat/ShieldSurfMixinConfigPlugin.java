package survivalblock.shield_surf.common.compat;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import survivalblock.shield_surf.common.ShieldSurf;

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

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
