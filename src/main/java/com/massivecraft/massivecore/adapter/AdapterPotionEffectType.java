package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Type;

public class AdapterPotionEffectType implements JsonDeserializer<PotionEffectType>, JsonSerializer<PotionEffectType> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static final AdapterPotionEffectType i = new AdapterPotionEffectType();
    @Contract(pure = true)
    public static AdapterPotionEffectType get() { return i; }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public JsonElement serialize(PotionEffectType src, Type typeOfSrc, JsonSerializationContext context)
    {
        if (src == null) return JsonNull.INSTANCE;
        return new JsonPrimitive(src.getName());
    }

    @Override
    public PotionEffectType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json == null) return null;
        if (json.equals(JsonNull.INSTANCE)) return null;
        return PotionEffectType.getByName(json.getAsString());
    }
}
