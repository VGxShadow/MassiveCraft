package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterItemStackMetaRecipes extends WriterAbstractItemStackMetaField<KnowledgeBookMeta, List<String>, List<NamespacedKey>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaRecipes i = new WriterItemStackMetaRecipes();
	public static WriterItemStackMetaRecipes get() { return i; }
	public WriterItemStackMetaRecipes()
	{
		super(KnowledgeBookMeta.class);
		this.setMaterial(Material.KNOWLEDGE_BOOK);
		this.setConverterTo(ConverterToNamespacedKeys.get());
		this.setConverterFrom(ConverterFromNamespacedKeys.get());
	}

	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public List<String> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getRecipes();
	}
	
	@Override
	public void setA(@NotNull DataItemStack ca, List<String> fa, ItemStack d)
	{
		ca.setRecipes(fa);
	}
	
	@Override
	public List<NamespacedKey> getB(@NotNull KnowledgeBookMeta cb, ItemStack d)
	{
		return cb.getRecipes();
	}
	
	@Override
	public void setB(@NotNull KnowledgeBookMeta cb, List<NamespacedKey> fb, ItemStack d)
	{
		cb.setRecipes(fb);
	}
	
}
