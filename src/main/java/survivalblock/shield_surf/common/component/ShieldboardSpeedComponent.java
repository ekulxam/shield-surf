package survivalblock.shield_surf.common.component;

import net.minecraft.registry.RegistryWrapper;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import survivalblock.shield_surf.common.entity.ShieldboardEntity;
import survivalblock.shield_surf.common.init.ShieldSurfEntityComponents;

public class ShieldboardSpeedComponent implements AutoSyncedComponent {

    private final ShieldboardEntity obj;
    private double currentBaseSpeed;

    public ShieldboardSpeedComponent(ShieldboardEntity obj) {
        this.obj = obj;
        this.currentBaseSpeed = 0;
    }

    @Override
    public void readFromNbt(NbtCompound tag /*? >=1.21.1 {*/ /*, RegistryWrapper.WrapperLookup wrapperLookup *//*?}*/) {
        this.currentBaseSpeed = tag.getDouble("CurrentBaseSpeed");
    }

    @Override
    public void writeToNbt(NbtCompound tag /*? >=1.21.1 {*/ /*, RegistryWrapper.WrapperLookup wrapperLookup *//*?}*/) {
        tag.putDouble("CurrentBaseSpeed", this.currentBaseSpeed);
    }

    public double getCurrentBaseSpeed() {
        return this.currentBaseSpeed;
    }

    public double getMaxBaseSpeed() {
        return ShieldboardEntity.MAX_SPEED + (this.obj.getEnchantmentLevel(50) - 1) * 0.05;
    }

    public void setCurrentBaseSpeed(double currentBaseSpeed) {
        double maxSpeed = this.getMaxBaseSpeed();
        this.currentBaseSpeed = MathHelper.clamp(currentBaseSpeed, -maxSpeed, maxSpeed);
        ShieldSurfEntityComponents.SHIELDBOARD_SPEED.sync(this.obj);
    }
}
