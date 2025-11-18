//? if =1.21.1 {
/*package survivalblock.shield_surf.mixin.fabricshieldlib.devfix;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings("UnusedMixin")
@Debug(export = true)
@Mixin(value = FabricShieldLib.class, remap = false)
public class FabricShieldLibMixin {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @WrapOperation(method = "lambda$onInitialize$1", at = @At(value = "INVOKE", target = "Ljava/util/Optional;get()Ljava/lang/Object;", remap = false), remap = true)
    private static Object andWhyDidYouNotRunDatagenBeforePublishing(Optional<?> instance, Operation<?> original, @Cancellable CallbackInfoReturnable<ActionResult> cir) {
        if (instance.isEmpty()) {
            cir.setReturnValue(ActionResult.PASS);
            return null;
        }
        return original.call(instance);
    }
}
*///?}