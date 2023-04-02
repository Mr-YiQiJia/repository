package com.sata.yqj.cqdxer.mapping;

import java.io.Serializable;

public class MappingVo implements Serializable {
    private String id;
    private int instructIndex;
    private String instructCode;
    private String uiText;
    private String uiColor;

    public MappingVo() {
    }

    public MappingVo(String id, int instructIndex, String instructCode, String uiText, String uiColor) {
        this.id = id;
        this.instructIndex = instructIndex;
        this.instructCode = instructCode;
        this.uiText = uiText;
        this.uiColor = uiColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInstructIndex() {
        return instructIndex;
    }

    public void setInstructIndex(int instructIndex) {
        this.instructIndex = instructIndex;
    }

    public String getInstructCode() {
        return instructCode;
    }

    public void setInstructCode(String instructCode) {
        this.instructCode = instructCode;
    }

    public String getUiText() {
        return uiText;
    }

    public void setUiText(String uiText) {
        this.uiText = uiText;
    }

    public String getUiColor() {
        return uiColor;
    }

    public void setUiColor(String uiColor) {
        this.uiColor = uiColor;
    }
}
