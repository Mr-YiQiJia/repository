package com.sata.common.ini;

import com.sata.common.ini.base.ConfConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Desktop {

    @Autowired
    private ConfConfig cfg;

    public enum Part {
        Height, Width, X, Y
    }

    public void addHeight(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.Height.name(), value);
    }

    public Double getHeight() {
        return cfg.get(this.getClass().getSimpleName(), Part.Height.name(), Double.class);
    }

    public void addWidth(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.Width.name(), value);
    }

    public Double getWidth() {
        return cfg.get(this.getClass().getSimpleName(), Part.Width.name(), Double.class);
    }

    public void addX(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.X.name(), value);
    }

    public Double getX() {
        return cfg.get(this.getClass().getSimpleName(), Part.X.name(), Double.class);
    }

    public void addY(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.Y.name(), value);
    }

    public Double getY() {
        return cfg.get(this.getClass().getSimpleName(), Part.Y.name(), Double.class);
    }
}
