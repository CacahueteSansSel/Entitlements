package dev.cacahuete.entitlements.block;

public class BlockInteraction {
    String playerInputAction;
    String intendedResult;

    public BlockInteraction(String playerInputAction, String intendedResult) {
        this.playerInputAction = playerInputAction;
        this.intendedResult = intendedResult;
    }

    public String getPlayerInputAction() {
        return playerInputAction;
    }

    public String getIntendedResult() {
        return intendedResult;
    }
}
