package survivalblock.shield_surf.common.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import survivalblock.shield_surf.common.ShieldSurf;

public class ShieldSurfSoundEvents {

    public static final SoundEvent ENTITY_PROJECTED_SHIELD_HIT = SoundEvent.of(ShieldSurf.id("entity.projected_shield.hit"));

    public static void init(){
        Registry.register(Registries.SOUND_EVENT, ENTITY_PROJECTED_SHIELD_HIT.getId(), ENTITY_PROJECTED_SHIELD_HIT);
    }
}
