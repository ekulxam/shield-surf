package survivalblock.shield_surf.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import survivalblock.shield_surf.common.entity.ShieldboardEntity;
import survivalblock.shield_surf.mixin.vanilla.shieldsurf.client.ItemRendererAccessor;

@Environment(value= EnvType.CLIENT)
public class ShieldboardEntityRenderer extends EntityRenderer<ShieldboardEntity> {

    public static final Identifier TEXTURE = /*? >=1.21.1 {*/ /*Identifier.ofVanilla *//*?} else {*/ new Identifier /*?}*/("textures/entity/shield_base_nopattern.png");

    public ShieldboardEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ShieldboardEntity shieldboardEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        ItemStack stack = shieldboardEntity.getItemStack();
        int overlay = OverlayTexture.DEFAULT_UV;
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180F - yaw));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        matrixStack.translate(0f, 0f, -0.135f);
        BuiltinModelItemRenderer builtinModelItemRenderer = ((ItemRendererAccessor) MinecraftClient.getInstance().getItemRenderer()).shield_surf$getBuiltinModelItemRenderer();
        ((RenderHandleSometimesAccess) builtinModelItemRenderer).shield_surf$setShouldRenderShieldHandle(false);
        builtinModelItemRenderer.render(stack, ModelTransformationMode.NONE, matrixStack, vertexConsumerProvider, light, overlay);
        ((RenderHandleSometimesAccess) builtinModelItemRenderer).shield_surf$setShouldRenderShieldHandle(true);
        matrixStack.pop();
        super.render(shieldboardEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }

    @Override
    public Identifier getTexture(ShieldboardEntity shieldboardEntity) {
        return TEXTURE;
    }
}
