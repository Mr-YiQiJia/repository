package com.sata.yqj.cqdxer.common.ini;

import org.ini4j.Ini;

public class Config extends IniLoader{
    protected final static Ini INI = readPath("conf/config.ini");
}
