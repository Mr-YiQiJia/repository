package com.sata.yqj.cqdxer.common;

import com.sata.yqj.cqdxer.exception.InValiDataException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class SceneLoader {
    private final static Locale language = Locale.forLanguageTag(IniUtils.Setting.getLanguage());

    private List<String> getCss() {
        List<String> elements = new ArrayList<>();
        elements.add(getClass().getClassLoader().getResource("v2/css/common.css").toExternalForm());
        return elements;
    }

    public Scene getScene(String resource){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("v2.i18n.language", language));
            loader.setLocation(getClass().getClassLoader().getResource(resource));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().addAll(getCss());
            return scene;
        } catch (Exception e) {
            throw new InValiDataException(I18N.getString("error.stage.load"),e);
        }
    }
}
