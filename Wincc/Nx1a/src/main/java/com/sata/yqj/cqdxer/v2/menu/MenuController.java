package com.sata.yqj.cqdxer.v2.menu;

import com.jfoenix.controls.JFXTabPane;
import com.sata.yqj.cqdxer.mapping.TabMenuEnum;
import com.sata.yqj.cqdxer.serial.SerialPortManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import org.apache.commons.lang3.ObjectUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private JFXTabPane tabMenu;

    public final static SimpleObjectProperty<Tab> current = new SimpleObjectProperty<>();

    private final ChangeListener<Tab> listener = new ChangeListener<Tab>() {
        @Override
        public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
            String outCode = TabMenuEnum.outCodeOf(newValue.getText());
            if (TabMenuEnum.Band.name().equals(oldValue.getText())) {
                String[] data = MenuBandController.BAND_SEND.clone();
                data[2] = outCode;
                SerialPortManager.getManager().sendData(data);
            } else if (TabMenuEnum.Cat.name().equals(oldValue.getText())) {
                String[] data = MenuCatController.CAT_SEND.clone();
                data[2] = outCode;
                SerialPortManager.getManager().sendData(data);
            } else if (TabMenuEnum.Other.name().equals(oldValue.getText())) {
                String[] data = MenuOtherController.OTHER_SEND.clone();
                data[2] = outCode;
                SerialPortManager.getManager().sendData(data);
            }
            observable.removeListener(listener);
            tabMenu.getSelectionModel().select(oldValue);
            observable.addListener(listener);
        }
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDataListener();
    }

    private void initDataListener() {
        SerialPortManager.getManager().dataChange.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(ObjectUtils.isEmpty(newValue)){
                    return;
                }
                settingStyleByData(newValue);
            }
        });
    }

    private Tab getTab(String tabName) {
        return tabMenu.getTabs().stream().filter(tab -> {
            return tab.getText().equalsIgnoreCase(tabName);
        }).findFirst().orElse(null);
    }

    private void settingStyleByData(String data) {
        Platform.runLater(() -> {
            String incode = data.split(" ")[1];
            Tab tab = getTab(TabMenuEnum.nameOf(incode));

            current.set(tab);
            tabMenu.getSelectionModel().selectedItemProperty().removeListener(listener);
            tabMenu.getSelectionModel().select(tab);
            tabMenu.getSelectionModel().selectedItemProperty().addListener(listener);
        });
    }

}
