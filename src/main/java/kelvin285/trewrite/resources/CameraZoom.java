package kelvin285.trewrite.resources;

import kelvin285.trewrite.options.CustomOptions;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.math.MathHelper;

public class CameraZoom {
    public static double ZOOM;
    public static double ZOOM_LERP;
    public static void UpdateZoom(GameOptions options) {
        if (CustomOptions.ZOOM_IN.wasPressed()) {
            ZOOM--;
        }
        else if (CustomOptions.ZOOM_OUT.wasPressed()) {
            ZOOM++;
        }

        ZOOM = MathHelper.clamp(ZOOM, -4, 4);
        ZOOM_LERP = MathHelper.lerp(0.25f, ZOOM_LERP, ZOOM);
    }
}
