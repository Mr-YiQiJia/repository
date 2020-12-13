package com.sata.yqj.cqdxer.desktop;

/**
 * @Description: 频率表
 * @author YQJ  
 * @date 2020年10月18日 下午3:32:32
 */
public enum FrequencyTable {
    _0("00","0M","0.000-0.000"),
    _6("0B","6M","50.000-54.000"),
    _10("0A","10M","28.000-29.700"),
    _12("09","12M","24.890-24.990"),
    _15("08","15M","21.000-21.450"),
    _17("07","17M","18.068-18.168"),
    _20("06","20M","14.000-14.350"),
    _30("05","30M","10.100-10.150"),
    _50("04","40M","7.000-7.200"),
    _60("03","60M","5.3510-5.367"),
    _80("02","80M","3.500-3.900"),
    _160("01","160M","1.800-2.000");
    private String content;
    private String frequency;
    private String band;

    private FrequencyTable(String content,String frequency,String band) {
        this.content = content;
        this.frequency = frequency;
        this.band = band;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getContent() {
        return content;
    }
    
    public String getBand() {
        return band;
    }

    public static String getFrequencyValue(String content) {
        FrequencyTable[] values = FrequencyTable.values();
        for (FrequencyTable modelTable : values) {
            if (modelTable.getContent().equalsIgnoreCase(content)) {
                return modelTable.getFrequency();
            }
        }
        return FrequencyTable._0.getFrequency();
    }
    public static String getBandValue(String content) {
        FrequencyTable[] values = FrequencyTable.values();
        for (FrequencyTable modelTable : values) {
            if (modelTable.getContent().equalsIgnoreCase(content)) {
                return modelTable.getBand();
            }
        }
        return FrequencyTable._0.getBand();
    }
    public static String getContent(String value) {
        FrequencyTable[] values = FrequencyTable.values();
        for (FrequencyTable modelTable : values) {
            if (modelTable.getBand().equalsIgnoreCase(value) || modelTable.getFrequency().equalsIgnoreCase(value)) {
                return modelTable.getContent();
            }
        }
        return null;
    }
}
