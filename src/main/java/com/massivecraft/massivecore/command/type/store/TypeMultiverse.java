package com.massivecraft.massivecore.command.type.store;

import com.massivecraft.massivecore.MassiveCorePerm;
import com.massivecraft.massivecore.Multiverse;
import com.massivecraft.massivecore.MultiverseColl;
import org.bukkit.command.CommandSender;

public class TypeMultiverse extends TypeEntity<Multiverse>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeMultiverse i = new TypeMultiverse();
	public static TypeMultiverse get() { return i; }
	public TypeMultiverse()
	{
		super(MultiverseColl.get());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean canList(CommandSender sender)
	{
		return MassiveCorePerm.USYS_MULTIVERSE_LIST.has(sender, false);
	}

}
