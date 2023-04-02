package com.sata.yqj.cqdxer.v2.index;

import com.sata.yqj.cqdxer.common.IniUtils;
import com.sata.yqj.cqdxer.common.StageLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;

public class IndexStage extends StageLoader {
    public void initStage(Stage main) {
        Scene scene = getScene("v2/index.fxml");
        main.setScene(scene);
        main.setTitle("Antennas Controller");
        main.getIcons().add(new Image("images/favicon.png"));
        Double height = IniUtils.Config.getDesktop("Height",Double.class);
        if(ObjectUtils.isNotEmpty(height)){
            main.setHeight(height);
        }
        Double width = IniUtils.Config.getDesktop("Width",Double.class);
        if(ObjectUtils.isNotEmpty(width)){
            main.setWidth(width);
        }
        Double x = IniUtils.Config.getDesktop("X",Double.class);
        if(ObjectUtils.isNotEmpty(x)){
            main.setX(x);
        }
        Double y = IniUtils.Config.getDesktop("Y",Double.class);
        if(ObjectUtils.isNotEmpty(y)){
            main.setY(y);
        }
    }

    private IndexStage() {
    }

    public static IndexStage getInstance(){
        return IndexStage.Holder.instance;
    }

    static class Holder{
        private static final IndexStage instance = new IndexStage();
    }
}
