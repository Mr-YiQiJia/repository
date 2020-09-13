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

public class SystemUtils {
    
    public static void cacheWrite(Map<String, String> cacheWrite) {
        Map<String, String> cacheRead = cacheRead();
        if(null == cacheRead) {
            cacheRead = new HashMap<>();
        }
        cacheRead.putAll(cacheWrite);
        
        //重新变换为list集合，准备重新写入文件
        List<String> lines = new ArrayList<>();
        for (String string : cacheRead.keySet()) {
            lines.add(string+"="+cacheRead.get(string));
        }
        
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(System.getProperty("java.io.tmpdir") + File.separator + "cqdxer.temp"));
            IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR, fileOutputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        IOUtils.closeQuietly(fileOutputStream);
    }
    
    public static Map<String, String> cacheRead() {
        File cacheFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "cqdxer.temp");
        List<String> readLines = null;
        
        if(cacheFile.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(cacheFile);
                readLines = IOUtils.readLines(fileInputStream);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            IOUtils.closeQuietly(fileInputStream);
        }
        if(readLines == null) {
            return null;
        }
        
        //替换或写入key
        Map<String,String> contentMap = new HashMap<>();
        for (String line : readLines) {
            String[] split = line.split("=");
            contentMap.put(split[0], split[1]);
        }
        return contentMap;
    }
}
