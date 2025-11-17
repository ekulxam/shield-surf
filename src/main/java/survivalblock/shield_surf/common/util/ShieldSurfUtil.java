package survivalblock.shield_surf.common.util;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import survivalblock.shield_surf.access.ExpulsionDamageAccess;
import survivalblock.shield_surf.common.ShieldSurf;
import survivalblock.shield_surf.common.compat.SurfingFabricShieldLib;
import survivalblock.shield_surf.common.component.ShieldSatellitesComponent;
import survivalblock.shield_surf.common.enchantment.ShieldSurfingEnchantment;
import survivalblock.shield_surf.common.entity.ProjectedShieldEntity;
import survivalblock.shield_surf.common.entity.ShieldboardEntity;
import survivalblock.shield_surf.common.init.ShieldSurfEnchantments;
import survivalblock.shield_surf.common.init.ShieldSurfEntityComponents;
import survivalblock.shield_surf.common.init.ShieldSurfGameRules;

public class ShieldSurfUtil {

    public static ItemStack getFirstAegisStack(LivingEntity living, boolean shouldCooldown){
        for (ItemStack handStack : living.getHandItems()){
            if (!isAShield(handStack)) continue;
            if (!(EnchantmentHelper.getLevel(ShieldSurfEnchantments.AEGIS, handStack) > 0)) continue;
            if (living instanceof PlayerEntity player && player.getItemCooldownManager().isCoolingDown(handStack.getItem())) continue;
            return handStack;
        }
        if (living instanceof PlayerEntity player){
            int size = player.getInventory().size();
            for (short slot = 0; slot < size; slot++){
                ItemStack stackInSlot = player.getInventory().getStack(slot);
                if (!isAShield(stackInSlot)) continue;
                if (!(EnchantmentHelper.getLevel(ShieldSurfEnchantments.AEGIS, stackInSlot) > 0)) continue;
                Item item = stackInSlot.getItem();
                if (player.getItemCooldownManager().isCoolingDown(item)) continue;
                if (shouldCooldown) {
                    player.getItemCooldownManager().set(item, 100);
                }
                return stackInSlot;
            }
        }
        return ItemStack.EMPTY;
    }
    public static boolean hasAegis(LivingEntity living){
        return getFirstAegisStack(living, false) != ItemStack.EMPTY;
    }

    public static boolean isAShield(ItemStack stack){
        return stack.isIn(ConventionalItemTags.SHIELDS) || stack.isOf(Items.SHIELD) || (ShieldSurf.hasFabricShieldLib && SurfingFabricShieldLib.isAFabricShield(stack));
    }

    public static boolean cancelShieldEnchantments(Enchantment original, Enchantment other){
        return !(other instanceof ShieldSurfingEnchantment) || other == original;
    }

    public static boolean rideTheLightning(World world, PlayerEntity user, Item item, ItemStack stack) {
        if (world.isClient()) {
            return false;
        }
        if (user.isSneaking()) {
            return false;
        }
        if (EnchantmentHelper.getLevel(ShieldSurfEnchantments.SHIELD_SURF, stack) <= 0) {
            return false;
        }
        user.incrementStat(Stats.USED.getOrCreateStat(item));
        ShieldboardEntity shieldboard = new ShieldboardEntity(world, user, stack);
        world.spawnEntity(shieldboard);
        user.refreshPositionAfterTeleport(user.getPos());
        user.getInventory().removeOne(stack);
        user.startRiding(shieldboard, true);
        //user.startRiding(shieldboard, true);
        return true;
    }

    public static void shieldcast(World world, PlayerEntity user, Hand hand, Item item, ItemStack stack){
        int expulsionLevel = EnchantmentHelper.getLevel(ShieldSurfEnchantments.EXPULSION, stack);
        if (world.isClient() || expulsionLevel <= 0 || !user.isSneaking()) {
            return;
        }
        float damage = ((ExpulsionDamageAccess) user).shield_surf$getExpulsionAttackDamage();
        if (damage * 2 <= 100){
            damage *= 2;
        } else if (damage < 100) {
            damage = 100;
        }
        try {
            ProjectedShieldEntity projectedShield;
            int multiplier = world.getGameRules().getInt(ShieldSurfGameRules.EXPULSION_MULTIPLIER);
            for (float i = 0; i < 360; i += 360 / (float) (expulsionLevel * multiplier)) {
                projectedShield = new ProjectedShieldEntity(world, user, stack);
                projectedShield.setYaw(i + user.getYaw());
                world.spawnEntity(projectedShield);
                projectedShield.setVelocity(user, projectedShield.getPitch(), projectedShield.getYaw(), 0.0f, 0.3f, 0.0f);
                Vec3d velocity = projectedShield.getVelocity();
                projectedShield.setVelocity(velocity.x, Math.max(-0.1, velocity.y), velocity.z);
                projectedShield.setDamage(Math.max(damage, 4));
            }
            damageAndIncrementStat(user, hand, item, stack, 2, 200);
        } catch (Exception e) {
            ShieldSurf.LOGGER.error("An exception occurred while trying to summon a Projected Shield", e);
        }
    }

    public static void solarSystem(World world, PlayerEntity user, Hand hand, Item item, ItemStack stack) {
        if (world.isClient() || EnchantmentHelper.getLevel(ShieldSurfEnchantments.ORBIT, stack) <= 0) {
            return;
        }
        ShieldSatellitesComponent satellitesComponent = ShieldSurfEntityComponents.SHIELD_SATELLITES.get(user);
        if (satellitesComponent.getSatellites() + 1 > ShieldSatellitesComponent.maxSatellites) {
            return;
        }
        satellitesComponent.addSatellite(stack);
        damageAndIncrementStat(user, hand, item, stack, 3, 80);
    }

    private static void damageAndIncrementStat(PlayerEntity user, Hand hand, Item item, ItemStack stack, int damage, int cooldown) {
        if (!user.isCreative()) {
            stack.damage(damage, user, (p) -> p.sendToolBreakStatus(hand));
            user.getItemCooldownManager().set(item, cooldown);
            user.stopUsingItem();
        }
        user.incrementStat(Stats.USED.getOrCreateStat(item));
    }
}
