package com.sata.yqj.cqdxer.common;

import com.sata.yqj.cqdxer.exception.InValiDataException;
import jdk.nashorn.internal.runtime.options.Option;
import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

public class IniUtils {

    public static class Config {
        private final static String CONNECT = "Connect";
        private final static String DESKTOP = "Desktop";
        private final static Ini INI = initConfig();

        private static Ini initConfig() {
            try {
                File file = new File("conf/config.ini");
                if (!file.exists()) {
                    FileUtils.touch(file);
                }
                return new Ini(file);
            } catch (IOException e) {
                throw new InValiDataException(I18N.getString("error.ini.load"), e);
            }
        }

        public static String getConnect(String key) {
            return INI.get(CONNECT, key);
        }

        public static <T> T getConnect(String key, Class<T> clazz) {
            return INI.get(CONNECT, key, clazz);
        }

        public static void addConnect(String key, Object value) {
            INI.put(CONNECT, key, value);
            try {
                INI.store();
            } catch (IOException e) {
                throw new InValiDataException(I18N.getString("error.ini.store"), e);
            }
        }

        public static void removeConnect(String key) {
            INI.remove(CONNECT, key);
            try {
                INI.store();
            } catch (IOException e) {
                throw new InValiDataException(I18N.getString("error.ini.store"), e);
            }
        }

        public static String getDesktop(String key) {
            return INI.get(DESKTOP, key);
        }

        public static <T> T getDesktop(String key, Class<T> clazz) {
            return INI.get(DESKTOP, key, clazz);
        }

        public static void addDesktop(String key, Object value) {
            INI.put(DESKTOP, key, value);
            try {
                INI.store();
            } catch (IOException e) {
                throw new InValiDataException(I18N.getString("error.ini.store"), e);
            }
        }

        public static void removeDesktop(String key) {
            INI.remove(DESKTOP, key);
            try {
                INI.store();
            } catch (IOException e) {
                throw new InValiDataException(I18N.getString("error.ini.store"), e);
            }
        }
    }

    public static class Setting {
        private final static String SECTION = "Setting";
        private final static Ini INI = initSetting();

        private static Ini initSetting() {
            try {
                File file = new File("setting.ini");
                if (!file.exists()) {
                    FileUtils.touch(file);
                }
                return new Ini(file);
            } catch (IOException e) {
                throw new InValiDataException(I18N.getString("error.ini.load"), e);
            }
        }

        public static String getLanguage() {
            return Optional.ofNullable(INI.get(SECTION, "Language")).orElse(Locale.ENGLISH.getLanguage());
        }
    }
}
