package com.sata.yqj.cqdxer.mapping;

import java.util.Arrays;

public enum TabMenuEnum {
    Band("F2","1"), Cat("F3","2"), Other("F4","3");
    public String inCode;
    public String outCode;

    TabMenuEnum(String inCode,String outCode) {
        this.inCode = inCode;
        this.outCode = outCode;
    }

    public static String outCodeOf(String name){
        return Arrays.stream(values()).filter(eu -> {
            return eu.name().equals(name);
        }).map(tabMenuEnum -> tabMenuEnum.outCode).findFirst().orElse(null);
    }

    public static String nameOf(String inCode){
        return Arrays.stream(values()).filter(eu -> {
            return eu.inCode.equals(inCode);
        }).map(tabMenuEnum -> tabMenuEnum.name()).findFirst().orElse(null);
    }
}
