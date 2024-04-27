package com.sata.yqj.cqdxer.common;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

/**
 * @author ThinkPad
 * @date 2024/4/25 14:48
 */
public final class JsonPathUtil {
    public static final Configuration cfg = Configuration.builder().mappingProvider(new JacksonMappingProvider(JsonReaderUtils.MAPPER)).build();

    public static DocumentContext parse(String json){
        return JsonPath.using(cfg).parse(json);
    }
}
