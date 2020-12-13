package com.sata.yqj.cqdxer.desktop;

/**
 * @Description: ANT表
 * @author YQJ  
 * @date 2020年10月18日 下午3:32:32
 */
public enum AntSendTable {
    _0("00",0),
    _1("01",1),
    _2("02",2),
    _3("03",3),
    _4("04",4),
    _5("05",5),
    _6("06",6),
    _7("07",7),
    _8("08",8);
    private String content;
    private Integer index;

    private AntSendTable(String content,Integer index) {
        this.content = content;
        this.index = index;
    }

    public String getContent() {
        return content;
    }
    
    public Integer getIndex() {
        return index;
    }
    public static String getContent(Integer value) {
        AntSendTable[] values = AntSendTable.values();
        for (AntSendTable modelTable : values) {
            if (modelTable.getIndex() == value) {
                return modelTable.getContent();
            }
        }
        return null;
    }

}
