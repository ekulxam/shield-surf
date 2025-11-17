package survivalblock.shield_surf.mixin.bnuuyhelp;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Inject(method = "drawBackground", at = @At("RETURN"))
    private void logDrawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        System.out.println("[DEBUG] entered InventoryScreen draw method");
    }
}
