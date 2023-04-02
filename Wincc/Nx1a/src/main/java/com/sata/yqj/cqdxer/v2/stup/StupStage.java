package com.sata.yqj.cqdxer.v2.stup;

import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.common.StageLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StupStage extends StageLoader {
    @Override
    public void initStage(Stage main) {
        Scene scene = getScene("v2/stup.fxml");
        main.setScene(scene);
        main.setTitle(I18N.getString("serial.connect"));
    }

    private StupStage() {
    }

    public static StupStage getInstance(){
        return StupStage.Holder.instance;
    }

    static class Holder{
        private final static StupStage instance = new StupStage();
    }
}
