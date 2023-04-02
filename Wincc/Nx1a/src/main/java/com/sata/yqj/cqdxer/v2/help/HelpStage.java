package com.sata.yqj.cqdxer.v2.help;

import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.common.StageLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpStage extends StageLoader {

    @Override
    public void initStage(Stage main) {
        Scene scene = getScene("v2/help.fxml");
        main.setScene(scene);
        main.setTitle(I18N.getString("about"));
    }


    private HelpStage() {
    }

    public static HelpStage getInstance(){
        return HelpStage.Holder.instance;
    }

    static class Holder{
        private final static HelpStage instance = new HelpStage();
    }
}
