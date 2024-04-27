package com.sata.yqj.cqdxer.common.ini;

import com.sata.yqj.cqdxer.common.ini.base.ConfConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Connects {
    @Autowired
    private ConfConfig cfg;

    public enum Part {
        SerialPort, SerialBaudRate, AutoConnect
    }

    public void addSerialPort(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.SerialPort.name(), value);
    }

    public String getSerialPort() {
        return cfg.get(this.getClass().getSimpleName(), Part.SerialPort.name());
    }

    public void addSerialBaudRate(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.SerialBaudRate.name(), value);
    }

    public Integer getSerialBaudRate() {
        return cfg.get(this.getClass().getSimpleName(), Part.SerialBaudRate.name(), Integer.class);
    }

    public void addAutoConnect(Object value) {
        cfg.put(this.getClass().getSimpleName(), Part.AutoConnect.name(), value);
    }

    public Boolean getAutoConnect() {
        return cfg.get(this.getClass().getSimpleName(), Part.AutoConnect.name(), Boolean.class);
    }
}
