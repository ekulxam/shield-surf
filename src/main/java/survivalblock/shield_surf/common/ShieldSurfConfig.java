package survivalblock.shield_surf.common;

import eu.midnightdust.lib.config.MidnightConfig;

public class ShieldSurfConfig extends MidnightConfig {
    @Entry(min = 1, max = 1000)
    public static int expulsionMultiplier = 3; // lag
    @Entry(category = "client")
    public static boolean projectedShieldsRenderOutwards = true;
    @Entry(category = "client")
    public static boolean projectedShieldsRenderWithHandle = true;
    @Entry(category = "client")
    public static boolean orbitingShieldsFlattenWhileSwimming = true;
    @Entry(category = "client")
    public static boolean orbitingShieldsRotateClockwise = true;
}
