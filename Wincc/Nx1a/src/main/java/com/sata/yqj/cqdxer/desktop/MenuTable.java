package com.sata.yqj.cqdxer.desktop;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Menu表
 * @author YQJ  
 * @date 2020年10月18日 下午3:32:32
 */
public enum MenuTable {
    _BCD("00","BCD"),
    _232("01","232"),
    _CIV("02","CIV"),
    _MNL("03","MNL"),
    _Reset("04","Reset");
    private String content;
    private String menu;

    private MenuTable(String content,String menu) {
        this.content = content;
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }

    public String getContent() {
        return content;
    }
    
    public static String getMenuValue(String content) {
        MenuTable[] values = MenuTable.values();
        for (MenuTable modelTable : values) {
            if (modelTable.getContent().equalsIgnoreCase(content)) {
                return modelTable.getMenu();
            }
        }
        return null;
    }
    public static String getContent(String value) {
        MenuTable[] values = MenuTable.values();
        for (MenuTable modelTable : values) {
            if (modelTable.getMenu().equalsIgnoreCase(value)) {
                return modelTable.getContent();
            }
        }
        return null;
    }

    public static List<String> getContents() {
        List<String> list = new ArrayList<>();
        MenuTable[] values = MenuTable.values();
        for (MenuTable modelTable : values) {
            list.add(modelTable.getMenu());
        }
        return list;
    }
}
