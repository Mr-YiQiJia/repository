package com.sata.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;

import java.io.InputStream;

public class JsonReaderUtils {
    public final static ObjectMapper MAPPER = JsonMapper.builder().build();

    @SneakyThrows
    public static String read(String resource) {
        InputStream in = JsonReaderUtils.class.getClassLoader().getResourceAsStream(resource);
        return MAPPER.readTree(in).toString();
    }

    @SneakyThrows
    public static <T> T readValue(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }
    @SneakyThrows
    public static <T> T readValue(String resource, TypeReference<T> valueTypeRef) {
        InputStream in = JsonReaderUtils.class.getClassLoader().getResourceAsStream(resource);
        return MAPPER.readValue(in, valueTypeRef);
    }
}
