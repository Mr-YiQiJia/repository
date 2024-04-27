package com.sata.view.stage;

import com.sata.common.BeanUtil;
import com.sata.common.ini.Desktop;
import com.sata.view.StageLoader;
import javafx.scene.image.Image;

public class IndexStage extends StageLoader {
    private final static Desktop desktop = BeanUtil.getBean(Desktop.class);

    @Override
    public void loadProperties() {
        setTitle("CQDXER");
        getIcons().add(new Image("images/favicon.png"));
        Double height = desktop.getHeight();
        if (height != null) {
            setHeight(height);
        }
        Double width = desktop.getWidth();
        if (width != null) {
            setWidth(width);
        }
        Double x = desktop.getX();
        if (x != null) {
            setX(x);
        }
        Double y = desktop.getY();
        if (y != null) {
            setY(y);
        }
    }

    @Override
    public String loadFxmlPath() {
        return "v2/index.fxml";
    }
}
