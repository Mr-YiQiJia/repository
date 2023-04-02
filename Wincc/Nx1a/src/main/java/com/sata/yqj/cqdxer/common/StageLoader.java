package com.sata.yqj.cqdxer.common;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.ObjectUtils;

public abstract class StageLoader extends SceneLoader {
    public abstract void initStage(Stage main);

    private String fxId;

    private Stage stage = new Stage();

    public StageLoader() {
        fxId = StrUtlis.toLowerFirst(this.getClass().getSimpleName());
        this.initStage(stage);
    }

    public String getFxId() {
        return fxId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setOwner(Window owner) {
        if (ObjectUtils.isNotEmpty(this.stage.getOwner())) {
            return;
        }
        this.stage.initOwner(owner);
        this.stage.initModality(Modality.APPLICATION_MODAL);
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
