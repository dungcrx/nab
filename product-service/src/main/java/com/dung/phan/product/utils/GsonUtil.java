package com.dung.phan.product.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class GsonUtil {
    private static Gson singletonGson;

    public static Gson singletonGson() {
        if (Objects.isNull(singletonGson)) {
            singletonGson = new GsonBuilder().create();
        }

        return singletonGson;
    }


    public static JsonArray arrayJson(String request){
        return singletonGson().fromJson(request,JsonArray.class);
    }

    public static JsonObject toJsonObject(String request){
        return singletonGson().fromJson(request,JsonObject.class);
    }



    /**
     * Get as string safely
     */
    public static String getAsString(JsonObject sourceJson, String fieldName) {
        JsonElement jsonElement = sourceJson.get(fieldName);
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return StringUtils.EMPTY;
        }

        return jsonElement.getAsString();
    }
}
