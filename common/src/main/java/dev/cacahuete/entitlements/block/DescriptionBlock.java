package dev.cacahuete.entitlements.block;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public interface DescriptionBlock {
    MutableComponent getDescription();
    List<BlockInteraction> getInteractions();
}
