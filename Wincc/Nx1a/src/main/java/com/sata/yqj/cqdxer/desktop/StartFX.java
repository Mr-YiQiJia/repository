package com.sata.yqj.cqdxer.desktop;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // 统一输出异常
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            Alter.popup("未知错误!!! 请联系开发者");
            throwable.printStackTrace();
        });

        // 加载界面xml和界面css样式
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("desktop.fxml"));
        Scene scene = new Scene(loader.load());
        AnchorPaneController contrl = loader.getController();
        scene.getStylesheets().add(getClass().getClassLoader().getResource("desktop.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("CQDXer");
        stage.getIcons().add(new Image("images/favicon.png"));
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setHeight(contrl.stage_height);
        stage.setWidth(contrl.stage_width);
        stage.setX(contrl.stage_x);
        stage.setY(contrl.stage_y);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(KeyCode.CONTROL == event.getCode()) {
                contrl.pressedKeys.add(event.getCode());
            }
        });
        stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(KeyCode.CONTROL == event.getCode()) {
                contrl.pressedKeys.remove(event.getCode());
            }
        });
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            if(!contrl.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            if(event.getDeltaY() < 0) {
                //缩小屏幕
                double width = stage.getWidth()+event.getDeltaY();
                double height = stage.getHeight()+event.getDeltaY();
                if(height < 600) {
                    return;
                }
                stage.setY((primaryScreenBounds.getHeight()-height)/2);
                stage.setX((primaryScreenBounds.getWidth()-width)/2);
                stage.setWidth(width);
                stage.setHeight(height);
            }else {
                //放大屏幕
                double width = stage.getWidth()+event.getDeltaY();
                double height = stage.getHeight()+event.getDeltaY();
                if(height > primaryScreenBounds.getHeight()) {
                    return;
                }
                stage.setY((primaryScreenBounds.getHeight()-height)/2);
                stage.setX((primaryScreenBounds.getWidth()-width)/2);
                stage.setWidth(width);
                stage.setHeight(height);
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // 关闭窗口，缓存数据，退出jvm
                try {
                    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            contrl.cacheWrite();
                        }
                    }));
                } catch (Exception e) {
                    Runtime.getRuntime().halt(0);
                }
                Runtime.getRuntime().exit(0);
            }
        });
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
