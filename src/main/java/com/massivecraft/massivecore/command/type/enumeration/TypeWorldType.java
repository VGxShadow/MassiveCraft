package com.massivecraft.massivecore.command.type.enumeration;

import com.massivecraft.massivecore.collections.MassiveSet;
import org.bukkit.WorldType;

import java.util.Set;

public class TypeWorldType extends TypeEnum<WorldType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeWorldType i = new TypeWorldType();
	public static TypeWorldType get() { return i; }
	public TypeWorldType()
	{
		super(WorldType.class);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Set<String> getNamesInner(WorldType value)
	{
		Set<String> ret = new MassiveSet<>(super.getNamesInner(value));
		
		if (value == WorldType.NORMAL)
		{
			ret.add("normal");
			ret.add("default");
		}
		
		return ret;
	}

}
