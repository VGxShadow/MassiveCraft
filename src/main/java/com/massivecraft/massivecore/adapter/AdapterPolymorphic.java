package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class AdapterPolymorphic<T> implements JsonDeserializer<T>, JsonSerializer<T>
{
	public static final String TYPE = "type";
	public static final String VALUE = "value";
	
	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context)
	{
		if (src == null)
		{
			return JsonNull.INSTANCE;
		}
		
		JsonObject ret = new JsonObject();
		
		String type = src.getClass().getCanonicalName();
		ret.addProperty(TYPE, type);
		
		JsonElement value = context.serialize(src); 
		ret.add(VALUE, value);
		
		return ret;
	}

	@Override
	public T deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		if (!json.isJsonObject())
		{
			throw new JsonParseException("A polymorph must be an object.");
		}
		
		JsonObject jsonObject = json.getAsJsonObject();
		
		if (!jsonObject.has(TYPE))
		{
			throw new JsonParseException("A polymorph must be have a \""+TYPE+"\" field.");
		}
		
		if (!jsonObject.has(VALUE))
		{
			throw new JsonParseException("A polymorph must be have a \"+VALUE+\" field.");
		}
		
		String type = jsonObject.get(TYPE).getAsString();
		
		Class<?> typeClass;
		try
		{
			typeClass = Class.forName(type);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			throw new JsonParseException(e.getMessage());
		}
		return context.deserialize(jsonObject.get(VALUE), typeClass);
	}
	
}
