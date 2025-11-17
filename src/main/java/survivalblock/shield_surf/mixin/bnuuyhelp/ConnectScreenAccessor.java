package survivalblock.shield_surf.mixin.bnuuyhelp;

import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ConnectScreen.class)
public interface ConnectScreenAccessor {

    @Invoker("setStatus") void modid$invokeSetStatus(Text status);
}
