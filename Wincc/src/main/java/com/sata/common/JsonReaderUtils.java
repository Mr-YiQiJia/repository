package com.sata.common;

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
}
