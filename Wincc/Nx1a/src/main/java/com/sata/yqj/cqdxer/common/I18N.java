package com.sata.yqj.cqdxer.common;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    private static final Locale language = Locale.forLanguageTag(IniUtils.Setting.getLanguage());
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("v2.i18n.language", language);

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
