package survivalblock.shield_surf.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.shield_surf.common.ShieldSurf;
import survivalblock.shield_surf.common.enchantment.*;

public class ShieldSurfEnchantments {

    public static final Enchantment SHIELD_SURF = new ShieldSurfingEnchantment(3, Enchantment.Rarity.RARE);

    public static final Enchantment AEGIS = new TreasureShieldEnchantment(Enchantment.Rarity.VERY_RARE);

    public static final Enchantment RAPID = new ShieldSurfingEnchantment(Enchantment.Rarity.UNCOMMON);

    public static final Enchantment EXPULSION = new TreasureShieldEnchantment(6, Enchantment.Rarity.RARE);

    public static final Enchantment ORBIT = new TreasureShieldEnchantment(Enchantment.Rarity.VERY_RARE);

    public static final Enchantment REBOUND = new TreasureShieldEnchantment(Enchantment.Rarity.VERY_RARE);

    public static void init() {
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("shield_surf"), SHIELD_SURF);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("aegis"), AEGIS);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("rapid"), RAPID);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("expulsion"), EXPULSION);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("orbit"), ORBIT);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("rebound"), REBOUND);
    }
}
