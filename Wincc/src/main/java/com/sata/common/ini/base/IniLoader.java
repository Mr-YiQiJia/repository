package com.sata.common.ini.base;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;

import java.io.File;

public abstract class IniLoader {
    abstract String getIniPath();
    private Ini ini;

    public IniLoader() {
        this.ini = iniLoad();
    }

    @SneakyThrows
    public Ini iniLoad() {
        File file = new File(getIniPath());
        if (!file.exists()) {
            FileUtils.touch(file);
        }
        return new Ini(file);
    }

    @SneakyThrows
    public void put(String sectionName, String optionName, Object value){
        if(value != null){
            this.ini.put(sectionName, optionName, value);
            ini.store();
        }
    }

    public String get(Object sectionName, Object optionName) {
        return get(sectionName, optionName, String.class);
    }

    public <T> T get(Object sectionName, Object optionName, Class<T> clazz) {
        return this.ini.get(sectionName, optionName, clazz);
    }
}
