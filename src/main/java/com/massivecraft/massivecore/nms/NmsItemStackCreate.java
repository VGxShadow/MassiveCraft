package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NmsItemStackCreate extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static final NmsItemStackCreate d = new NmsItemStackCreate().setAlternatives(
		NmsItemStackCreate120R3.class,
		NmsItemStackCreateFallback.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackCreate i = d;
	public static NmsItemStackCreate get() { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	public ItemStack create()
	{
		throw notImplemented();
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public @NotNull Class<?> getClassCraftItemStack()
	{
		throw notImplemented();
	}
	
	public @Nullable Class<?> getClassCraftItemStackCatch()
	{
		try
		{
			return getClassCraftItemStack();
		}
		catch (Throwable t)
		{
			return null;
		}
	}
	
}
