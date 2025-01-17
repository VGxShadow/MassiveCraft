package com.massivecraft.massivecore.item;

import org.bukkit.enchantments.Enchantment;

public class ConverterFromEnchant extends Converter<Enchantment, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromEnchant i = new ConverterFromEnchant();
	public static ConverterFromEnchant get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String convert(Enchantment x)
	{
		if (x == null) return null;
		return x.getKey().getKey();
	}

}
