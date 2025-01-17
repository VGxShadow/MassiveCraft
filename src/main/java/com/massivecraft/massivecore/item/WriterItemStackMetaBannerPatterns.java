package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterItemStackMetaBannerPatterns extends WriterAbstractItemStackMetaField<BannerMeta, List<DataBannerPattern>, List<Pattern>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaBannerPatterns i = new WriterItemStackMetaBannerPatterns();
	public static WriterItemStackMetaBannerPatterns get() { return i; }
	public WriterItemStackMetaBannerPatterns()
	{
		super(BannerMeta.class);
		this.setMaterial(Material.WHITE_BANNER);
		this.setConverterTo(ConverterToBannerPatterns.get());
		this.setConverterFrom(ConverterFromBannerPatterns.get());
		this.addDependencyClasses(
			WriterBannerPattern.class
		);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public List<DataBannerPattern> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getBannerPatterns();
	}
	
	@Override
	public void setA(@NotNull DataItemStack ca, List<DataBannerPattern> fa, ItemStack d)
	{
		ca.setBannerPatterns(fa);
	}
	
	@Override
	public List<Pattern> getB(@NotNull BannerMeta cb, ItemStack d)
	{
		return cb.getPatterns();
	}
	
	@Override
	public void setB(@NotNull BannerMeta cb, List<Pattern> fb, ItemStack d)
	{
		cb.setPatterns(fb);
	}
	
}
