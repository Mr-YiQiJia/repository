package com.sata;

import com.sata.common.BeanUtil;
import com.sata.common.ini.Connects;
import com.sata.common.ini.Desktop;
import com.sata.serial.SerialPortManager;
import com.sata.view.StageLoader;
import com.sata.view.StageManager;
import com.sata.view.stage.HelpStage;
import com.sata.view.stage.IndexStage;
import com.sata.view.stage.StupStage;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.sata.**")
public class StartFX extends Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StartFX.class, args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageLoader.register(IndexStage.class).show();
        StageLoader.register(HelpStage.class);
        StageLoader.register(StupStage.class);
    }

    @Override
    public void stop() throws Exception {
        SerialPortManager portManager = BeanUtil.getBean(SerialPortManager.class);
        Desktop desktop = BeanUtil.getBean(Desktop.class);
        Connects connects = BeanUtil.getBean(Connects.class);
        IndexStage stage = BeanUtil.getBean(StageManager.class).get(IndexStage.class);

        desktop.addHeight(stage.getHeight());
        desktop.addWidth(stage.getWidth());
        desktop.addX(stage.getX());
        desktop.addY(stage.getY());
        connects.addSerialPort(portManager.curentPort.getValue());
        connects.addSerialBaudRate(portManager.curentBaudRate.getValue());
        connects.addAutoConnect(portManager.autoConnect.getValue());
    }
}
