package survivalblock.shield_surf.mixin.bnuuyhelp;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true)
@Mixin(RabbitEntity.class)
public class RabbitEntityMixin {

    @Shadow private int jumpTicks;

    @WrapOperation(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "floatValue=8.0")))
    private void addFleeGoal(GoalSelector instance, int priority, Goal goal, Operation<Void> original) {
        //ItemGroupEvents.modifyEntriesEvent();
        if (!(goal instanceof FleeEntityGoal<?> fleeEntityGoal)) {
            original.call(instance, priority, goal);
            return;
        }
        TargetPredicate targetPredicate = ((FleeEntityGoalAccessor) fleeEntityGoal).modid$getWithinRangePredicate();
        targetPredicate.setPredicate(((TargetPredicateAccessor) targetPredicate).modid$getPredicate().and(living -> {
            return !(living instanceof PlayerEntity player) || !player.getCommandTags().contains("bunny_mark");
        }));
        original.call(instance, priority, fleeEntityGoal);
    }
}
