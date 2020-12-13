package com.sata.yqj.cqdxer.desktop;

/**
 * @Description: 开关表
 * @author YQJ  
 * @date 2020年10月18日 下午3:32:32
 */
public enum SwitchTable {
    _1("01","ON"),
    _2("02","OFF");
    private String content;
    private String switchBtn;

    private SwitchTable(String content,String switchBtn) {
        this.content = content;
        this.switchBtn = switchBtn;
    }

    public String getSwitchBtn() {
        return switchBtn;
    }

    public String getContent() {
        return content;
    }
    
    public static String getSwitchValue(String content) {
        SwitchTable[] values = SwitchTable.values();
        for (SwitchTable modelTable : values) {
            if (modelTable.getContent().equalsIgnoreCase(content)) {
                return modelTable.getSwitchBtn();
            }
        }
        return null;
    }
    public static String getContent(String value) {
        SwitchTable[] values = SwitchTable.values();
        for (SwitchTable modelTable : values) {
            if (modelTable.getSwitchBtn().equalsIgnoreCase(value)) {
                return modelTable.getContent();
            }
        }
        return null;
    }
}
