package com.sata.yqj.cqdxer.desktop;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Model表
 * @author YQJ  
 * @date 2020年10月18日 下午3:32:32
 */
public enum ModelTable {
    _4("04","1x4",4),
    _6("06","1x6",6),
    _8("08","1x8",8);
    private String content;
    private String model;
    private Integer dispalyCount;

    private ModelTable(String content,String model,Integer dispalyCount) {
        this.content = content;
        this.model = model;
        this.dispalyCount = dispalyCount;
    }

    public Integer getDispalyCount() {
        return dispalyCount;
    }

    public String getModel() {
        return model;
    }

    public String getContent() {
        return content;
    }
    
    public static String getModelValue(String content) {
        ModelTable[] values = ModelTable.values();
        for (ModelTable modelTable : values) {
            if (modelTable.getContent().equalsIgnoreCase(content)) {
                return modelTable.getModel();
            }
        }
        return null;
    }
    
    public static String getContent(String value) {
        ModelTable[] values = ModelTable.values();
        for (ModelTable modelTable : values) {
            if (modelTable.getModel().equalsIgnoreCase(value)) {
                return modelTable.getContent();
            }
        }
        return null;
    }
    
    public static Integer getDispalyCount(String model) {
        ModelTable[] values = ModelTable.values();
        for (ModelTable modelTable : values) {
            if (modelTable.getModel().equalsIgnoreCase(model)) {
                return modelTable.getDispalyCount();
            }
        }
        return null;
    }
    
    public static List<String> getContents() {
        List<String> list = new ArrayList<>();
        ModelTable[] values = ModelTable.values();
        for (ModelTable modelTable : values) {
            list.add(modelTable.getModel());
        }
        return list;
    }
}
