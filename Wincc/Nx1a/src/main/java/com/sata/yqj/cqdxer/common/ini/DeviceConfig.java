package com.sata.yqj.cqdxer.common.ini;

import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.exception.InValiDataException;

import java.io.IOException;
import java.util.Optional;

public class DeviceConfig extends Config {
    private static Character MIN = 'A';
    private static Character MAX = 'Z';
    enum Device {
        Number
    }

    public static String getNumber() {
        Character current =(char)(((int)get() + 1 -(int)MIN)%((int)MAX-(int)MIN)+(int)MIN);
        put(current);
        return String.valueOf(current);
    }

    private static Character get() {
        return Optional.ofNullable(INI.get(Device.class.getSimpleName(), Device.Number.name(), Character.class)).orElse(MIN);
    }

    private static void put(Character cha) {
        INI.put(Device.class.getSimpleName(), Device.Number.name(), cha);
        try {
            INI.store();
        } catch (IOException e) {
            throw new InValiDataException(I18N.getString("error.ini.store"), e);
        }
    }
}
