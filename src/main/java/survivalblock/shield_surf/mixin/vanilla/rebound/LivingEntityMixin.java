package survivalblock.shield_surf.mixin.vanilla.rebound;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;
import survivalblock.shield_surf.common.init.ShieldSurfGameRules;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Unique
    private ItemStack activeShieldStack;

    @Shadow public abstract ItemStack getActiveItem();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void cacheActiveItem(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.getWorld().isClient()) {
            return;
        }
        this.activeShieldStack = this.getActiveItem().copy();
    }
    @WrapWithCondition(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V"))
    private boolean doNotDamageShield(LivingEntity instance, float amount, DamageSource source, float amount2) {
        return projectileImmunity(source);
    }

    @WrapWithCondition(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V"))
    private boolean doNotTakeShieldHit(LivingEntity instance, LivingEntity attacker, DamageSource source, float amount) {
        return projectileImmunity(source);
    }

    @Unique
    private boolean projectileImmunity(DamageSource source) {
        World world = this.getWorld();
        if (world.isClient() || !world.getGameRules().getBoolean(ShieldSurfGameRules.REBOUND_SHIELDS_HAVE_PROJECTILE_IMMUNITY)) {
            return true;
        }
        if (this.activeShieldStack == null || this.activeShieldStack.isEmpty()) {
            return true;
        }
        return !(source.getSource() instanceof ProjectileEntity) || EnchantmentHelper.getLevel(/*? >=1.21 {*/ /*ShieldSurfEnchantments.get(ShieldSurfEnchantments.REBOUND, world) *//*?} else {*/ ShieldSurfEnchantments.REBOUND /*?}*/, this.activeShieldStack) <= 0;
    }

    @ModifyReturnValue(method = "damage", slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;despawnCounter:I", opcode = Opcodes.PUTFIELD, ordinal = 0)), at = @At("RETURN"))
    private boolean projectileDeflection(boolean original, DamageSource source, float amount, @Local(ordinal = 0) boolean bl) {
        if (original) {
            return true;
        }
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return false;
        }
        if (this.activeShieldStack == null || this.activeShieldStack.isEmpty()) {
            return false;
        }
        if (source.getSource() instanceof ProjectileEntity projectileEntity && bl && EnchantmentHelper.getLevel(/*? >=1.21 {*/ /*ShieldSurfEnchantments.get(ShieldSurfEnchantments.REBOUND, serverWorld) *//*?} else {*/ ShieldSurfEnchantments.REBOUND /*?}*/, this.activeShieldStack) > 0) {
            Vec3d velocity = projectileEntity.getVelocity();
            Vec3d changedVelocity = new Vec3d(velocity.x, velocity.y, velocity.z);
            MinecraftServer server = serverWorld.getServer();
            server.send(((MinecraftServerAccessor) server).shield_surf$invokeCreateTask(() -> {
                if (projectileEntity.isRemoved()) {
                    return;
                }
                // I can't really control whether modded projectiles have a dealtDamage field that I can access and whether it even needs to be accessed and changed
                // if in the case that you do need to change this, use mixinsquared
                if (projectileEntity instanceof TridentEntity trident) {
                    ((TridentEntityAccessor) trident).shield_surf$setDealtDamage(false);
                }
                projectileEntity.velocityDirty = true;
                projectileEntity.setVelocity(changedVelocity.multiply(-1));
                projectileEntity.velocityModified = true;
            }));
        }
        return false;
    }
}
