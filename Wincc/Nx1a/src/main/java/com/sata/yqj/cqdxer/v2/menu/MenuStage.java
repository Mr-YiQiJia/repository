package com.sata.yqj.cqdxer.v2.menu;

import com.sata.yqj.cqdxer.common.StageLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class MenuStage extends StageLoader {
    public void initStage(Stage main) {
        Scene scene = getScene("v2/menuTable.fxml");
        main.setScene(scene);
        main.setTitle("Menu");
    }

    public List<String> getTabIds(){
        return Arrays.asList("tabBand","tabCat","tabOther");
    }

    private MenuStage() { }

    public static MenuStage getInstance(){
        return Holder.instance;
    }

    static class Holder{
        private final static MenuStage instance = new MenuStage();
    }
}
