package com.sata.yqj.cqdxer.common;

import com.sata.yqj.cqdxer.common.ini.SettingStart;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    private static final Locale language = Locale.forLanguageTag(SettingStart.getLanguage());
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("v2.i18n.language", language);

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
