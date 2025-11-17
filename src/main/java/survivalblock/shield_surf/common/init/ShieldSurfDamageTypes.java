package survivalblock.shield_surf.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import survivalblock.shield_surf.common.ShieldSurf;

import java.util.Map;

public class ShieldSurfDamageTypes {
    public static final RegistryKey<DamageType> SHIELDBOARD_COLLISION = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, ShieldSurf.id("shieldboard_collision"));
    public static final RegistryKey<DamageType> SHIELD_IMPACT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, ShieldSurf.id("shield_impact"));

    public static RegistryEntry.Reference<DamageType> get(RegistryKey<DamageType> key, World world) {
        return world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key);
    }

    /**
     * Creates a map with the {@link RegistryKey<DamageType>}s as keys and {@link DamageType}s as values
     * @return an {@link ImmutableMap}
     */
    public static ImmutableMap<RegistryKey<DamageType>, DamageType> asDamageTypes() {
        ImmutableMap.Builder<RegistryKey<DamageType>, DamageType> damageTypes = ImmutableMap.builder();
        damageTypes.put(SHIELDBOARD_COLLISION, new DamageType("shield_surf.shieldboard_collision", 0.1F));
        damageTypes.put(SHIELD_IMPACT, new DamageType("shield_surf.shield_impact", 0.1F));
        return damageTypes.build();
    }

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : asDamageTypes().entrySet()) {
            damageTypeRegisterable.register(entry.getKey(), entry.getValue());
        }
    }
}
