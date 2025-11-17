//? if <1.21 {
package survivalblock.shield_surf.mixin.vanilla.vanillachanges.enchantableshields;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.shield_surf.common.enchantment.ShieldSurfingEnchantment;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"))
    private static boolean returnCorrectForShieldEnchantments(EnchantmentTarget instance, Item item, Operation<Boolean> original, int power, ItemStack stack, @Local Enchantment enchantment){
        if (enchantment instanceof ShieldSurfingEnchantment) {
            return enchantment.isAcceptableItem(stack);
        }
        return original.call(instance, item);
    }
}
//?}