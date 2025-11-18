package survivalblock.shield_surf.mixin.vanilla.orbit.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.shield_surf.access.RenderHandleSometimesAccess;
import survivalblock.shield_surf.client.util.ShieldSurfClientUtil;
import survivalblock.shield_surf.common.compat.config.ShieldSurfConfig;
import survivalblock.shield_surf.common.component.ShieldSatellitesComponent;
import survivalblock.shield_surf.common.init.ShieldSurfEntityComponents;
import survivalblock.shield_surf.mixin.vanilla.shieldsurf.client.ItemRendererAccessor;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;setModelPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)V", shift = At.Shift.AFTER))
    private void renderSatellites(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci){
        boolean showBody = this.isVisible(player);
        MinecraftClient client = MinecraftClient.getInstance();
        boolean translucent = !showBody /*&& !player.isInvisibleTo(client.player)*/;
        boolean showOutline = client.hasOutline(player);
        ShieldSatellitesComponent satellitesComponent = ShieldSurfEntityComponents.SHIELD_SATELLITES.get(player);
        int satellites = satellitesComponent.getSatellites();
        if (satellites <= 0 || satellitesComponent.getItemStacksSize() <= 0) {
            return;
        }
        RenderLayer layer = ShieldSurfClientUtil.getOrbitingShieldsRenderLayer(showBody, translucent, showOutline, this.getTexture(player), this.model);
        if (!showBody) {
            if (layer == null) return;
            VertexConsumerProvider finalVertexConsumerProvider = vertexConsumerProvider;
            vertexConsumerProvider = (layer1 -> finalVertexConsumerProvider.getBuffer(layer));
        }
        int overlay = OverlayTexture.DEFAULT_UV;
        for (float i = 0; i < 360; i += 360 / (float) satellites) {
            matrixStack.push();
            matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(
                    i + (
                            satellitesComponent.getRotation() * (
                                    ShieldSurfConfig.orbitingShieldsRotateClockwise() ? 1 : -1
                            )
                    ) + (
                            ShieldSurfConfig.projectedShieldsRenderOutwards() ? 0 : 180
                    ) + tickDelta
            ));
            matrixStack.translate(0.0f, 1.1f, 1.6f);
            if (player.getHeight() <= 1) {
                if (ShieldSurfConfig.orbitingShieldsFlattenWhileSwimming()) {
                    matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
                    matrixStack.translate(-player.getHeight(), 0.0f, -0.0025f + 0.2 * Math.max(0, satellites - 8));
                } else {
                    matrixStack.translate(0.0f, -0.6, 0.0f);
                }
            }
            BuiltinModelItemRenderer builtinModelItemRenderer = ((ItemRendererAccessor) MinecraftClient.getInstance().getItemRenderer()).shield_surf$getBuiltinModelItemRenderer();
            ((RenderHandleSometimesAccess) builtinModelItemRenderer).shield_surf$setShouldRenderShieldHandle(false);
            builtinModelItemRenderer.render(satellitesComponent.getStack((int) (i * satellites / 360f)), ModelTransformationMode.NONE, matrixStack, vertexConsumerProvider, light, overlay);
            ((RenderHandleSometimesAccess) builtinModelItemRenderer).shield_surf$setShouldRenderShieldHandle(true);
            matrixStack.pop();
        }
    }
}
