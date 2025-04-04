/*
 * Copyright 2025 FrozenBlock
 * This file is part of Happier Ghasts.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.happierghasts.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.happierghasts.entity.HappyGhast;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Animal.class)
public class AnimalMixin {

	@ModifyExpressionValue(
		method = "mobInteract",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/Animal;getSpeedUpSecondsWhenFeeding(I)I"
		)
	)
	public int happierghasts$fasterSnowballGrowth(int original) {
		if (Animal.class.cast(this) instanceof HappyGhast) original *= 2;
		return original;
	}
}
