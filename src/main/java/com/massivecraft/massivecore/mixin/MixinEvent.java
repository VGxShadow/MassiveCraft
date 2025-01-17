package com.massivecraft.massivecore.mixin;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;

public class MixinEvent extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinEvent d = new MixinEvent();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinEvent i = d;
	@Contract(pure = true)
	public static MixinEvent get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public <E extends Event & Serializable> void distribute(E event)
	{
		this.distribute(event, false);
	}
	
	public <E extends Event & Serializable> void distribute(E event, boolean includeSelf)
	{
		this.distributeOthers(event);
		if (includeSelf) this.distributeSelf(event);
	}
	
	protected <E extends Event & Serializable> void distributeSelf(E event)
	{
		Bukkit.getPluginManager().callEvent(event);
	}
	
	protected <E extends Event & Serializable> void distributeOthers(E event)
	{
		// NOTE: This is where the event would be sent to all other servers in the network.
	}

}
