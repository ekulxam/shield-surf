package survivalblock.shield_surf.common.component;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import survivalblock.shield_surf.common.ShieldSurf;
import survivalblock.shield_surf.common.init.ShieldSurfEntityComponents;

import java.util.ArrayList;
import java.util.List;

public class ShieldSatellitesComponent implements AutoSyncedComponent, CommonTickingComponent {

    public static final int MAX_SATELLITES = 16;
    /*? >=1.21.1 {*/ /*public static final Codec<List<ItemStack>> ITEMSTACK_LIST_CODEC = ItemStack.OPTIONAL_CODEC.listOf(); *//*?}*/

    private final PlayerEntity obj;
    private final List<ItemStack> itemStacks = new ArrayList<>();
    private int satellites = 0;
    private int rotation = 0;
    private boolean dirty = false;

    public ShieldSatellitesComponent(PlayerEntity obj) {
        this.obj = obj;
    }

    @Override
    public void readFromNbt(NbtCompound tag /*? >=1.21.1 {*/ /*, RegistryWrapper.WrapperLookup wrapperLookup *//*?}*/) {
        this.satellites = tag.getInt("Satellites");
        this.rotation = tag.getInt("OrbitRotation");
        //? if >=1.21.1 {
        /*if (tag.contains("Items")) {
            ITEMSTACK_LIST_CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), tag.get("Items"))
                    .resultOrPartial(error -> ShieldSurf.LOGGER.error("Tried to load an invalid list of itemstacks: '{}'", error))
                    .ifPresent(list -> {
                        this.itemStacks.clear();
                        this.itemStacks.addAll(list.stream().filter(stack -> !stack.isEmpty()).toList());
                    });
        }
        *///?} else {
        itemStacks.clear();
        NbtList nbtList = tag.getList("Items", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            itemStacks.add(ItemStack.fromNbt(nbtCompound));
        }
        tag.put("Items", nbtList);
         //?}
    }

    @Override
    public void writeToNbt(NbtCompound tag /*? >=1.21.1 {*/ /*, RegistryWrapper.WrapperLookup wrapperLookup *//*?}*/) {
        tag.putInt("Satellites", this.satellites);
        tag.putInt("OrbitRotation", this.rotation);
        //? if >=1.21.1 {
        /*tag.put("Items", ITEMSTACK_LIST_CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.itemStacks)
                .getOrThrow());
        *///?} else {
        NbtList nbtList = new NbtList();
        for (ItemStack itemStack : itemStacks) {
            if (itemStack.isEmpty()) continue;
            NbtCompound nbtCompound = new NbtCompound();
            itemStack.writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        tag.put("Items", nbtList);
         //?}
    }

    public int getRotation() {
        return this.rotation;
    }

    public int getSatellites() {
        return this.satellites;
    }

    public void addSatellite(ItemStack stack) {
        ++this.satellites;
        itemStacks.add(stack);
        this.markDirty();
    }

    public void removeSatellite() {
        --this.satellites;
        itemStacks.remove(0);
        this.obj.getWorld().sendEntityStatus(this.obj, (byte) 29);
        this.obj.getWorld().sendEntityStatus(this.obj, (byte) 30);
        this.markDirty();
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        this.markDirty();
    }

    @Override
    public void tick() {
        if (this.satellites > MAX_SATELLITES || this.getItemStacksSize() != this.satellites) {
            this.satellites = 0;
            this.itemStacks.clear();
            this.markDirty();
        } else {
            this.setRotation((this.getRotation() + 2) % 360);
        }

        if (this.dirty) {
            this.dirty = false;
            ShieldSurfEntityComponents.SHIELD_SATELLITES.sync(this.obj);
        }
    }

    public void markDirty(){
        this.dirty = true;
    }

    public int getItemStacksSize() {
        return this.itemStacks.size();
    }

    public ItemStack getStack(int i) {
        return this.itemStacks.get(i);
    }
}
