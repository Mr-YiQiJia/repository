package com.sata.common.ini;

import com.sata.common.ini.base.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class Startup {
    @Autowired
    private Setting cfg;

    public enum Part {
        Language, Footer, SingleInstance
    }

    public void addLanguage(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.Language.name(), value);
    }

    public String getLanguage() {
        return Optional.ofNullable(cfg.get(this.getClass().getSimpleName(), Part.Language.name())).orElse(Locale.CHINA.getLanguage());
    }

    public void addFooter(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.Footer.name(), value);
    }

    public String getFooter() {
        return cfg.get(this.getClass().getSimpleName(), Part.Footer.name());
    }

    public boolean getSingleInstance() {
        return Optional.ofNullable(cfg.get(this.getClass().getSimpleName(), Part.SingleInstance.name(),Boolean.class)).orElse(false);
    }
}
