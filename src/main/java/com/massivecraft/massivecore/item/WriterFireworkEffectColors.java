package com.massivecraft.massivecore.item;

import com.google.common.collect.ImmutableList;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterFireworkEffectColors extends WriterAbstractFireworkEffect<List<Integer>, ImmutableList<Color>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterFireworkEffectColors i = new WriterFireworkEffectColors();
	public static WriterFireworkEffectColors get() { return i; }
	public WriterFireworkEffectColors()
	{
		super("colors");
		this.setConverterTo(ConverterToColors.get());
		this.setConverterFrom(ConverterFromColors.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public List<Integer> getA(@NotNull DataFireworkEffect ca, Object d)
	{
		return ca.getColors();
	}
	
	@Override
	public void setA(@NotNull DataFireworkEffect ca, List<Integer> fa, Object d)
	{
		ca.setColors(fa);
	}
	
	@Override
	public ImmutableList<Color> getB(@NotNull FireworkEffect cb, Object d)
	{
		return (ImmutableList<Color>) cb.getColors();
	}
	
}
