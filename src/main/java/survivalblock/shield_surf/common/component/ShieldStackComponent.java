package survivalblock.shield_surf.common.component;

import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import survivalblock.shield_surf.common.ShieldSurf;
import survivalblock.shield_surf.common.init.ShieldSurfEntityComponents;

public class ShieldStackComponent implements AutoSyncedComponent {

    private final Entity obj;
    private ItemStack shieldStack;

    public ShieldStackComponent(Entity entity) {
        this.obj = entity;
        this.shieldStack = ItemStack.EMPTY;
    }

    @Override
    public void readFromNbt(NbtCompound nbt /*? >=1.21.1 {*/ /*, RegistryWrapper.WrapperLookup wrapperLookup *//*?}*/) {
        //? if >=1.21.1 {
        /*if (nbt.contains("Shield")) {
            ItemStack.OPTIONAL_CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), nbt.get("Shield"))
                    .resultOrPartial(error -> ShieldSurf.LOGGER.error("Tried to load an invalid itemstack: '{}'", error))
                    .ifPresent(stack -> this.shieldStack = stack);
        }
        *///?} else {
         if (nbt.contains("Shield", NbtElement.COMPOUND_TYPE)) {
            this.shieldStack = ItemStack.fromNbt(nbt.getCompound("Shield"));
        }
         //?}
    }

    @Override
    public void writeToNbt(NbtCompound nbt /*? >=1.21.1 {*/ /*, RegistryWrapper.WrapperLookup wrapperLookup *//*?}*/) {
        //? if >=1.21.1 {
        /*nbt.put("Shield", ItemStack.OPTIONAL_CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.shieldStack)
                .getOrThrow());
        *///?} else {
        nbt.put("Shield", shieldStack.writeNbt(new NbtCompound()));
         //?}
    }

    public ItemStack getShieldStack() {
        return this.shieldStack;
    }

    public void setShieldStack(ItemStack shieldStack) {
        this.shieldStack = shieldStack;
        ShieldSurfEntityComponents.SHIELD_STACK.sync(this.obj);
    }
}
