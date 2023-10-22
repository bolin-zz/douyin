package com.douyin.utils;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;


import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * 序列化参数
 */
public class TranslationSorting {

    // 序列化参数
    // 这一步看上去冗余，实际很重要。如果要自己实现，则必须保证这三点：
    // 1、保证JSON所有层级上Key的有序性
    // 2、保证JSON的所有数值不带多余的小数点
    // 3、保证转义逻辑与这段代码一致
    public static String marshal(Object o) {
        String raw = CustomGson.toJson(o);
        Map<?, ?> m = CustomGson.fromJson(raw, LinkedTreeMap.class); // 执行反序列化，把所有JSON对象转换成LinkedTreeMap
        return CustomGson.toJson(m); // 重新序列化，保证JSON所有层级上Key的有序性
    }

    private static final Gson CustomGson = new GsonBuilder().registerTypeAdapter(LinkedTreeMap.class, newMapSerializer()) // 定制LinkedTreeMap序列化，确保所有key按字典序排序
            .registerTypeAdapter(Integer.class, newNumberSerializer()) // 定制数值类型的序列化，确保整数输出不带小数点
            .registerTypeAdapter(Long.class, newNumberSerializer()) // 同上
            .registerTypeAdapter(Double.class, newNumberSerializer()) // 同上
            .disableHtmlEscaping() // 禁用Html Escape，确保符号不转义：&<>='
            .create();

    // 为LinkedTreeMap定制的序列化器
    public static JsonSerializer<LinkedTreeMap<?, ?>> newMapSerializer() {
        return new JsonSerializer<>() {
            @Override
            public JsonElement serialize(LinkedTreeMap<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
                List<String> keys = src.keySet().stream().map(Object::toString).sorted().toList();
                JsonObject o = new JsonObject();
                for (String k : keys) {
                    o.add(k, context.serialize(src.get(k)));
                }
                return o;
            }
        };
    }

    // 为Number定制化的序列化器
    private static <T extends Number> JsonSerializer<T> newNumberSerializer() {
        return new JsonSerializer<T>() {
            @Override
            public JsonElement serialize(T number, Type type, JsonSerializationContext context) {
                if (number instanceof Integer) {
                    return new JsonPrimitive(number.intValue());
                }
                if (number instanceof Long) {
                    return new JsonPrimitive(number.longValue());
                }
                if (number instanceof Double) {
                    long longValue = number.longValue();
                    double doubleValue = number.doubleValue();
                    if (longValue == doubleValue) {
                        return new JsonPrimitive(longValue);
                    }
                }
                return new JsonPrimitive(number);
            }
        };
    }
}
