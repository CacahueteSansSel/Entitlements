package dev.cacahuete.entitlements.block;

import net.minecraft.network.chat.Component;

public class BlockInteraction {
    Component playerInputAction;
    Component intendedResult;

    public BlockInteraction(Component playerInputAction, Component intendedResult) {
        this.playerInputAction = playerInputAction;
        this.intendedResult = intendedResult;
    }

    public Component getPlayerInputAction() {
        return playerInputAction;
    }

    public Component getIntendedResult() {
        return intendedResult;
    }
}
