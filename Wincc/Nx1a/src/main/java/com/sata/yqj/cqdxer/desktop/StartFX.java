package com.sata.yqj.cqdxer.desktop;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.sata.yqj.cqdxer.communication.SerialPortManager;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartFX extends Application {
    private SerialPortManager serialPortManager = SerialPortManager.getSerialPortManager();
    
    @Override
    public void start(Stage stage) throws Exception {
        // 统一输出异常
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            Alter.popup("未知错误!!! 请联系开发者");
        });

        // 加载界面xml和界面css样式
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("desktop.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("desktop.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AnchorPaneController contrl = loader.getController();
        scene.getStylesheets().add(getClass().getClassLoader().getResource("desktop.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("CQDXer");
        stage.getIcons().add(new Image("images/cqdxer.png"));
        stage.setMinWidth(contrl.STAGE_WIDTH);
        stage.setMinHeight(contrl.STAGE_HEIGHT);
        stage.show();
        // 关闭窗口，退出jvm
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                serialPortManager.setCache();
                System.exit(0);
            }
        });
        contrl.STAGE_X = stage.getX();
        contrl.STAGE_Y = stage.getY();
    }

    /*private void initSilder(AnchorPaneController contrl,Stage stage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double heightMax = new BigDecimal(primaryScreenBounds.getHeight()/STAGE_HEIGHT).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double withMax = new BigDecimal(primaryScreenBounds.getWidth()/STAGE_WIDTH).setScale(1, RoundingMode.HALF_UP).doubleValue();
        if(heightMax < withMax) {
            contrl.slider.setMax(heightMax);
        }else {
            contrl.slider.setMax(withMax);
        }
        contrl.slider.setMin(1);
        contrl.STAGE_X = stage.getX();
        contrl.STAGE_Y = stage.getY();
    }*/
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
