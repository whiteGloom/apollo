package dev.emiller.mc.lazyplacing.mixin.client;

import dev.emiller.mc.lazyplacing.LazyPlacingClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void lazyPlacing$render(DrawContext context, float tickDelta, CallbackInfo ci) {
        float val = LazyPlacingClient.placingValue;

        if (val != 0 && val != 1) {
            float width = context.getScaledWindowWidth();
            float height = context.getScaledWindowHeight();
            int xCenter = (int) width / 2;
            int yCenter = (int) height / 2;

            int xWidthTotal = 40;
            int xStart = xCenter - xWidthTotal / 2;
            int yStart = (int) (yCenter * 1.35);

            int spacer = 2;
            int xWidthCurrent = (int) ((xWidthTotal - spacer) * val);
            int yHeight = 5;

            // Outer half transparent black box
            context.fill(xStart, yStart, xStart + xWidthTotal, yStart + yHeight, 0x7A000000);

            // Inner white progressbar
            context.fill(
                    xStart + spacer,
                    yStart + spacer,
                    xStart + xWidthCurrent,
                    yStart + yHeight - spacer,
                    0xFFFFFFFF
            );
        }
    }
}