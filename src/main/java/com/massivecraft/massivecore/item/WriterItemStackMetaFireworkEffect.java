package com.massivecraft.massivecore.item;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaFireworkEffect extends WriterAbstractItemStackMetaField<FireworkEffectMeta, DataFireworkEffect, FireworkEffect>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaFireworkEffect i = new WriterItemStackMetaFireworkEffect();
	public static WriterItemStackMetaFireworkEffect get() { return i; }
	public WriterItemStackMetaFireworkEffect()
	{
		super(FireworkEffectMeta.class);
		this.setMaterial(Material.FIREWORK_STAR);
		this.setConverterTo(ConverterToFireworkEffect.get());
		this.setConverterFrom(ConverterFromFireworkEffect.get());
		this.addDependencyClasses(
			WriterFireworkEffect.class
		);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public DataFireworkEffect getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getFireworkEffect();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, DataFireworkEffect fa, ItemStack d)
	{
		ca.setFireworkEffect(fa);
	}

	@Override
	public FireworkEffect getB(@NotNull FireworkEffectMeta cb, ItemStack d)
	{
		return cb.getEffect();
	}

	@Override
	public void setB(@NotNull FireworkEffectMeta cb, FireworkEffect fb, ItemStack d)
	{
		cb.setEffect(fb);
	}
	
}
