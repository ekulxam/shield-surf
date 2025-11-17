package survivalblock.shield_surf.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import survivalblock.shield_surf.access.RenderHandleSometimesAccess;
import survivalblock.shield_surf.common.compat.config.ShieldSurfConfig;
import survivalblock.shield_surf.common.entity.ProjectedShieldEntity;
import survivalblock.shield_surf.mixin.vanilla.shieldsurf.client.ItemRendererAccessor;

public class ProjectedShieldEntityRenderer extends EntityRenderer<ProjectedShieldEntity> {

    public static final Identifier TEXTURE = /*? >=1.21.1 {*/ /*Identifier.ofVanilla *//*?} else {*/ new Identifier /*?}*/("textures/entity/shield_base_nopattern.png");

    public ProjectedShieldEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ProjectedShieldEntity projectedShield, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        ItemStack stack = projectedShield.asItemStack();
        int overlay = OverlayTexture.DEFAULT_UV;
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw + (ShieldSurfConfig.projectedShieldsRenderOutwards() ? 0 : 180)));
        matrixStack.translate(0.0f, 0.6f, 0.0f);
        BuiltinModelItemRenderer builtinModelItemRenderer = ((ItemRendererAccessor) MinecraftClient.getInstance().getItemRenderer()).shield_surf$getBuiltinModelItemRenderer();
        ((RenderHandleSometimesAccess) builtinModelItemRenderer).shield_surf$setShouldRenderShieldHandle(ShieldSurfConfig.projectedShieldsRenderWithHandle());
        builtinModelItemRenderer.render(stack, ModelTransformationMode.NONE, matrixStack, vertexConsumerProvider, light, overlay);
        ((RenderHandleSometimesAccess) builtinModelItemRenderer).shield_surf$setShouldRenderShieldHandle(true);
        matrixStack.pop();
        super.render(projectedShield, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public Identifier getTexture(ProjectedShieldEntity projectedShield) {
        return TEXTURE;
    }
}
