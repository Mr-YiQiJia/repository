package com.sata.yqj.cqdxer.common.ini;

import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.exception.InValiDataException;

import java.io.IOException;

public class DesktopConfig extends Config{
    public enum Desktop{
        Height,Width,X,Y
    }

    public static String getDesktop(Desktop desktop) {
        return INI.get(Desktop.class.getSimpleName(), desktop.name());
    }

    public static <T> T getDesktop(Desktop desktop, Class<T> clazz) {
        return INI.get(Desktop.class.getSimpleName(), desktop.name(), clazz);
    }

    public static void addDesktop(Desktop desktop, Object value) {
        INI.put(Desktop.class.getSimpleName(), desktop.name(), value);
        try {
            INI.store();
        } catch (IOException e) {
            throw new InValiDataException(I18N.getString("error.ini.store"), e);
        }
    }
}
