package com.sata.yqj.cqdxer.common.ini;

import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.exception.InValiDataException;

import java.io.IOException;

public class ConnectConfig extends Config {
    public enum Connect{
        SerialPort,SerialBaudRate,AutoConnect
    }

    public static String getConnect(Connect con) {
        return INI.get(Connect.class.getSimpleName(), con.name());
    }

    public static <T> T getConnect(Connect con, Class<T> clazz) {
        return INI.get(Connect.class.getSimpleName(), con.name(), clazz);
    }

    public static void addConnect(Connect con, Object value) {
        INI.put(Connect.class.getSimpleName(), con.name(), value);
        try {
            INI.store();
        } catch (IOException e) {
            throw new InValiDataException(I18N.getString("error.ini.store"), e);
        }
    }
}
