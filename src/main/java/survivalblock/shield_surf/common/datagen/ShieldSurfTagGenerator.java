package survivalblock.shield_surf.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
//? if >=1.21
/*import net.minecraft.registry.tag.EnchantmentTags;*/
import survivalblock.shield_surf.common.init.ShieldSurfDamageTypes;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;
import survivalblock.shield_surf.common.init.ShieldSurfEntityTypes;
import survivalblock.shield_surf.common.init.ShieldSurfTags;

import java.util.concurrent.CompletableFuture;

public class ShieldSurfTagGenerator {

    public static class ShieldSurfDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
        public ShieldSurfDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                    .add(ShieldSurfDamageTypes.SHIELDBOARD_COLLISION, ShieldSurfDamageTypes.SHIELD_IMPACT);

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                    .add(ShieldSurfDamageTypes.SHIELDBOARD_COLLISION, ShieldSurfDamageTypes.SHIELD_IMPACT);

            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(ShieldSurfDamageTypes.SHIELD_IMPACT);
        }
    }

    //? if >=1.21 {
    /*public static class ShieldSurfEnchantmentTagGenerator extends FabricTagProvider<Enchantment> {
        public ShieldSurfEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.ENCHANTMENT, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {

            getOrCreateTagBuilder(EnchantmentTags.TREASURE)
                    .add(
                            ShieldSurfEnchantments.AEGIS,
                            ShieldSurfEnchantments.EXPULSION,
                            ShieldSurfEnchantments.ORBIT,
                            ShieldSurfEnchantments.REBOUND
                    );

            getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE)
                    .add(
                            ShieldSurfEnchantments.SHIELD_SURF,
                            ShieldSurfEnchantments.RAPID
                    );

            getOrCreateTagBuilder(ShieldSurfTags.ShieldSurfEnchantmentTags.EXCLUSIVE_SET)
                    .add(
                            ShieldSurfEnchantments.SHIELD_SURF,
                            ShieldSurfEnchantments.AEGIS,
                            ShieldSurfEnchantments.RAPID,
                            ShieldSurfEnchantments.EXPULSION,
                            ShieldSurfEnchantments.ORBIT,
                            ShieldSurfEnchantments.REBOUND
                    );
        }
    }
    *///?}

    public static class ShieldSurfEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
        public ShieldSurfEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(ShieldSurfTags.ShieldSurfEntityTypeTags.SHIELD_ENTITIES)
                    .add(ShieldSurfEntityTypes.SHIELDBOARD, ShieldSurfEntityTypes.PROJECTED_SHIELD);
        }
    }
}
