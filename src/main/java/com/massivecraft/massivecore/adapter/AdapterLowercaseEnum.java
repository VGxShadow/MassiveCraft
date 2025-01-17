package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public record AdapterLowercaseEnum<T extends Enum<T>>(Class<T> clazz) implements JsonDeserializer<T>, JsonSerializer<T>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public Class<T> getClazz() {return this.clazz;}
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static <T extends Enum<T>> @NotNull AdapterLowercaseEnum<T> get(@NotNull Class<T> clazz)
	{
		return new AdapterLowercaseEnum<>(clazz);
	}
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("null -> fail")
	public AdapterLowercaseEnum
	{
		if (clazz == null) throw new IllegalArgumentException("clazz is null");
		if (!clazz.isEnum()) throw new IllegalArgumentException("clazz is not enum");
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context)
	{
		// Null
		if (src == null) return JsonNull.INSTANCE;
		
		String comparable = this.getComparable(src);
		return new JsonPrimitive(comparable);
	}
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		// Null
		if (json == null) return null;
		if (json.equals(JsonNull.INSTANCE)) return null;
		
		return this.getEnumValueFrom(json);
	}
	
	// -------------------------------------------- //
	// GET ENUM VALUE FROM
	// -------------------------------------------- //
	
	public T getEnumValueFrom(JsonElement json)
	{
		String string = this.getComparable(json);
		return this.getEnumValueFrom(string);
	}
	
	public T getEnumValueFrom(String string)
	{
		string = this.getComparable(string);
		for (T value : this.getEnumValues())
		{
			String comparable = this.getComparable(value);
			if (comparable.equals(string)) return value;
		}
		return null;
	}
	
	// -------------------------------------------- //
	// GET ENUM VALUES
	// -------------------------------------------- //
	
	public T[] getEnumValues()
	{
		Class<T> clazz = this.getClazz();
		return clazz.getEnumConstants();
	}
	
	// -------------------------------------------- //
	// GET COMPARABLE
	// -------------------------------------------- //
	
	public String getComparable(Enum<?> value)
	{
		if (value == null) return null;
		return this.getComparable(value.name());
	}
	
	public String getComparable(JsonElement json)
	{
		if (json == null) return null;
		return this.getComparable(json.getAsString());
	}
	
	public String getComparable(String string)
	{
		if (string == null) return null;
		return string.toLowerCase();
	}
	
}
