package com.massivecraft.massivecore.nms;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NmsItemStackCreateFallback extends NmsItemStackCreate
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackCreateFallback i = new NmsItemStackCreateFallback();
	public static NmsItemStackCreateFallback get () { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //

	@Override
	public ItemStack create()
	{
		return new ItemStack(Material.STONE);
	}
	
}
