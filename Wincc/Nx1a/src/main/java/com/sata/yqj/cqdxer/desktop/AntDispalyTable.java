package com.sata.yqj.cqdxer.desktop;

/**
 * @Description: ANT表
 * @author YQJ  
 * @date 2020年10月18日 下午3:32:32
 */
public enum AntDispalyTable {
    _GREEN("00","Green"),
    _GREY("01","rgb(110,66,0)"),
    _DARKGREY("03","rgb(80,47,0)");
    private String content;
    private String color;

    private AntDispalyTable(String content,String color) {
        this.content = content;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getContent() {
        return content;
    }
    
    public static String getAntColor(String content) {
        AntDispalyTable[] values = AntDispalyTable.values();
        for (AntDispalyTable modelTable : values) {
            if (modelTable.getContent().equalsIgnoreCase(content)) {
                return modelTable.getColor();
            }
        }
        return null;
    }
    public static String getContent(String value) {
        AntDispalyTable[] values = AntDispalyTable.values();
        for (AntDispalyTable modelTable : values) {
            if (modelTable.getColor().equalsIgnoreCase(value)) {
                return modelTable.getContent();
            }
        }
        return null;
    }
}
