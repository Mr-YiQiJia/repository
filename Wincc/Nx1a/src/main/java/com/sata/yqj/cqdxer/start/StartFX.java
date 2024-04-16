package com.sata.yqj.cqdxer.start;

import com.sata.yqj.cqdxer.common.ini.ConnectConfig;
import com.sata.yqj.cqdxer.common.ini.DesktopConfig;
import com.sata.yqj.cqdxer.exception.GloablExceptionHandle;
import com.sata.yqj.cqdxer.serial.SerialPortManager;
import com.sata.yqj.cqdxer.v2.index.IndexStage;
import com.sun.javafx.stage.StageHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class StartFX extends Application {
    private static final Logger log = Logger.getLogger(StartFX.class.toString());

    @Override
    public void start(Stage stage) {
        // 统一输出异常
        new GloablExceptionHandle().exceptionHandler();

        // 加载界面xml和界面css样式
        IndexStage indexWindows = IndexStage.getInstance();
        indexWindows.show();

        // 程序关闭监听
        Stage main = StageHelper.getStages().stream().findFirst().get();
        main.setOnCloseRequest(event -> {
            this.mainShutdown(main);
        });
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    private void mainShutdown(Stage stage) {
        // 关闭窗口，缓存数据，退出jvm
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    Platform.exit();
                    addLocation(stage);
                    addConnect();
                }
            }));
        } catch (Exception e) {
            Runtime.getRuntime().halt(0);
        }
        Runtime.getRuntime().exit(0);
    }

    private void addLocation(Stage stage){
        DesktopConfig.addDesktop(DesktopConfig.Desktop.Width,stage.getWidth());
        DesktopConfig.addDesktop(DesktopConfig.Desktop.Height,stage.getHeight());
        DesktopConfig.addDesktop(DesktopConfig.Desktop.X,stage.getX());
        DesktopConfig.addDesktop(DesktopConfig.Desktop.Y,stage.getY());
    }

    public void addConnect(){
        ConnectConfig.addConnect(ConnectConfig.Connect.SerialPort,SerialPortManager.getManager().curentPort.getValue());
        ConnectConfig.addConnect(ConnectConfig.Connect.SerialBaudRate,SerialPortManager.getManager().curentBaudRate.getValue());
        ConnectConfig.addConnect(ConnectConfig.Connect.AutoConnect,SerialPortManager.getManager().autoConnect.getValue());
    }
}
