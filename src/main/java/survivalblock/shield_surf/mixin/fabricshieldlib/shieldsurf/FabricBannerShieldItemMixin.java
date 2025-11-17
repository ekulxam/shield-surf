package survivalblock.shield_surf.mixin.fabricshieldlib.shieldsurf;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricBannerShieldItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.shield_surf.common.util.ShieldSurfUtil;

@Mixin(FabricBannerShieldItem.class)
public abstract class FabricBannerShieldItemMixin extends Item {

    public FabricBannerShieldItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setCurrentHand(Lnet/minecraft/util/Hand;)V"), cancellable = true)
    private void rideTheLightning(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack stack){
        if (ShieldSurfUtil.rideTheLightning(world, user, this, stack)) {
            cir.setReturnValue(TypedActionResult.success(stack));
            user.stopUsingItem();
        }
    }
}
