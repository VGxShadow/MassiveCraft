package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaUnbreakable extends WriterAbstractItemStackMetaField<ItemMeta, Boolean, Boolean>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaUnbreakable i = new WriterItemStackMetaUnbreakable();
	public static WriterItemStackMetaUnbreakable get() { return i; }
	public WriterItemStackMetaUnbreakable()
	{
		super(ItemMeta.class);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Boolean getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.isUnbreakable();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Boolean fa, ItemStack d)
	{
		ca.setUnbreakable(fa);
	}

	@Override
	public Boolean getB(@NotNull ItemMeta cb, ItemStack d)
	{
		try {
			return cb.isUnbreakable();
		} catch (UnsupportedOperationException ignored) {
			return false;
		}
	}

	@Override
	public void setB(@NotNull ItemMeta cb, Boolean fb, ItemStack d)
	{
		try {
			cb.setUnbreakable(fb);
		} catch (UnsupportedOperationException ignored) {
			// ignored
		}
	}
	
}
