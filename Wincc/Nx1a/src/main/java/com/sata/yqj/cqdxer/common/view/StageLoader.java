package com.sata.yqj.cqdxer.common.view;

import com.sata.yqj.cqdxer.common.BeanUtil;
import com.sata.yqj.cqdxer.common.I18N;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 窗口抽象
 * 窗口统一初始化
 * 增加窗口重载功能
 *
 * @author yiqijia
 * @date 2024/4/23 10:46
 */
public abstract class StageLoader extends Stage {
    public abstract void loadProperties();

    public abstract String loadFxmlPath();

    public StageLoader() {
        loadStage();
    }

    @SneakyThrows
    private void loadStage() {
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setResources(I18N.RESOURCE_BUNDLE);
        fXMLLoader.setLocation(ClassLoader.getSystemResource(this.loadFxmlPath()));
        Scene scene = new Scene(fXMLLoader.load());
        scene.getStylesheets().addAll(Stream.of(ClassLoader.getSystemResource("v2/css/common.css").toExternalForm()).collect(Collectors.toList()));
        this.setScene(scene);
        this.loadProperties();
    }

    public void reload() {
        StageLoader old = BeanUtil.reload(this.getClass());
        old.close();
        StageLoader news = BeanUtil.getBean(this.getClass());
        news.show();
    }
}
