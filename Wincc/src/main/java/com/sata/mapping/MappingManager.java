package com.sata.mapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sata.common.JsonReaderUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yiqijia
 * @date 2024/5/8 16:42
 */
@Component
public class MappingManager {
    public static final List<MappingVo> indexReceive = JsonReaderUtils.readValue("mapping/index.json",new TypeReference<List<MappingVo>>(){});
    public String[] indexSend(int location,String data) {
        String[] indexSend = new String[]{"FA", "F1", "00", "00", "00", "00", "FB"};
        indexSend[location] = data;
        return indexSend;
    }
}
