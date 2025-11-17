//? if <1.21 {
package survivalblock.shield_surf.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import survivalblock.shield_surf.common.util.ShieldSurfUtil;

public class ShieldSurfingEnchantment extends Enchantment {

    public static final EquipmentSlot[] HAND_SLOTS;

    static {
        HAND_SLOTS = new EquipmentSlot[2];
        HAND_SLOTS[0] = EquipmentSlot.MAINHAND;
        HAND_SLOTS[1] = EquipmentSlot.OFFHAND;
    }

    private final int maxLevel;

    public ShieldSurfingEnchantment(Rarity weight) {
        this(1, weight);
    }

    public ShieldSurfingEnchantment(int maxLevel, Rarity weight) {
        super(weight, EnchantmentTarget.FISHING_ROD, HAND_SLOTS);
        this.maxLevel = maxLevel;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return ShieldSurfUtil.isAShield(stack);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && ShieldSurfUtil.cancelShieldEnchantments(this, other);
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }
}
//?}