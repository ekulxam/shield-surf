package survivalblock.shield_surf.mixin.bnuuyhelp;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FleeEntityGoal.class)
public interface FleeEntityGoalAccessor {

    @Accessor("withinRangePredicate")
    TargetPredicate modid$getWithinRangePredicate();
}
