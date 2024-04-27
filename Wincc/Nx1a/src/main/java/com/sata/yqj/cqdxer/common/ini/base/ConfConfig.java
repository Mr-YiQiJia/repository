package com.sata.yqj.cqdxer.common.ini.base;

import org.springframework.stereotype.Component;

@Component
public class ConfConfig extends IniLoader{

    @Override
    String getIniPath() {
        return "conf/config.ini";
    }
}
