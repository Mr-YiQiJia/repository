package com.sata.common.ini.base;

import org.springframework.stereotype.Component;

@Component
public class Setting extends IniLoader{
    @Override
    String getIniPath() {
        return "setting.ini";
    }
}
