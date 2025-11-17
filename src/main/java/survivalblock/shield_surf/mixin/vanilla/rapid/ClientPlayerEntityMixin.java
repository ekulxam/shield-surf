package survivalblock.shield_surf.mixin.vanilla.rapid;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;
import survivalblock.shield_surf.common.util.ShieldSurfUtil;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private boolean noSlowdown(boolean original){
        return doesNotImpedeMovement(original);
    }

    @ModifyExpressionValue(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean doesNotDisableSprinting(boolean original){
        return doesNotImpedeMovement(original);
    }

    @Unique
    private boolean doesNotImpedeMovement(boolean other){
        return (!ShieldSurfUtil.isAShield(this.getActiveItem()) || EnchantmentHelper.getLevel(/*? >=1.21 {*/ /*ShieldSurfEnchantments.get(ShieldSurfEnchantments.RAPID, this.getWorld())*//*?} else {*/ ShieldSurfEnchantments.RAPID /*?}*/, this.getActiveItem()) <= 0) && other;
    }
}
