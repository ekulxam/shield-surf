package survivalblock.shield_surf.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.shield_surf.common.ShieldSurf;

public class ShieldSurfTags {

    //? if >=1.21 {
    /*public static class ShieldSurfEnchantmentTags {
        public static final TagKey<Enchantment> EXCLUSIVE_SET = TagKey.of(RegistryKeys.ENCHANTMENT, ShieldSurf.id("exclusive_set"));
    }
    *///?}

    public static class ShieldSurfEntityTypeTags {
        public static final TagKey<EntityType<?>> SHIELD_ENTITIES = TagKey.of(RegistryKeys.ENTITY_TYPE, ShieldSurf.id("shield_entities"));
    }
}
