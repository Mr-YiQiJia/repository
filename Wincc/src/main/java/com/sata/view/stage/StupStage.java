package com.sata.view.stage;

import com.sata.common.BeanUtil;
import com.sata.common.I18N;
import com.sata.view.StageLoader;
import com.sata.view.StageManager;
import javafx.stage.Modality;

public class StupStage extends StageLoader {
    private final static StageManager stageManager = BeanUtil.getBean(StageManager.class);

    @Override
    public void loadProperties() {
        setTitle(I18N.getString("serial.connect"));
        initOwner(stageManager.get(IndexStage.class));
        initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String loadFxmlPath() {
        return "v2/stup.fxml";
    }
}
