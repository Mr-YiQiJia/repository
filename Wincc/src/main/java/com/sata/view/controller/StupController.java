package com.sata.view.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import com.sata.common.BeanUtil;
import com.sata.serial.SerialPortManager;
import com.sata.serial.SerialPortUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StupController implements Initializable {
    private SerialPortManager portManager = BeanUtil.getBean(SerialPortManager.class);
    
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
        fieldAutoConnect.selectedProperty().bindBidirectional(portManager.autoConnect);
    }

    /**
     * @return void
     * @Description: 初始化下拉框(port)
     * @date 2020年8月30日 下午8:21:30
     */
    private void initPort() {
        fieldPort.setItems(FXCollections.observableArrayList(SerialPortUtils.findPorts()));
        fieldPort.valueProperty().bindBidirectional(portManager.curentPort);
        fieldPort.disableProperty().bind(portManager.connectStatus);
    }

    /**
     * @return void
     * @Description: 初始化下拉框(baudRate)
     * @date 2020年8月30日 下午8:21:30
     */
    private void initBaudRate() {
        fieldBaudRate.setItems(FXCollections.observableArrayList(portManager.baudRateList));
        fieldBaudRate.disableProperty().bind(portManager.connectStatus);
        fieldBaudRate.valueProperty().bindBidirectional(portManager.curentBaudRate);
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
        fieldConnect.selectedProperty().bindBidirectional(portManager.connectStatus);
    }
}
