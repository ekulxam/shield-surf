package survivalblock.shield_surf.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.shield_surf.common.init.ShieldSurfEntityTypes;

import java.util.concurrent.CompletableFuture;

public class ShieldSurfEnUsLangGenerator extends FabricLanguageProvider {

    public ShieldSurfEnUsLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput /*? >=1.21.1 {*/ /*, registryLookup *//*?}*/);
    }

    @Override
    public void generateTranslations( /*? >=1.21.1 {*/ /*RegistryWrapper.WrapperLookup wrapperLookup, *//*?}*/TranslationBuilder translationBuilder) {
        // entity
        translationBuilder.add(ShieldSurfEntityTypes.SHIELDBOARD, "Shieldboard");
        translationBuilder.add(ShieldSurfEntityTypes.PROJECTED_SHIELD, "Projected Shield");

        // damage
        translationBuilder.add("death.attack.shield_surf.shieldboard_collision", "%1$s was run over by %2$s");
        translationBuilder.add("death.attack.shield_surf.shieldboard_collision.player", "%1$s was run over by %2$s");
        translationBuilder.add("death.attack.shield_surf.shieldboard_collision.item", "%1$s was run over by %2$s using %3$s");

        translationBuilder.add("death.attack.shield_surf.shield_impact", "%1$s was flattened by %2$s");
        translationBuilder.add("death.attack.shield_surf.shield_impact.player", "%1$s was flattened by %2$s");
        translationBuilder.add("death.attack.shield_surf.shield_impact.item", "%1$s was flattened by %2$s using %3$s");

        // enchantments
        translationBuilder.add("enchantment.shield_surf.shield_surf", "Shield Surf");
        translationBuilder.add("enchantment.shield_surf.shield_surf.desc", "If not sneaking, allows the user to ride the shield like a surfboard.");
        translationBuilder.add("enchantment.shield_surf.aegis", "Aegis");
        translationBuilder.add("enchantment.shield_surf.aegis.desc", "Shields will automatically block for you, regardless of where they are in your inventory. Shields will be put on cooldown upon auto-blocking.");
        translationBuilder.add("enchantment.shield_surf.rapid", "Rapid");
        translationBuilder.add("enchantment.shield_surf.rapid.desc", "Using the shield will not impede movement");
        translationBuilder.add("enchantment.shield_surf.expulsion", "Expulsion");
        translationBuilder.add("enchantment.shield_surf.expulsion.desc", "Using the shield while sneaking will cast out shields in a circle around the user. Damage scales with the greatest damage taken by the user since last cast.");
        translationBuilder.add("enchantment.shield_surf.orbit", "Orbit");
        translationBuilder.add("enchantment.shield_surf.orbit.desc", "Using the shield will summon a shield satellite that will orbit around the player. The shield satellite will block any entity-based damage only once before breaking.");
        translationBuilder.add("enchantment.shield_surf.rebound", "Rebound");
        translationBuilder.add("enchantment.shield_surf.rebound.desc", "Blocked Projectiles will reverse direction greatly upon hitting the shield.");

        // sounds
        translationBuilder.add("subtitles.shield_surf.entity.projected_shield.hit", "Projected Shield hits");

        // gamerules
        translationBuilder.add("gamerule.shieldSurfExpulsionMultiplier", "Shield Surf - Expulsion Projectile Multiplier");
        translationBuilder.add("gamerule.shieldSurfReboundShieldsHaveProjectileImmunity", "Shield Surf- Rebound Shields Are Immune to Projectiles");

        // config
        translationBuilder.add("shield_surf.config.title", "Shield Surf Config");
        translationBuilder.add("shield_surf.config.client", "Client");
        translationBuilder.add("shield_surf.config.option.boolean.projectedShieldsRenderOutwards", "Projected Shields Render Outwards");
        translationBuilder.add("shield_surf.config.option.boolean.projectedShieldsRenderOutwards.desc", "Projected Shields render facing outwards");
        translationBuilder.add("shield_surf.config.option.boolean.projectedShieldsRenderWithHandle", "Projected Shields Render With Handle");
        translationBuilder.add("shield_surf.config.option.boolean.projectedShieldsRenderWithHandle.desc", "Projected Shields render with their handles");
        translationBuilder.add("shield_surf.config.option.boolean.orbitingShieldsFlattenWhileSwimming", "Orbiting Shields Render Sideways Sometimes");
        translationBuilder.add("shield_surf.config.option.boolean.orbitingShieldsFlattenWhileSwimming.desc", "Orbiting Shields render sideways when the player's height is at most one");
        translationBuilder.add("shield_surf.config.option.boolean.orbitingShieldsRotateClockwise", "Orbiting Shields Rotate Clockwise");
        translationBuilder.add("shield_surf.config.option.boolean.orbitingShieldsRotateClockwise.desc", "Orbiting Shields rotate clockwise around their origin");
        // command
        translationBuilder.add("commands.shieldsurfconfig.noyacl", "Unable to generate Shield Surf config screen. Do you have YACL installed?");
        translationBuilder.add("commands.shieldsurfconfig.fail", "Unable to open Shield Surf config screen");
    }
}
