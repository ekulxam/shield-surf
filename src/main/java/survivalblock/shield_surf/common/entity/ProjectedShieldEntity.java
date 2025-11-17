package survivalblock.shield_surf.common.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import survivalblock.shield_surf.common.component.ShieldStackComponent;
import survivalblock.shield_surf.common.init.ShieldSurfDamageTypes;
import survivalblock.shield_surf.common.init.ShieldSurfEntityComponents;
import survivalblock.shield_surf.common.init.ShieldSurfEntityTypes;
import survivalblock.shield_surf.common.init.ShieldSurfSoundEvents;

import java.util.Objects;

public class ProjectedShieldEntity extends PersistentProjectileEntity {

    public ProjectedShieldEntity(EntityType<? extends ProjectedShieldEntity> entityType, World world) {
        super(entityType, world);
        this.getShieldStackComponent().setShieldStack(Items.SHIELD.getDefaultStack());
    }

    public ProjectedShieldEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ShieldSurfEntityTypes.PROJECTED_SHIELD, owner.getX(), Math.ceil(-1 + owner.getY() + (owner.getHeight()) / 2), owner.getZ(), world/*? >=1.21.1 {*/ /*, ItemStack.EMPTY, stack.copy() *//*?}*/);
        this.getShieldStackComponent().setShieldStack(stack.copy());
        this.setNoGravity(true);
        this.setOwner(owner);
    }

    private ShieldStackComponent getShieldStackComponent(){
        return ShieldSurfEntityComponents.SHIELD_STACK.get(this);
    }

    //? if >=1.21.1 {
    /*@Override
    public ItemStack getDefaultItemStack() {
        return Items.SHIELD.getDefaultStack();
    }

    @Override
    public ItemStack asItemStack() {
        return super.asItemStack();
    }
    *///?} else {
    @Override
    public ItemStack asItemStack() {
        return this.getShieldStackComponent().getShieldStack().copy();
    }
    //?}

    @Override
    public void tick() {
        /*? >=1.21.1 {*/ /*this.setStack(this.getShieldStackComponent().getShieldStack().copy()); *//*?}*/
        boolean ownerDoesNotExist = this.getOwner() == null || this.getOwner().isRemoved(); // same as !this.getOwner().isAlive()
        if ((this.age > 120 || ownerDoesNotExist || this.isInsideWall()) && !this.getWorld().isClient()) {
            this.kill();
        }
        super.tick();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        if (Objects.equals(entity, entity2)) {
            return;
        }
        double damage = this.getDamage();
        DamageSource damageSource = new DamageSource(ShieldSurfDamageTypes.get(ShieldSurfDamageTypes.SHIELD_IMPACT, this.getWorld()), this, entity2);
        //? if >=1.21 {
        /*ItemStack weapon = this.getWeaponStack();
        if (weapon != null) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                damage = EnchantmentHelper.getDamage(serverWorld, weapon, entity, damageSource, (float) damage);
            }
        }
        *///?}
        if (entity.damage(damageSource, (float) damage)) {
            if (entity instanceof LivingEntity livingEntity2) {
                //? if >=1.21.1 {
                /*if (this.getWorld() instanceof ServerWorld serverWorld) {
                    EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity2, damageSource, this.getWeaponStack());
                }
                *///?} else {
                if (entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity2);
                }
                //?}
                this.onHit(livingEntity2);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient()) this.discard();
    }

    @Override
    protected SoundEvent getHitSound() {
        return ShieldSurfSoundEvents.ENTITY_PROJECTED_SHIELD_HIT;
    }
}
