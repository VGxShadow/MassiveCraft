package com.massivecraft.massivecore.command.type.primitive;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.nms.NmsItemStackMeta;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TypeStringParsedJSON extends TypeString
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeStringParsedJSON i = new TypeStringParsedJSON();
	public static TypeStringParsedJSON get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getName()
	{
		return "colored text JSON";
	}
	
	@Override
	public ChatColor getVisualColor(String value, CommandSender sender)
	{
		return ChatColor.RESET;
	}
	
	@Override
	public String getIdInner(String value)
	{
		return NmsItemStackMeta.get().fromJSONToLegacy(value);
	}
	
	@Override
	public String read(String arg, CommandSender sender) throws MassiveException
	{
		return NmsItemStackMeta.get().fromLegacyToJSON(Txt.parse(super.read(arg, sender)));
	}

}
