package com.sata.yqj.cqdxer.communication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class SystemUtils {
    //缓存文件名
    private static final String TEMP_FILE_NAME = "cqdxer.temp";
    
    /**
     * @Description: 写出到缓存文件
     * @param cacheWrite   
     * @return void    
     * @date 2020年9月19日 上午11:40:20
     */
    public static void cacheWrite(Map<String, String> cacheWrite) {
        //组装写入list集合
        List<String> lines = new ArrayList<>();
        for (String string : cacheWrite.keySet()) {
            lines.add(string+"="+cacheWrite.get(string));
        }
        
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(System.getProperty("java.io.tmpdir") + File.separator + TEMP_FILE_NAME));
            IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR, fileOutputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        IOUtils.closeQuietly(fileOutputStream);
    }
    
    /**
     * @Description: 从缓存文件读取内容
     * @return Map<String,String>    
     * @date 2020年9月19日 上午11:40:50
     */
    public static Map<String, String> cacheRead() {
        File cacheFile = new File(System.getProperty("java.io.tmpdir") + File.separator + TEMP_FILE_NAME);
        if(!cacheFile.exists()) {
            return null;
        }
        
        List<String> readLines = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(cacheFile);
            readLines = IOUtils.readLines(fileInputStream);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        IOUtils.closeQuietly(fileInputStream);
        
        if(readLines == null) {
            return null;
        }
        
        //替换或写入key
        Map<String,String> contentMap = new HashMap<>();
        for (String line : readLines) {
            if(StringUtils.isNoneBlank(line)) {
                String[] split = line.split("=");
                contentMap.put(split[0], split[1]);
            }
        }
        return contentMap;
    }
}
