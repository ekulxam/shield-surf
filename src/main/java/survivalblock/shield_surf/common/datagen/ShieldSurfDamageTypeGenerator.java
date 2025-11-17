package survivalblock.shield_surf.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.shield_surf.common.init.ShieldSurfDamageTypes;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ShieldSurfDamageTypeGenerator extends FabricDynamicRegistryProvider {

    public ShieldSurfDamageTypeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : ShieldSurfDamageTypes.asDamageTypes().entrySet()) {
            entries.add(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String getName() {
        return "Damage types";
    }
}
