package net.frozenblock.happierghasts.client.renderer.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class HappyGhastRenderState extends LivingEntityRenderState {
	public ItemStack harness = ItemStack.EMPTY;
	public boolean isBeingRidden;
}
