package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MixinModification extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinModification d = new MixinModification();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinModification i = d;
	@Contract(pure = true)
	public static MixinModification get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public void syncModification(@NotNull Entity<?> entity)
	{
		this.syncModification(entity.getColl(), entity.getId());
	}
	
	public void syncModification(Coll<?> coll, String id)
	{
		// Nothing to do here
	}

}
