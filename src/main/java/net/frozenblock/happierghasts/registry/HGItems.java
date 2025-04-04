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

package net.frozenblock.happierghasts.registry;

import net.frozenblock.happierghasts.HGConstants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

public class HGItems {

	public static final Item BLACK_HARNESS = register("black_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.BLACK))
	);
	public static final Item BLUE_HARNESS = register("blue_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.BLUE))
	);
	public static final Item BROWN_HARNESS = register("brown_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.BROWN))
	);
	public static final Item CYAN_HARNESS = register("cyan_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.CYAN))
	);
	public static final Item GRAY_HARNESS = register("gray_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.GRAY))
	);
	public static final Item GREEN_HARNESS = register("green_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.GREEN))
	);
	public static final Item LIGHT_BLUE_HARNESS = register("light_blue_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.LIGHT_BLUE))
	);
	public static final Item LIGHT_GRAY_HARNESS = register("light_gray_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.LIGHT_GRAY))
	);
	public static final Item LIME_HARNESS = register("lime_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.LIME))
	);
	public static final Item MAGENTA_HARNESS = register("magenta_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.MAGENTA))
	);
	public static final Item ORANGE_HARNESS = register("orange_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.ORANGE))
	);
	public static final Item PINK_HARNESS = register("pink_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.PINK))
	);
	public static final Item PURPLE_HARNESS = register("purple_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.PURPLE))
	);
	public static final Item RED_HARNESS = register("red_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.RED	))
	);
	public static final Item WHITE_HARNESS = register("white_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.WHITE))
	);
	public static final Item YELLOW_HARNESS = register("yellow_harness",
		Item::new,
		new Item.Properties().stacksTo(1).component(DataComponents.EQUIPPABLE, happyGhastHarness(DyeColor.YELLOW))
	);

	public static @NotNull Equippable happyGhastHarness(DyeColor dyeColor) {
		return Equippable.builder(EquipmentSlot.SADDLE)
			.setEquipSound(SoundEvents.HORSE_SADDLE)
			.setAsset(HGEquipmentAssets.HARNESSES.get(dyeColor))
			.setAllowedEntities(HGEntityTypes.HAPPY_GHAST)
			.setEquipOnInteract(true)
			.build();
	}

	public static void init() {
	}

	private static @NotNull <T extends Item> T register(String name, @NotNull Function<Item.Properties, Item> function, Item.@NotNull Properties properties) {
		return (T) Items.registerItem(ResourceKey.create(Registries.ITEM, HGConstants.id(name)), function, properties);
	}

}
