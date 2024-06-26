package dev.cacahuete.entitlements.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class RegionTitleOverlay {
    static float alpha = 0f;
    static float timer = 0f;
    static String regionNameText;
    static State state = State.Hidden;
    static Component nowEnteringComponent;
    static float displayTimeSeconds;

    public static void render(GuiGraphics graphics, float partialTicks) {
        if (state == State.Hidden || regionNameText == null) return;

        Minecraft mc = Minecraft.getInstance();

        int rgbAlpha = (int)(alpha * 255f);
        int finalColor = 16777215 | (rgbAlpha << 24 & -16777216);

        float dt = 1f / mc.getFps();

        switch (state) {
            case Showing:
                if (alpha >= 1f) {
                    alpha = 1f;
                    state = State.Shown;
                }
                else alpha += dt;

                break;
            case Shown:
                timer += dt;

                if (timer > displayTimeSeconds) state = State.Hiding;

                break;
            case Hiding:
                if (alpha <= 0) {
                    alpha = 0f;
                    state = State.Hidden;
                    regionNameText = null;
                }
                else alpha -= dt;

                break;
        }

        if (alpha > 0.1f) {
            graphics.pose().pushPose();
            RenderSystem.enableBlend();
            graphics.drawCenteredString(mc.font, nowEnteringComponent.getString(), graphics.guiWidth() / 2, 45, finalColor);
            graphics.pose().scale(2f, 2f, 2f);
            graphics.drawCenteredString(mc.font, regionNameText, graphics.guiWidth() / 2 / 2, 30, finalColor);
            RenderSystem.disableBlend();
            graphics.pose().popPose();
        }
    }

    public static void show(String regionName, float displayTime) {
        if (Objects.equals(regionNameText, regionName)) return;

        regionNameText = regionName;
        displayTimeSeconds = displayTime;
        alpha = 0f;
        timer = 0f;
        nowEnteringComponent = Component.translatable("ui.entitlements.now_entering");

        state = State.Showing;
    }

    enum State {
        Hidden,
        Showing,
        Shown,
        Hiding
    }
}
