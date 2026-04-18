package survivalblock.shield_surf.common.init;

import com.google.common.collect.ImmutableMap;
//? if >=1.21 {
/*import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
*///?}
import net.minecraft.enchantment.Enchantment;
//? if <1.21
import net.minecraft.item.Item;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.World;
import survivalblock.shield_surf.common.ShieldSurf;
//? if <1.21
import survivalblock.shield_surf.common.enchantment.*;

import java.util.Map;

public class ShieldSurfEnchantments {

    //? if <=1.21 {
    public static final Enchantment SHIELD_SURF = new ShieldSurfingEnchantment(3, Enchantment.Rarity.RARE);

    public static final Enchantment AEGIS = new TreasureShieldEnchantment(Enchantment.Rarity.VERY_RARE);

    public static final Enchantment RAPID = new ShieldSurfingEnchantment(Enchantment.Rarity.UNCOMMON);

    public static final Enchantment EXPULSION = new TreasureShieldEnchantment(6, Enchantment.Rarity.RARE);

    public static final Enchantment ORBIT = new TreasureShieldEnchantment(Enchantment.Rarity.VERY_RARE);

    public static final Enchantment REBOUND = new TreasureShieldEnchantment(Enchantment.Rarity.VERY_RARE);

    public static final Enchantment CLOCKWORK = new ClockworkEnchantment();

    public static void init() {
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("shield_surf"), SHIELD_SURF);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("aegis"), AEGIS);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("rapid"), RAPID);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("expulsion"), EXPULSION);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("orbit"), ORBIT);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("rebound"), REBOUND);
        Registry.register(Registries.ENCHANTMENT, ShieldSurf.id("clockwork"), CLOCKWORK);
    }

    //?} else {
    /*public static final RegistryKey<Enchantment> SHIELD_SURF = of("shield_surf");
    public static final RegistryKey<Enchantment> AEGIS = of("aegis");
    public static final RegistryKey<Enchantment> RAPID = of("rapid");
    public static final RegistryKey<Enchantment> EXPULSION = of("expulsion");
    public static final RegistryKey<Enchantment> ORBIT = of("orbit");
    public static final RegistryKey<Enchantment> REBOUND = of("rebound");
    public static final RegistryKey<Enchantment> CLOCKWORK = of("clockwork");

    private static RegistryKey<Enchantment> of(String value) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, ShieldSurf.id(value));
    }

    // thanks mojang, I love data-driven enchantments (not sarcasm)
    public static RegistryEntry.Reference<Enchantment> get(RegistryKey<Enchantment> key, World world) {
        return world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(key);
    }

    /^*
     * Creates a map with the {@link RegistryKey<Enchantment>}s as keys and {@link Enchantment}s as values
     * @return an {@link ImmutableMap}
     ^/
    public static ImmutableMap<RegistryKey<Enchantment>, Enchantment> asEnchantments(RegistryEntryLookup<Enchantment> enchantmentLookup, RegistryEntryLookup<Item> itemLookup) {
        ImmutableMap.Builder<RegistryKey<Enchantment>, Enchantment> enchantments = ImmutableMap.builder();
        RegistryEntryList<Item> shields = itemLookup.getOrThrow(ConventionalItemTags.SHIELD_TOOLS);
        RegistryEntryList<Enchantment> exclusiveSet = enchantmentLookup.getOrThrow(ShieldSurfTags.ShieldSurfEnchantmentTags.EXCLUSIVE_SET);
        addToBuilder(enchantments, SHIELD_SURF, 3, Rarity.RARE, shields, exclusiveSet);
        addToBuilder(enchantments, AEGIS, 1, Rarity.VERY_RARE, shields, exclusiveSet);
        addToBuilder(enchantments, RAPID, 1, Rarity.UNCOMMON, shields, exclusiveSet);
        addToBuilder(enchantments, EXPULSION, 6, Rarity.RARE, shields, exclusiveSet);
        addToBuilder(enchantments, ORBIT, 1, Rarity.RARE, shields, exclusiveSet);
        addToBuilder(enchantments, REBOUND, 1, Rarity.RARE, shields, exclusiveSet);
        addToBuilder(enchantments, CLOCKWORK, 1, Rarity.RARE, shields, exclusiveSet);
        return enchantments.build();
    }

    public static void addToBuilder(ImmutableMap.Builder<RegistryKey<Enchantment>, Enchantment> builder, RegistryKey<Enchantment> enchantment, int maxLevel, Rarity rarity, RegistryEntryList<Item> shields, RegistryEntryList<Enchantment> exclusiveSet) {
        builder.put(enchantment, Enchantment.builder(
                Enchantment.definition(
                        shields,
                        rarity.getWeight(),
                        maxLevel,
                        Enchantment.leveledCost(2, 8),
                        Enchantment.leveledCost(6, 8),
                        rarity.getAnvilCost(),
                        AttributeModifierSlot.MAINHAND, AttributeModifierSlot.OFFHAND)
                        )
                .exclusiveSet(exclusiveSet)
                .build(
                        enchantment.getValue()
                )
        );
    }

    public static void bootstrap(Registerable<Enchantment> registerable) {
        for (Map.Entry<RegistryKey<Enchantment>, Enchantment> entry : asEnchantments(
                registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT),
                registerable.getRegistryLookup(RegistryKeys.ITEM)
        ).entrySet()) {
            registerable.register(entry.getKey(), entry.getValue());
        }
    }

    public enum Rarity {
        @SuppressWarnings("unused")
        COMMON(10, 1),
        UNCOMMON(5, 2),
        RARE(2, 4),
        VERY_RARE(1, 8);

        private final int weight;
        private final int anvilCost;

        Rarity(int weight, int anvilCost) {
            this.weight = weight;
            this.anvilCost = anvilCost;
        }

        public int getWeight() {
            return this.weight;
        }

        public int getAnvilCost() {
            return this.anvilCost;
        }
    }
    *///?}
}
