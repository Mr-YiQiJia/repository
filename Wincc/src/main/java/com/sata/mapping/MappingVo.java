package com.sata.mapping;

import lombok.Data;

import java.io.Serializable;

@Data
public class MappingVo implements Serializable {
    // 显示id
    private String id;
    // 指令位置
    private int instructIndex;
    // 指令编码
    private String instructCode;
    // 显示内容
    private String uiText;
    private String uiColor;
}