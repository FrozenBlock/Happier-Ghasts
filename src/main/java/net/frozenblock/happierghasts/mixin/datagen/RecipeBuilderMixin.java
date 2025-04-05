/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
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

package net.frozenblock.happierghasts.mixin.datagen;

import net.frozenblock.happierghasts.HGConstants;
import net.frozenblock.happierghasts.datagen.recipe.HGRecipeProvider;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RecipeBuilder.class)
public interface RecipeBuilderMixin {

	@ModifyVariable(
		method = "save(Lnet/minecraft/data/recipes/RecipeOutput;Ljava/lang/String;)V",
		at = @At("HEAD"),
		argsOnly = true,
		ordinal = 0
	)
	default String trailierTales$save(String original) {
		if (HGRecipeProvider.GENERATING_HG_RECIPES) {
			ResourceLocation originalLocation = ResourceLocation.tryParse(original);
			ResourceLocation trailierLocation = HGConstants.id(originalLocation.getPath());
			return trailierLocation.toString();
		}
		return original;
	}

}
