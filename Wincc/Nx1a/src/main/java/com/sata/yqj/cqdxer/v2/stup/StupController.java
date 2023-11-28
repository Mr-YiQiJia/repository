package com.sata.yqj.cqdxer.v2.stup;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.common.ini.SettingStart;
import com.sata.yqj.cqdxer.serial.SerialPortManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class StupController implements Initializable {
    private static final SerialPortManager manager = SerialPortManager.getManager();
    @FXML
    private ComboBox<String> fieldPort;
    @FXML
    private ComboBox<Integer> fieldBaudRate;
    @FXML
    private JFXToggleButton fieldConnect;
    @FXML
    private JFXCheckBox fieldAutoConnect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPort();
        initBaudRate();
        initConnectStatus();
        // 自动连接,按钮,绑定
        fieldAutoConnect.selectedProperty().bindBidirectional(manager.autoConnect);
    }

    /**
     * @return void
     * @Description: 初始化下拉框(port)
     * @date 2020年8月30日 下午8:21:30
     */
    private void initPort() {
        fieldPort.setItems(FXCollections.observableArrayList(manager.portList));
        fieldPort.valueProperty().bindBidirectional(manager.curentPort);
        fieldPort.disableProperty().bind(manager.connectStatus);
    }

    /**
     * @return void
     * @Description: 初始化下拉框(baudRate)
     * @date 2020年8月30日 下午8:21:30
     */
    private void initBaudRate() {
        fieldBaudRate.setItems(FXCollections.observableArrayList(manager.baudRateList));
        fieldBaudRate.disableProperty().bind(manager.connectStatus);
        fieldBaudRate.valueProperty().bindBidirectional(manager.curentBaudRate);
        fieldBaudRate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                System.out.println("newValue=" + newValue);
            }
        });
    }

    /**
     * @return void
     * @Description: 动态刷新串口连接状态
     * @date 2020年9月12日 下午5:27:36
     */
    private void initConnectStatus() {
        BooleanBinding fieldPortBinding = fieldPort.getSelectionModel().selectedItemProperty().isNull();
        BooleanBinding fieldBaudRateBinding = fieldBaudRate.getSelectionModel().selectedItemProperty().isNull();
        fieldConnect.disableProperty().bind(Bindings.or(fieldPortBinding, fieldBaudRateBinding));
        fieldConnect.selectedProperty().bindBidirectional(manager.connectStatus);
        fieldConnect.setOnAction(event -> {
            JFXToggleButton but = (JFXToggleButton) event.getSource();
            if (but.isSelected()) {
                manager.open();
            } else {
                manager.close();
            }
        });
    }
}
