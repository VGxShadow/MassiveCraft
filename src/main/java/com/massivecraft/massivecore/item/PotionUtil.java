package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivecore.xlib.guava.collect.BiMap;
import com.massivecraft.massivecore.xlib.guava.collect.ImmutableBiMap;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Contract;

public class PotionUtil
{
	@Contract("null -> null")
	public static PotionData toPotionData(String potionString)
	{
		if (potionString == null) return null;
		return POTION_ID_TO_DATA.get(potionString);
	}
	
	@Contract("null -> null")
	public static String toPotionString(PotionData potionData)
	{
		if (potionData == null) return null;
		return POTION_ID_TO_DATA.inverse().get(potionData);
	}
	
	private static final BiMap<String, PotionData> POTION_ID_TO_DATA = ImmutableBiMap.<String, PotionData>builder()
		// REGULAR
		.put("empty", new PotionData(PotionType.UNCRAFTABLE, false, false))
		.put("water", new PotionData(PotionType.WATER, false, false))
		.put("mundane", new PotionData(PotionType.MUNDANE, false, false))
		.put("thick", new PotionData(PotionType.THICK, false, false))
		.put("awkward", new PotionData(PotionType.AWKWARD, false, false))
		.put("night_vision", new PotionData(PotionType.NIGHT_VISION, false, false))
		.put("invisibility", new PotionData(PotionType.INVISIBILITY, false, false))
		.put("leaping", new PotionData(PotionType.JUMP, false, false))
		.put("fire_resistance", new PotionData(PotionType.FIRE_RESISTANCE, false, false))
		.put("swiftness", new PotionData(PotionType.SPEED, false, false))
		.put("slowness", new PotionData(PotionType.SLOWNESS, false, false))
		.put("water_breathing", new PotionData(PotionType.WATER_BREATHING, false, false))
		.put("healing", new PotionData(PotionType.INSTANT_HEAL, false, false))
		.put("harming", new PotionData(PotionType.INSTANT_DAMAGE, false, false))
		.put("poison", new PotionData(PotionType.POISON, false, false))
		.put("regeneration", new PotionData(PotionType.REGEN, false, false))
		.put("strength", new PotionData(PotionType.STRENGTH, false, false))
		.put("weakness", new PotionData(PotionType.WEAKNESS, false, false))
		.put("luck", new PotionData(PotionType.LUCK, false, false))
		.put("turtle_master", new PotionData(PotionType.TURTLE_MASTER, false, false))
		.put("slow_falling", new PotionData(PotionType.SLOW_FALLING, false, false))
		
		// UPGRADABLE
		.put("strong_leaping", new PotionData(PotionType.JUMP, false, true))
		.put("strong_swiftness", new PotionData(PotionType.SPEED, false, true))
		.put("strong_healing", new PotionData(PotionType.INSTANT_HEAL, false, true))
		.put("strong_harming", new PotionData(PotionType.INSTANT_DAMAGE, false, true))
		.put("strong_poison", new PotionData(PotionType.POISON, false, true))
		.put("strong_regeneration", new PotionData(PotionType.REGEN, false, true))
		.put("strong_strength", new PotionData(PotionType.STRENGTH, false, true))
		.put("strong_slowness", new PotionData(PotionType.SLOWNESS, false, true))
		.put("strong_turtle_master", new PotionData(PotionType.TURTLE_MASTER, false, true))
		
		// EXTENDABLE
		.put("long_night_vision", new PotionData(PotionType.NIGHT_VISION, true, false))
		.put("long_invisibility", new PotionData(PotionType.INVISIBILITY, true, false))
		.put("long_leaping", new PotionData(PotionType.JUMP, true, false))
		.put("long_fire_resistance", new PotionData(PotionType.FIRE_RESISTANCE, true, false))
		.put("long_swiftness", new PotionData(PotionType.SPEED, true, false))
		.put("long_slowness", new PotionData(PotionType.SLOWNESS, true, false))
		.put("long_water_breathing", new PotionData(PotionType.WATER_BREATHING, true, false))
		.put("long_poison", new PotionData(PotionType.POISON, true, false))
		.put("long_regeneration", new PotionData(PotionType.REGEN, true, false))
		.put("long_strength", new PotionData(PotionType.STRENGTH, true, false))
		.put("long_weakness", new PotionData(PotionType.WEAKNESS, true, false))
		.put("long_turtle_master", new PotionData(PotionType.TURTLE_MASTER, true, false))
		.put("long_slow_falling", new PotionData(PotionType.SLOW_FALLING, true, false))
	
	// BUILD
	.build();
	
	public static String friendlyPotionEffectName(PotionEffectType unfriendlyPotionEffectType)
	{
		if (PotionEffectType.FAST_DIGGING.equals(unfriendlyPotionEffectType)) return "Haste";
		else if (PotionEffectType.DAMAGE_RESISTANCE.equals(unfriendlyPotionEffectType)) return "Resistance";
		else if (PotionEffectType.CONFUSION.equals(unfriendlyPotionEffectType)) return "Nausea";
		else if (PotionEffectType.HARM.equals(unfriendlyPotionEffectType)) return "Instant Damage";
		else if (PotionEffectType.HEAL.equals(unfriendlyPotionEffectType)) return "Instant Health";
		else if (PotionEffectType.INCREASE_DAMAGE.equals(unfriendlyPotionEffectType)) return "Strength";
		else if (PotionEffectType.JUMP.equals(unfriendlyPotionEffectType)) return "Jump Boost";
		else if (PotionEffectType.SLOW.equals(unfriendlyPotionEffectType)) return "Slowness";
		else if (PotionEffectType.SLOW_DIGGING.equals(unfriendlyPotionEffectType)) return "Mining Fatigue";
		else if (PotionEffectType.UNLUCK.equals(unfriendlyPotionEffectType)) return "Bad Luck";
		
		return Txt.getNicedEnumString(unfriendlyPotionEffectType.getName(), " ");
	}
}