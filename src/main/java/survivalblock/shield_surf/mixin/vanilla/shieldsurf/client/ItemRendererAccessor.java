package survivalblock.shield_surf.mixin.vanilla.shieldsurf.client;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {

    @Accessor("builtinModelItemRenderer")
    BuiltinModelItemRenderer shield_surf$getBuiltinModelItemRenderer();
}
