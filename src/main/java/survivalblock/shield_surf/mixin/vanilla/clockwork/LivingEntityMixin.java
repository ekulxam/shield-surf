package survivalblock.shield_surf.mixin.vanilla.clockwork;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;
import survivalblock.shield_surf.common.util.ShieldSurfUtil;


// Manages the Clockwork shield blocking mechanics

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique private static final int CLOCKWORK_BLOCK_DURATION = 5;
    @Unique private static final int CLOCKWORK_COOLDOWN = 15;

    @Unique private int clockwork_cooldownRemaining = 0;


//  Get active item using reflection to avoid shadowing issues.
    @Unique
    private ItemStack getClockworkActiveItem() {
        try {
            java.lang.reflect.Field field = ((Object) this).getClass().getDeclaredField("activeItem");
            field.setAccessible(true);
            return (ItemStack) field.get(this);
        } catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }

    @Unique
    private int getClockworkActiveItemStack() {
        try {
            java.lang.reflect.Field field = ((Object) this).getClass().getDeclaredField("activeItemStack");
            field.setAccessible(true);
            return (int) field.get(this);
        } catch (Exception e) {
            return 0;
        }
    }


    @Unique
    private void setClockworkActiveItemStack(int value) {
        try {
            java.lang.reflect.Field field = ((Object) this).getClass().getDeclaredField("activeItemStack");
            field.setAccessible(true);
            field.set(this, value);
        } catch (Exception e) {
            // Silent fail
        }
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void tickClockworkCooldown(CallbackInfo ci) {
        if (((LivingEntity) (Object) this).getWorld().isClient()) {
            return;
        }

        // Decrement cooldown timer
        if (this.clockwork_cooldownRemaining > 0) {
            this.clockwork_cooldownRemaining--;
        }

        ItemStack activeItem = this.getClockworkActiveItem();
        if (!ShieldSurfUtil.isAShield(activeItem)) {
            return;
        }

        // Get the Clockwork enchantment from registry
        var clockworkEntry = ((LivingEntity) (Object) this).getWorld().getRegistryManager()
            .get(RegistryKeys.ENCHANTMENT)
            .getEntry(ShieldSurfEnchantments.CLOCKWORK)
            .orElse(null);
        
        if (clockworkEntry == null) {
            return;
        }

        int clockworkLevel = EnchantmentHelper.getLevel(clockworkEntry, activeItem);
        if (clockworkLevel <= 0) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) livingEntity;
        int activeStack = this.getClockworkActiveItemStack();

        if (player.isUsingItem() && activeStack > CLOCKWORK_BLOCK_DURATION && this.clockwork_cooldownRemaining == 0) {
            this.clockwork_cooldownRemaining = CLOCKWORK_COOLDOWN;
            player.stopUsingItem();
        }
    }


//     Blocks only within the 5 tick window and when not on cooldown

    @Inject(method = "isBlocking", at = @At("HEAD"), cancellable = true)
    private void handleClockworkBlocking(CallbackInfoReturnable<Boolean> cir) {
        // Cast to LivingEntity to access living entity methods
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack activeItem = this.getClockworkActiveItem();
        
        if (!ShieldSurfUtil.isAShield(activeItem)) {
            return;
        }

        // Get the Clockwork enchantment from registry
        var clockworkEntry = livingEntity.getWorld().getRegistryManager()
            .get(RegistryKeys.ENCHANTMENT)
            .getEntry(ShieldSurfEnchantments.CLOCKWORK)
            .orElse(null);
        
        if (clockworkEntry == null) {
            return;
        }

        int clockworkLevel = EnchantmentHelper.getLevel(clockworkEntry, activeItem);
        if (clockworkLevel <= 0) {
            return;
        }

        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) livingEntity;

        if (this.clockwork_cooldownRemaining > 0) {
            cir.setReturnValue(false);
            return;
        }

        if (!player.isUsingItem()) {
            cir.setReturnValue(false);
            return;
        }

        int activeStack = this.getClockworkActiveItemStack();
        boolean isInBlockWindow = activeStack > 0 && activeStack <= CLOCKWORK_BLOCK_DURATION;
        cir.setReturnValue(isInBlockWindow);
    }
}
