package com.massivecraft.massivecore.adapter;

import com.google.common.collect.ImmutableMap;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * This is a GSON serializer/deserializer for the Bukkit ItemStack. Why not use
 * the built in Bukkit serializer/deserializer? I would have loved to do that :)
 * but sadly that one is YAML centric and cannot be used with json in a good
 * way. This serializer requires manual updating to work but produces clean
 * json. See the file itemstackformat.txt for more info.
 */
public class AdapterItemStack implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //

	private static final AdapterItemStack i = new AdapterItemStack();
	public static AdapterItemStack get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context)
	{
		DataItemStack dataItemStack = new DataItemStack(src);
		return context.serialize(dataItemStack);
	}

	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		convertTypeIdToString(json);
		DataItemStack dataItemStack = context.deserialize(json, DataItemStack.class);
		return dataItemStack.toBukkit();
	}
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
	public static void convertTypeIdToString(JsonElement jsonElement)
	{
		// It is always an object
		JsonObject json = jsonElement.getAsJsonObject();
		
		// Get the id
		JsonElement id = json.get("id");
		if (id == null || id.isJsonNull()) return;
		
		// The id is either a number or string, always a JsonPrimitive
		JsonPrimitive primitive = id.getAsJsonPrimitive();
		
		// Only convert if number
		if (!primitive.isNumber()) return;
		
		int typeId = primitive.getAsInt();
		String name = id2name.get(typeId);
		if (name == null) throw new RuntimeException(String.valueOf(typeId));
		JsonElement newValue = new JsonPrimitive(name);
		json.add("id", newValue);
	}
	
	private static final Map<Integer, String> id2name;
	
	static
	{
		Map<Integer, String> map = new MassiveMap<>();
		
		map.put(0, "AIR");
		map.put(1, "STONE");
		map.put(2, "GRASS");
		map.put(3, "DIRT");
		map.put(4, "COBBLESTONE");
		map.put(5, "WOOD");
		map.put(6, "SAPLING");
		map.put(7, "BEDROCK");
		map.put(8, "WATER");
		map.put(9, "STATIONARY_WATER");
		map.put(10, "LAVA");
		map.put(11, "STATIONARY_LAVA");
		map.put(12, "SAND");
		map.put(13, "GRAVEL");
		map.put(14, "GOLD_ORE");
		map.put(15, "IRON_ORE");
		map.put(16, "COAL_ORE");
		map.put(17, "LOG");
		map.put(18, "LEAVES");
		map.put(19, "SPONGE");
		map.put(20, "GLASS");
		map.put(21, "LAPIS_ORE");
		map.put(22, "LAPIS_BLOCK");
		map.put(23, "DISPENSER");
		map.put(24, "SANDSTONE");
		map.put(25, "NOTE_BLOCK");
		map.put(26, "BED_BLOCK");
		map.put(27, "POWERED_RAIL");
		map.put(28, "DETECTOR_RAIL");
		map.put(29, "PISTON_STICKY_BASE");
		map.put(30, "WEB");
		map.put(31, "LONG_GRASS");
		map.put(32, "DEAD_BUSH");
		map.put(33, "PISTON_BASE");
		map.put(34, "PISTON_EXTENSION");
		map.put(35, "WOOL");
		map.put(36, "PISTON_MOVING_PIECE");
		map.put(37, "YELLOW_FLOWER");
		map.put(38, "RED_ROSE");
		map.put(39, "BROWN_MUSHROOM");
		map.put(40, "RED_MUSHROOM");
		map.put(41, "GOLD_BLOCK");
		map.put(42, "IRON_BLOCK");
		map.put(43, "DOUBLE_STEP");
		map.put(44, "STEP");
		map.put(45, "BRICK");
		map.put(46, "TNT");
		map.put(47, "BOOKSHELF");
		map.put(48, "MOSSY_COBBLESTONE");
		map.put(49, "OBSIDIAN");
		map.put(50, "TORCH");
		map.put(51, "FIRE");
		map.put(52, "MOB_SPAWNER");
		map.put(53, "WOOD_STAIRS");
		map.put(54, "CHEST");
		map.put(55, "REDSTONE_WIRE");
		map.put(56, "DIAMOND_ORE");
		map.put(57, "DIAMOND_BLOCK");
		map.put(58, "WORKBENCH");
		map.put(59, "CROPS");
		map.put(60, "SOIL");
		map.put(61, "FURNACE");
		map.put(62, "BURNING_FURNACE");
		map.put(63, "SIGN_POST");
		map.put(64, "WOODEN_DOOR");
		map.put(65, "LADDER");
		map.put(66, "RAILS");
		map.put(67, "COBBLESTONE_STAIRS");
		map.put(68, "WALL_SIGN");
		map.put(69, "LEVER");
		map.put(70, "STONE_PLATE");
		map.put(71, "IRON_DOOR_BLOCK");
		map.put(72, "WOOD_PLATE");
		map.put(73, "REDSTONE_ORE");
		map.put(74, "GLOWING_REDSTONE_ORE");
		map.put(75, "REDSTONE_TORCH_OFF");
		map.put(76, "REDSTONE_TORCH_ON");
		map.put(77, "STONE_BUTTON");
		map.put(78, "SNOW");
		map.put(79, "ICE");
		map.put(80, "SNOW_BLOCK");
		map.put(81, "CACTUS");
		map.put(82, "CLAY");
		map.put(83, "SUGAR_CANE_BLOCK");
		map.put(84, "JUKEBOX");
		map.put(85, "FENCE");
		map.put(86, "PUMPKIN");
		map.put(87, "NETHERRACK");
		map.put(88, "SOUL_SAND");
		map.put(89, "GLOWSTONE");
		map.put(90, "PORTAL");
		map.put(91, "JACK_O_LANTERN");
		map.put(92, "CAKE_BLOCK");
		map.put(93, "DIODE_BLOCK_OFF");
		map.put(94, "DIODE_BLOCK_ON");
		map.put(95, "STAINED_GLASS");
		map.put(96, "TRAP_DOOR");
		map.put(97, "MONSTER_EGGS");
		map.put(98, "SMOOTH_BRICK");
		map.put(99, "HUGE_MUSHROOM_1");
		map.put(100, "HUGE_MUSHROOM_2");
		map.put(101, "IRON_FENCE");
		map.put(102, "THIN_GLASS");
		map.put(103, "MELON_BLOCK");
		map.put(104, "PUMPKIN_STEM");
		map.put(105, "MELON_STEM");
		map.put(106, "VINE");
		map.put(107, "FENCE_GATE");
		map.put(108, "BRICK_STAIRS");
		map.put(109, "SMOOTH_STAIRS");
		map.put(110, "MYCEL");
		map.put(111, "WATER_LILY");
		map.put(112, "NETHER_BRICK");
		map.put(113, "NETHER_FENCE");
		map.put(114, "NETHER_BRICK_STAIRS");
		map.put(115, "NETHER_WARTS");
		map.put(116, "ENCHANTMENT_TABLE");
		map.put(117, "BREWING_STAND");
		map.put(118, "CAULDRON");
		map.put(119, "ENDER_PORTAL");
		map.put(120, "ENDER_PORTAL_FRAME");
		map.put(121, "ENDER_STONE");
		map.put(122, "DRAGON_EGG");
		map.put(123, "REDSTONE_LAMP_OFF");
		map.put(124, "REDSTONE_LAMP_ON");
		map.put(125, "WOOD_DOUBLE_STEP");
		map.put(126, "WOOD_STEP");
		map.put(127, "COCOA");
		map.put(128, "SANDSTONE_STAIRS");
		map.put(129, "EMERALD_ORE");
		map.put(130, "ENDER_CHEST");
		map.put(131, "TRIPWIRE_HOOK");
		map.put(132, "TRIPWIRE");
		map.put(133, "EMERALD_BLOCK");
		map.put(134, "SPRUCE_WOOD_STAIRS");
		map.put(135, "BIRCH_WOOD_STAIRS");
		map.put(136, "JUNGLE_WOOD_STAIRS");
		map.put(137, "COMMAND");
		map.put(138, "BEACON");
		map.put(139, "COBBLE_WALL");
		map.put(140, "FLOWER_POT");
		map.put(141, "CARROT");
		map.put(142, "POTATO");
		map.put(143, "WOOD_BUTTON");
		map.put(144, "SKULL");
		map.put(145, "ANVIL");
		map.put(146, "TRAPPED_CHEST");
		map.put(147, "GOLD_PLATE");
		map.put(148, "IRON_PLATE");
		map.put(149, "REDSTONE_COMPARATOR_OFF");
		map.put(150, "REDSTONE_COMPARATOR_ON");
		map.put(151, "DAYLIGHT_DETECTOR");
		map.put(152, "REDSTONE_BLOCK");
		map.put(153, "QUARTZ_ORE");
		map.put(154, "HOPPER");
		map.put(155, "QUARTZ_BLOCK");
		map.put(156, "QUARTZ_STAIRS");
		map.put(157, "ACTIVATOR_RAIL");
		map.put(158, "DROPPER");
		map.put(159, "STAINED_CLAY");
		map.put(160, "STAINED_GLASS_PANE");
		map.put(161, "LEAVES_2");
		map.put(162, "LOG_2");
		map.put(163, "ACACIA_STAIRS");
		map.put(164, "DARK_OAK_STAIRS");
		map.put(165, "SLIME_BLOCK");
		map.put(166, "BARRIER");
		map.put(167, "IRON_TRAPDOOR");
		map.put(168, "PRISMARINE");
		map.put(169, "SEA_LANTERN");
		map.put(170, "HAY_BLOCK");
		map.put(171, "CARPET");
		map.put(172, "HARD_CLAY");
		map.put(173, "COAL_BLOCK");
		map.put(174, "PACKED_ICE");
		map.put(175, "DOUBLE_PLANT");
		map.put(176, "STANDING_BANNER");
		map.put(177, "WALL_BANNER");
		map.put(178, "DAYLIGHT_DETECTOR_INVERTED");
		map.put(179, "RED_SANDSTONE");
		map.put(180, "RED_SANDSTONE_STAIRS");
		map.put(181, "DOUBLE_STONE_SLAB2");
		map.put(182, "STONE_SLAB2");
		map.put(183, "SPRUCE_FENCE_GATE");
		map.put(184, "BIRCH_FENCE_GATE");
		map.put(185, "JUNGLE_FENCE_GATE");
		map.put(186, "DARK_OAK_FENCE_GATE");
		map.put(187, "ACACIA_FENCE_GATE");
		map.put(188, "SPRUCE_FENCE");
		map.put(189, "BIRCH_FENCE");
		map.put(190, "JUNGLE_FENCE");
		map.put(191, "DARK_OAK_FENCE");
		map.put(192, "ACACIA_FENCE");
		map.put(193, "SPRUCE_DOOR");
		map.put(194, "BIRCH_DOOR");
		map.put(195, "JUNGLE_DOOR");
		map.put(196, "ACACIA_DOOR");
		map.put(197, "DARK_OAK_DOOR");
		map.put(198, "END_ROD");
		map.put(199, "CHORUS_PLANT");
		map.put(200, "CHORUS_FLOWER");
		map.put(201, "PURPUR_BLOCK");
		map.put(202, "PURPUR_PILLAR");
		map.put(203, "PURPUR_STAIRS");
		map.put(204, "PURPUR_DOUBLE_SLAB");
		map.put(205, "PURPUR_SLAB");
		map.put(206, "END_BRICKS");
		map.put(207, "BEETROOT_BLOCK");
		map.put(208, "GRASS_PATH");
		map.put(209, "END_GATEWAY");
		map.put(210, "COMMAND_REPEATING");
		map.put(211, "COMMAND_CHAIN");
		map.put(212, "FROSTED_ICE");
		map.put(213, "MAGMA");
		map.put(214, "NETHER_WART_BLOCK");
		map.put(215, "RED_NETHER_BRICK");
		map.put(216, "BONE_BLOCK");
		map.put(217, "STRUCTURE_VOID");
		map.put(218, "OBSERVER");
		map.put(219, "WHITE_SHULKER_BOX");
		map.put(220, "ORANGE_SHULKER_BOX");
		map.put(221, "MAGENTA_SHULKER_BOX");
		map.put(222, "LIGHT_BLUE_SHULKER_BOX");
		map.put(223, "YELLOW_SHULKER_BOX");
		map.put(224, "LIME_SHULKER_BOX");
		map.put(225, "PINK_SHULKER_BOX");
		map.put(226, "GRAY_SHULKER_BOX");
		map.put(227, "SILVER_SHULKER_BOX");
		map.put(228, "CYAN_SHULKER_BOX");
		map.put(229, "PURPLE_SHULKER_BOX");
		map.put(230, "BLUE_SHULKER_BOX");
		map.put(231, "BROWN_SHULKER_BOX");
		map.put(232, "GREEN_SHULKER_BOX");
		map.put(233, "RED_SHULKER_BOX");
		map.put(234, "BLACK_SHULKER_BOX");
		map.put(235, "WHITE_GLAZED_TERRACOTTA");
		map.put(236, "ORANGE_GLAZED_TERRACOTTA");
		map.put(237, "MAGENTA_GLAZED_TERRACOTTA");
		map.put(238, "LIGHT_BLUE_GLAZED_TERRACOTTA");
		map.put(239, "YELLOW_GLAZED_TERRACOTTA");
		map.put(240, "LIME_GLAZED_TERRACOTTA");
		map.put(241, "PINK_GLAZED_TERRACOTTA");
		map.put(242, "GRAY_GLAZED_TERRACOTTA");
		map.put(243, "SILVER_GLAZED_TERRACOTTA");
		map.put(244, "CYAN_GLAZED_TERRACOTTA");
		map.put(245, "PURPLE_GLAZED_TERRACOTTA");
		map.put(246, "BLUE_GLAZED_TERRACOTTA");
		map.put(247, "BROWN_GLAZED_TERRACOTTA");
		map.put(248, "GREEN_GLAZED_TERRACOTTA");
		map.put(249, "RED_GLAZED_TERRACOTTA");
		map.put(250, "BLACK_GLAZED_TERRACOTTA");
		map.put(251, "CONCRETE");
		map.put(252, "CONCRETE_POWDER");
		map.put(255, "STRUCTURE_BLOCK");
		map.put(256, "IRON_SPADE");
		map.put(257, "IRON_PICKAXE");
		map.put(258, "IRON_AXE");
		map.put(259, "FLINT_AND_STEEL");
		map.put(260, "APPLE");
		map.put(261, "BOW");
		map.put(262, "ARROW");
		map.put(263, "COAL");
		map.put(264, "DIAMOND");
		map.put(265, "IRON_INGOT");
		map.put(266, "GOLD_INGOT");
		map.put(267, "IRON_SWORD");
		map.put(268, "WOOD_SWORD");
		map.put(269, "WOOD_SPADE");
		map.put(270, "WOOD_PICKAXE");
		map.put(271, "WOOD_AXE");
		map.put(272, "STONE_SWORD");
		map.put(273, "STONE_SPADE");
		map.put(274, "STONE_PICKAXE");
		map.put(275, "STONE_AXE");
		map.put(276, "DIAMOND_SWORD");
		map.put(277, "DIAMOND_SPADE");
		map.put(278, "DIAMOND_PICKAXE");
		map.put(279, "DIAMOND_AXE");
		map.put(280, "STICK");
		map.put(281, "BOWL");
		map.put(282, "MUSHROOM_SOUP");
		map.put(283, "GOLD_SWORD");
		map.put(284, "GOLD_SPADE");
		map.put(285, "GOLD_PICKAXE");
		map.put(286, "GOLD_AXE");
		map.put(287, "STRING");
		map.put(288, "FEATHER");
		map.put(289, "SULPHUR");
		map.put(290, "WOOD_HOE");
		map.put(291, "STONE_HOE");
		map.put(292, "IRON_HOE");
		map.put(293, "DIAMOND_HOE");
		map.put(294, "GOLD_HOE");
		map.put(295, "SEEDS");
		map.put(296, "WHEAT");
		map.put(297, "BREAD");
		map.put(298, "LEATHER_HELMET");
		map.put(299, "LEATHER_CHESTPLATE");
		map.put(300, "LEATHER_LEGGINGS");
		map.put(301, "LEATHER_BOOTS");
		map.put(302, "CHAINMAIL_HELMET");
		map.put(303, "CHAINMAIL_CHESTPLATE");
		map.put(304, "CHAINMAIL_LEGGINGS");
		map.put(305, "CHAINMAIL_BOOTS");
		map.put(306, "IRON_HELMET");
		map.put(307, "IRON_CHESTPLATE");
		map.put(308, "IRON_LEGGINGS");
		map.put(309, "IRON_BOOTS");
		map.put(310, "DIAMOND_HELMET");
		map.put(311, "DIAMOND_CHESTPLATE");
		map.put(312, "DIAMOND_LEGGINGS");
		map.put(313, "DIAMOND_BOOTS");
		map.put(314, "GOLD_HELMET");
		map.put(315, "GOLD_CHESTPLATE");
		map.put(316, "GOLD_LEGGINGS");
		map.put(317, "GOLD_BOOTS");
		map.put(318, "FLINT");
		map.put(319, "PORK");
		map.put(320, "GRILLED_PORK");
		map.put(321, "PAINTING");
		map.put(322, "GOLDEN_APPLE");
		map.put(323, "SIGN");
		map.put(324, "WOOD_DOOR");
		map.put(325, "BUCKET");
		map.put(326, "WATER_BUCKET");
		map.put(327, "LAVA_BUCKET");
		map.put(328, "MINECART");
		map.put(329, "SADDLE");
		map.put(330, "IRON_DOOR");
		map.put(331, "REDSTONE");
		map.put(332, "SNOW_BALL");
		map.put(333, "BOAT");
		map.put(334, "LEATHER");
		map.put(335, "MILK_BUCKET");
		map.put(336, "CLAY_BRICK");
		map.put(337, "CLAY_BALL");
		map.put(338, "SUGAR_CANE");
		map.put(339, "PAPER");
		map.put(340, "BOOK");
		map.put(341, "SLIME_BALL");
		map.put(342, "STORAGE_MINECART");
		map.put(343, "POWERED_MINECART");
		map.put(344, "EGG");
		map.put(345, "COMPASS");
		map.put(346, "FISHING_ROD");
		map.put(347, "WATCH");
		map.put(348, "GLOWSTONE_DUST");
		map.put(349, "RAW_FISH");
		map.put(350, "COOKED_FISH");
		map.put(351, "INK_SACK");
		map.put(352, "BONE");
		map.put(353, "SUGAR");
		map.put(354, "CAKE");
		map.put(355, "BED");
		map.put(356, "DIODE");
		map.put(357, "COOKIE");
		map.put(358, "MAP");
		map.put(359, "SHEARS");
		map.put(360, "MELON");
		map.put(361, "PUMPKIN_SEEDS");
		map.put(362, "MELON_SEEDS");
		map.put(363, "RAW_BEEF");
		map.put(364, "COOKED_BEEF");
		map.put(365, "RAW_CHICKEN");
		map.put(366, "COOKED_CHICKEN");
		map.put(367, "ROTTEN_FLESH");
		map.put(368, "ENDER_PEARL");
		map.put(369, "BLAZE_ROD");
		map.put(370, "GHAST_TEAR");
		map.put(371, "GOLD_NUGGET");
		map.put(372, "NETHER_STALK");
		map.put(373, "POTION");
		map.put(374, "GLASS_BOTTLE");
		map.put(375, "SPIDER_EYE");
		map.put(376, "FERMENTED_SPIDER_EYE");
		map.put(377, "BLAZE_POWDER");
		map.put(378, "MAGMA_CREAM");
		map.put(379, "BREWING_STAND_ITEM");
		map.put(380, "CAULDRON_ITEM");
		map.put(381, "EYE_OF_ENDER");
		map.put(382, "SPECKLED_MELON");
		map.put(383, "MONSTER_EGG");
		map.put(384, "EXP_BOTTLE");
		map.put(385, "FIREBALL");
		map.put(386, "BOOK_AND_QUILL");
		map.put(387, "WRITTEN_BOOK");
		map.put(388, "EMERALD");
		map.put(389, "ITEM_FRAME");
		map.put(390, "FLOWER_POT_ITEM");
		map.put(391, "CARROT_ITEM");
		map.put(392, "POTATO_ITEM");
		map.put(393, "BAKED_POTATO");
		map.put(394, "POISONOUS_POTATO");
		map.put(395, "EMPTY_MAP");
		map.put(396, "GOLDEN_CARROT");
		map.put(397, "SKULL_ITEM");
		map.put(398, "CARROT_STICK");
		map.put(399, "NETHER_STAR");
		map.put(400, "PUMPKIN_PIE");
		map.put(401, "FIREWORK");
		map.put(402, "FIREWORK_CHARGE");
		map.put(403, "ENCHANTED_BOOK");
		map.put(404, "REDSTONE_COMPARATOR");
		map.put(405, "NETHER_BRICK_ITEM");
		map.put(406, "QUARTZ");
		map.put(407, "EXPLOSIVE_MINECART");
		map.put(408, "HOPPER_MINECART");
		map.put(409, "PRISMARINE_SHARD");
		map.put(410, "PRISMARINE_CRYSTALS");
		map.put(411, "RABBIT");
		map.put(412, "COOKED_RABBIT");
		map.put(413, "RABBIT_STEW");
		map.put(414, "RABBIT_FOOT");
		map.put(415, "RABBIT_HIDE");
		map.put(416, "ARMOR_STAND");
		map.put(417, "IRON_BARDING");
		map.put(418, "GOLD_BARDING");
		map.put(419, "DIAMOND_BARDING");
		map.put(420, "LEASH");
		map.put(421, "NAME_TAG");
		map.put(422, "COMMAND_MINECART");
		map.put(423, "MUTTON");
		map.put(424, "COOKED_MUTTON");
		map.put(425, "BANNER");
		map.put(426, "END_CRYSTAL");
		map.put(427, "SPRUCE_DOOR_ITEM");
		map.put(428, "BIRCH_DOOR_ITEM");
		map.put(429, "JUNGLE_DOOR_ITEM");
		map.put(430, "ACACIA_DOOR_ITEM");
		map.put(431, "DARK_OAK_DOOR_ITEM");
		map.put(432, "CHORUS_FRUIT");
		map.put(433, "CHORUS_FRUIT_POPPED");
		map.put(434, "BEETROOT");
		map.put(435, "BEETROOT_SEEDS");
		map.put(436, "BEETROOT_SOUP");
		map.put(437, "DRAGONS_BREATH");
		map.put(438, "SPLASH_POTION");
		map.put(439, "SPECTRAL_ARROW");
		map.put(440, "TIPPED_ARROW");
		map.put(441, "LINGERING_POTION");
		map.put(442, "SHIELD");
		map.put(443, "ELYTRA");
		map.put(444, "BOAT_SPRUCE");
		map.put(445, "BOAT_BIRCH");
		map.put(446, "BOAT_JUNGLE");
		map.put(447, "BOAT_ACACIA");
		map.put(448, "BOAT_DARK_OAK");
		map.put(449, "TOTEM");
		map.put(450, "SHULKER_SHELL");
		map.put(452, "IRON_NUGGET");
		map.put(453, "KNOWLEDGE_BOOK");
		map.put(2256, "GOLD_RECORD");
		map.put(2257, "GREEN_RECORD");
		map.put(2258, "RECORD_3");
		map.put(2259, "RECORD_4");
		map.put(2260, "RECORD_5");
		map.put(2261, "RECORD_6");
		map.put(2262, "RECORD_7");
		map.put(2263, "RECORD_8");
		map.put(2264, "RECORD_9");
		map.put(2265, "RECORD_10");
		map.put(2266, "RECORD_11");
		map.put(2267, "RECORD_12");
	
		id2name = ImmutableMap.copyOf(map);
	}
	
}
