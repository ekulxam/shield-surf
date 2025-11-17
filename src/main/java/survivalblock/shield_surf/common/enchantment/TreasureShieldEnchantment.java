package survivalblock.shield_surf.common.enchantment;

public class TreasureShieldEnchantment extends ShieldSurfingEnchantment {

    public static final boolean TREASURE = true;

    public TreasureShieldEnchantment(Rarity weight) {
        this(1, weight);
    }

    public TreasureShieldEnchantment(int maxLevel, Rarity weight) {
        super(maxLevel, weight);
    }

    @Override
    public boolean isTreasure() {
        return TREASURE;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return !TREASURE;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return !TREASURE;
    }
}
