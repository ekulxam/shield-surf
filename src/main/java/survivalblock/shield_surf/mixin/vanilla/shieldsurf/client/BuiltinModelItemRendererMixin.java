package survivalblock.shield_surf.mixin.vanilla.shieldsurf.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.shield_surf.access.RenderHandleSometimesAccess;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin implements RenderHandleSometimesAccess {

    @Unique
    private boolean shouldRenderShieldHandle = true;

    @Override
    public void shield_surf$setShouldRenderShieldHandle(boolean shouldRender) {
        this.shouldRenderShieldHandle = shouldRender;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target =
            //? if <1.21 {
            "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"
            //?} else {
            /*"Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"
            *///?}
            , ordinal = 0))
    private boolean renderShieldHandleIfNotShieldboard(
            //? if <1.21 {
            ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha
             //?} else {
            /*ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay
            *///?}
    ){
        return shouldRenderShieldHandle;
    }
}
