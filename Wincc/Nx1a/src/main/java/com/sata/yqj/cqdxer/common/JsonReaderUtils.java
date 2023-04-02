package com.sata.yqj.cqdxer.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sata.yqj.cqdxer.exception.InValiDataException;

import java.io.IOException;
import java.io.InputStream;

public class JsonReaderUtils {
    public final static ObjectMapper MAPPER = JsonMapper.builder().build();
    public static String read(String resource) {
        try {
            InputStream in = JsonReaderUtils.class.getClassLoader().getResourceAsStream(resource);
            return MAPPER.readTree(in).toString();
        } catch (IOException e) {
            throw new InValiDataException(I18N.getString("error.json.read"),e);
        }
    }
}
