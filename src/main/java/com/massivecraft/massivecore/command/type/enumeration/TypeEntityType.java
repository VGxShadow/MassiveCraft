package com.massivecraft.massivecore.command.type.enumeration;

import com.massivecraft.massivecore.collections.MassiveSet;
import org.bukkit.entity.EntityType;

import java.util.Set;

public class TypeEntityType extends TypeEnum<EntityType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeEntityType i = new TypeEntityType();
	public static TypeEntityType get() { return i; }
	public TypeEntityType()
	{
		super(EntityType.class);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Set<String> getNamesInner(EntityType value)
	{
		Set<String> ret = new MassiveSet<>(super.getNamesInner(value));
		
		if (value == EntityType.ZOMBIFIED_PIGLIN)
		{
			ret.add("pigman");
			ret.add("pigzombie");
			ret.add("manpig");
			ret.add("zombiepig");
			ret.add("zombiepiglin");
			ret.add("zombifiedpig");
			ret.add("zombifiedpiglin");
		}
		
		return ret;
	}

}
