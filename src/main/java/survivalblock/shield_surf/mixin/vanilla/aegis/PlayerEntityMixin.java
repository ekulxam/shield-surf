package survivalblock.shield_surf.mixin.vanilla.aegis;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import survivalblock.shield_surf.common.util.ShieldSurfUtil;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapMethod(method = "damageShield")
    private void setActiveItemStackToAegisShield(float amount, Operation<Void> original){
        ItemStack previousActiveStack = this.activeItemStack;
        if (!ShieldSurfUtil.isAShield(this.activeItemStack)) {
            this.activeItemStack = ShieldSurfUtil.getFirstAegisStack(this, true);
        }
        original.call(amount);
        this.activeItemStack = previousActiveStack;
    }
}
