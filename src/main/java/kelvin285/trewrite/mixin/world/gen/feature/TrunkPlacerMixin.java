package kelvin285.trewrite.mixin.world.gen.feature;

import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {

    @Shadow @Mutable protected int baseHeight;

    @Shadow @Mutable protected int firstRandomHeight;

    @Shadow @Mutable
    protected int secondRandomHeight;

    @Inject(at=@At("RETURN"),method="<init>")
    public void StraightTrunkPlacer(int i, int j, int k, CallbackInfo info) {
        if ((Object)this instanceof StraightTrunkPlacer) {
            baseHeight = i;
            firstRandomHeight = j + 2;
            secondRandomHeight = k + 2;
        }
    }
}
