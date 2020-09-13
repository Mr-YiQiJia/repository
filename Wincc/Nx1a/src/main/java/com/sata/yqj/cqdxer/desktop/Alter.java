package com.sata.yqj.cqdxer.desktop;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @Description: javaFX弹框工具
 * @author YQJ  
 * @date 2020年9月4日 上午12:25:56
 */
public class Alter {
    public static void popup(String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("message");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
