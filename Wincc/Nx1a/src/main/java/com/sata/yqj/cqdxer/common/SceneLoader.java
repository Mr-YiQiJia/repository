package com.sata.yqj.cqdxer.common;

import com.sata.yqj.cqdxer.exception.InValiDataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public abstract class SceneLoader {
    private List<String> getCss() {
        List<String> elements = new ArrayList<>();
        elements.add(getClass().getClassLoader().getResource("v2/css/common.css").toExternalForm());
        return elements;
    }

    public Scene getScene(String resource){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(I18N.RESOURCE_BUNDLE);
            loader.setLocation(getClass().getClassLoader().getResource(resource));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().addAll(getCss());
            return scene;
        } catch (Exception e) {
            throw new InValiDataException(I18N.getString("error.stage.load"),e);
        }
    }
}
