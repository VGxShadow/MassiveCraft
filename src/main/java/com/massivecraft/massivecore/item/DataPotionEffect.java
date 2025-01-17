package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.command.editor.annotation.EditorEditable;
import com.massivecraft.massivecore.command.editor.annotation.EditorMethods;
import com.massivecraft.massivecore.command.editor.annotation.EditorVisible;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.massivecraft.massivecore.item.DataItemStack.get;
import static com.massivecraft.massivecore.item.DataItemStack.set;

@SuppressWarnings("FieldMayBeFinal")
@EditorMethods(true)
public class DataPotionEffect implements Comparable<DataPotionEffect>
{
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //
	
	public static final transient Integer DEFAULT_ID = null;
	public static final transient PotionEffectType DEFAULT_TYPE = null;
	public static final transient Integer DEFAULT_DURATION = 20 * 3 * 60;
	public static final transient Integer DEFAULT_AMPLIFIER = 0;
	public static final transient Boolean DEFAULT_AMBIENT = false;
	public static final transient Boolean DEFAULT_PARTICLES = true;
	
	// -------------------------------------------- //
	// FIELDS: VERSION
	// -------------------------------------------- //
	
	@EditorEditable(false)
	@EditorVisible(false)
	private int version = 1;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private PotionEffectType id = null;
	public PotionEffectType getId() { return get(this.id, DEFAULT_TYPE); }
	public DataPotionEffect setId(PotionEffectType id) { this.id = set(id, DEFAULT_TYPE); return this; }
	
	private Integer duration = null;
	public int getDuration() { return get(this.duration, DEFAULT_DURATION); }
	public DataPotionEffect setDuration(int duration) { this.duration = set(duration, DEFAULT_DURATION); return this; }
	
	private Integer amplifier = null;
	public int getAmplifier() { return get(this.amplifier, DEFAULT_AMPLIFIER); }
	public DataPotionEffect setAmplifier(int amplifier) { this.amplifier = set(amplifier, DEFAULT_AMPLIFIER); return this; }
	
	private Boolean ambient = null; 
	public boolean isAmbient() { return get(this.ambient, DEFAULT_AMBIENT); }
	public DataPotionEffect setAmbient(boolean ambient) { this.ambient = set(ambient, DEFAULT_AMBIENT); return this; }
	
	// SINCE: 1.8
	private Boolean particles = null;
	public boolean isParticles() { return get(this.particles, DEFAULT_PARTICLES); }
	public DataPotionEffect setParticles(boolean particles) { this.particles = set(particles, DEFAULT_PARTICLES); return this; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public DataPotionEffect()
	{
		
	}
	
	public DataPotionEffect(PotionEffect potionEffect)
	{
		this.write(potionEffect, false);
	}
	
	// -------------------------------------------- //
	// WRITE
	// -------------------------------------------- //
	
	public void write(PotionEffect potionEffect, boolean a2b)
	{
		WriterPotionEffect.get().write(this, potionEffect, a2b);
	}
	
	// -------------------------------------------- //
	// TO BUKKIT
	// -------------------------------------------- //
	
	public PotionEffect toBukkit()
	{
		// Create
		PotionEffect ret = WriterPotionEffect.get().createOB();
		
		// Fill
		this.write(ret, true);
		
		// Return
		return ret;
	}
	
	// -------------------------------------------- //
	// COMPARE & EQUALS & HASHCODE 
	// -------------------------------------------- //
	
	@Override
	public int compareTo(@NotNull DataPotionEffect that)
	{
		return ComparatorSmart.get().compare(
			this.getId(), that.getId(),
			this.getDuration(), that.getDuration(),
			this.getAmplifier(), that.getAmplifier(),
			this.isAmbient(), that.isAmbient(),
			this.isParticles(), that.isParticles()
		);
	}
	
	// TODO: Use compare instead to avoid bugs?
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object object)
	{
		if ( ! (object instanceof DataPotionEffect that)) return false;
		
		return MUtil.equals(
			this.getId(), that.getId(),
			this.getDuration(), that.getDuration(),
			this.getAmplifier(), that.getAmplifier(),
			this.isAmbient(), that.isAmbient(),
			this.isParticles(), that.isParticles()
		);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(
			this.getId(),
			this.getDuration(),
			this.getAmplifier(),
			this.isAmbient(),
			this.isParticles()
		);
	}
	
}
