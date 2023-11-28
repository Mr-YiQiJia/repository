package com.sata.yqj.cqdxer.common.ini;

import java.util.Locale;
import java.util.Optional;

public class SettingStart extends Start{
    public enum Setting {
        Language,Footer
    }

    public static String get(Setting key) {
        return INI.get(Setting.class.getSimpleName(), key.name());
    }

    public static String getLanguage() {
        return Optional.ofNullable(INI.get(Setting.class.getSimpleName(), Setting.Language.name())).orElse(Locale.CHINA.getLanguage());
    }
}
