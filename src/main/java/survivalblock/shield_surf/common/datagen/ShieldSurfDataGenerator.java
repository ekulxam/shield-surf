package survivalblock.shield_surf.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import survivalblock.shield_surf.common.init.ShieldSurfDamageTypes;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;

public class ShieldSurfDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ShieldSurfEnUsLangGenerator::new);
        pack.addProvider(ShieldSurfTagGenerator.ShieldSurfDamageTypeTagGenerator::new);
        //? if >=1.21
		/*pack.addProvider(ShieldSurfTagGenerator.ShieldSurfEnchantmentTagGenerator::new);*/
		pack.addProvider(ShieldSurfTagGenerator.ShieldSurfEntityTypeTagGenerator::new);
		pack.addProvider(ShieldSurfDynamicRegistryGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, ShieldSurfDamageTypes::bootstrap);
        //? if >=1.21
        /*registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ShieldSurfEnchantments::bootstrap);*/
    }
}
