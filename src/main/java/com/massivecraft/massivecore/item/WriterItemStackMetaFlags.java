package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WriterItemStackMetaFlags extends WriterAbstractItemStackMetaField<ItemMeta, Set<String>, Set<ItemFlag>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaFlags i = new WriterItemStackMetaFlags();
	public static WriterItemStackMetaFlags get() { return i; }
	public WriterItemStackMetaFlags()
	{
		super(ItemMeta.class);
		this.setConverterTo(ConverterToItemFlags.get());
		this.setConverterFrom(ConverterFromItemFlags.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Set<String> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getFlags();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Set<String> fa, ItemStack d)
	{
		ca.setFlags(fa);
	}

	@Override
	public Set<ItemFlag> getB(@NotNull ItemMeta cb, ItemStack d)
	{
		return cb.getItemFlags();
	}

	@Override
	public void setB(@NotNull ItemMeta cb, Set<ItemFlag> fb, ItemStack d)
	{
		cb.addItemFlags(fb.toArray(new ItemFlag[0]));
	}
	
}
