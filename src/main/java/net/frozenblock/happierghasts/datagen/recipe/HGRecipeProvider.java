/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
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

package net.frozenblock.happierghasts.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.happierghasts.registry.HGBlocks;
import net.frozenblock.happierghasts.registry.HGItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class HGRecipeProvider extends FabricRecipeProvider {
	public static boolean GENERATING_HG_RECIPES = false;

	public HGRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Contract("_, _ -> new")
	@Override
	protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
		return new RecipeProvider(registries, exporter) {
			@Override
			public void buildRecipes() {
				GENERATING_HG_RECIPES = true;

				this.shaped(RecipeCategory.MISC, HGBlocks.DRIED_GHAST)
					.group("dried_ghast")
					.define('*', Ingredient.of(Items.GHAST_TEAR))
					.define('#', Ingredient.of(Items.BONE_BLOCK))
					.pattern("***")
					.pattern("*#*")
					.pattern("***")
					.unlockedBy(RecipeProvider.getHasName(Items.GHAST_TEAR), this.has(Items.GHAST_TEAR))
					.save(exporter);

				List<Item> dyes = List.of(
					Items.BLACK_DYE,
					Items.BLUE_DYE,
					Items.BROWN_DYE,
					Items.CYAN_DYE,
					Items.GRAY_DYE,
					Items.GREEN_DYE,
					Items.LIGHT_BLUE_DYE,
					Items.LIGHT_GRAY_DYE,
					Items.LIME_DYE,
					Items.MAGENTA_DYE,
					Items.ORANGE_DYE,
					Items.PINK_DYE,
					Items.PURPLE_DYE,
					Items.RED_DYE,
					Items.YELLOW_DYE,
					Items.WHITE_DYE
				);
				List<Item> harnesses = List.of(
					HGItems.BLACK_HARNESS,
					HGItems.BLUE_HARNESS,
					HGItems.BROWN_HARNESS,
					HGItems.CYAN_HARNESS,
					HGItems.GRAY_HARNESS,
					HGItems.GREEN_HARNESS,
					HGItems.LIGHT_BLUE_HARNESS,
					HGItems.LIGHT_GRAY_HARNESS,
					HGItems.LIME_HARNESS,
					HGItems.MAGENTA_HARNESS,
					HGItems.ORANGE_HARNESS,
					HGItems.PINK_HARNESS,
					HGItems.PURPLE_HARNESS,
					HGItems.RED_HARNESS,
					HGItems.YELLOW_HARNESS,
					HGItems.WHITE_HARNESS
				);

				this.colorWithDye(dyes, harnesses, null, "harness", RecipeCategory.TRANSPORTATION);

				this.harnessRecipe(exporter, HGItems.BLACK_HARNESS, Items.BLACK_WOOL);
				this.harnessRecipe(exporter, HGItems.BLUE_HARNESS, Items.BLUE_WOOL);
				this.harnessRecipe(exporter, HGItems.BROWN_HARNESS, Items.BROWN_WOOL);
				this.harnessRecipe(exporter, HGItems.CYAN_HARNESS, Items.CYAN_WOOL);
				this.harnessRecipe(exporter, HGItems.GRAY_HARNESS, Items.GRAY_WOOL);
				this.harnessRecipe(exporter, HGItems.GREEN_HARNESS, Items.GREEN_WOOL);
				this.harnessRecipe(exporter, HGItems.LIGHT_BLUE_HARNESS, Items.LIGHT_BLUE_WOOL);
				this.harnessRecipe(exporter, HGItems.LIGHT_GRAY_HARNESS, Items.LIGHT_GRAY_WOOL);
				this.harnessRecipe(exporter, HGItems.LIME_HARNESS, Items.LIME_WOOL);
				this.harnessRecipe(exporter, HGItems.MAGENTA_HARNESS, Items.MAGENTA_WOOL);
				this.harnessRecipe(exporter, HGItems.ORANGE_HARNESS, Items.ORANGE_WOOL);
				this.harnessRecipe(exporter, HGItems.PINK_HARNESS, Items.PINK_WOOL);
				this.harnessRecipe(exporter, HGItems.PURPLE_HARNESS, Items.PURPLE_WOOL);
				this.harnessRecipe(exporter, HGItems.RED_HARNESS, Items.RED_WOOL);
				this.harnessRecipe(exporter, HGItems.YELLOW_HARNESS, Items.YELLOW_WOOL);
				this.harnessRecipe(exporter, HGItems.WHITE_HARNESS, Items.WHITE_WOOL);

				GENERATING_HG_RECIPES = false;
			}

			private void harnessRecipe(RecipeOutput exporter, Item harness, Item wool) {
				this.shaped(RecipeCategory.TRANSPORTATION, harness)
					.group("harness")
					.define('L', Ingredient.of(Items.LEATHER))
					.define('#', Ingredient.of(Items.GLASS))
					.define('W', Ingredient.of(wool))
					.pattern("LLL")
					.pattern("#W#")
					.unlockedBy(RecipeProvider.getHasName(Items.LEATHER), this.has(Items.LEATHER))
					.save(exporter);
			}
		};
	}

	@Override
	@NotNull
	public String getName() {
		return "Wilder Wild Recipes";
	}
}
