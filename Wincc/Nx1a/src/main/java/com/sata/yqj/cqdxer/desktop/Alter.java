package com.sata.yqj.cqdxer.desktop;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * @Description: javaFX弹框工具
 * @author YQJ  
 * @date 2020年9月4日 上午12:25:56
 */
public class Alter {
    public static Optional<ButtonType> popup(String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("message");
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
