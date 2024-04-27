package com.sata.common;

import com.sata.common.ini.Startup;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    private final static Startup startup = BeanUtil.getBean(Startup.class);
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("v2.i18n.language", Locale.forLanguageTag(startup.getLanguage()));

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
