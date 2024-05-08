package com.sata.view.controller;

import com.sata.common.BeanUtil;
import com.sata.common.I18N;
import com.sata.mapping.MappingManager;
import com.sata.mapping.MappingVo;
import com.sata.serial.SerialPortManager;
import com.sata.view.StageManager;
import com.sata.view.stage.HelpStage;
import com.sata.view.stage.IndexStage;
import com.sata.view.stage.StupStage;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.apache.commons.lang3.ObjectUtils;

import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class IndexController implements Initializable {
    private SerialPortManager portManager = BeanUtil.getBean(SerialPortManager.class);
    private StageManager stageManager = BeanUtil.getBean(StageManager.class);
    private MappingManager mappingManager = BeanUtil.getBean(MappingManager.class);

    @FXML
    private Label fieldFrequency;
    @FXML
    private Button fieldAnt1;
    @FXML
    private Button fieldAnt2;
    @FXML
    private Button fieldAnt3;
    @FXML
    private Button fieldAnt4;
    @FXML
    private Button fieldAnt5;
    @FXML
    private Button fieldAnt6;
    @FXML
    private Button fieldAnt7;
    @FXML
    private Button fieldAnt8;
    @FXML
    private Label fieldSignalMode;
    @FXML
    private Label fieldFlexMode;
    @FXML
    private Button fieldOff;
    @FXML
    private ComboBox deviceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deviceBox.setItems(deviceBoxNumber());
        initIndexListener();
        initDataListener();
    }

    private ObservableList<String> deviceBoxNumber(){
        List<Character> value = new ArrayList();
        for (int i = 'A'; i <= 'Z'; i++) {
            value.add((char)i);
        }
        String templ = I18N.getString("device.number");
        return FXCollections.observableArrayList(value.stream().map(o -> {
            return MessageFormat.format(templ, o);
        }).collect(Collectors.toList()));
    }

    @FXML
    private void toSerialConnect() {
        stageManager.get(StupStage.class).show();
    }

    @FXML
    private void toAbout() {
        stageManager.get(HelpStage.class).show();
    }

    private Map<String, MappingVo> convertInstructToUi(String data) {
        Map<String, MappingVo> mapping = new HashMap<>();
        if (ObjectUtils.isEmpty(data)) {
            return mapping;
        }
        String[] instructs = data.split(" ");
        for (int i = 0; i < instructs.length; i++) {
            String instruct = instructs[i];
            for (MappingVo vo : mappingManager.indexReceive) {
                if(i == 2 && i == vo.getInstructIndex()){
                    vo.setInstructCode(instruct);
                    mapping.put(vo.getId(),vo);
                }
                if(i != vo.getInstructIndex()){
                    continue;
                }
                if(!instruct.equals(vo.getInstructCode())){
                    continue;
                }
                mapping.put(vo.getId(),vo);
            }
        }
        return mapping;
    }

    private void settingStyleByData(Map<String, MappingVo> mappingVoMap) {
        Platform.runLater(() -> {
            if (mappingVoMap.isEmpty()) {
                return;
            }
            settingAntDispaly(mappingVoMap);
            settingSignal(mappingVoMap);
            settingAnt(mappingVoMap);
            settingFrequency(mappingVoMap);
            settingFlex(mappingVoMap);
            settingOff(mappingVoMap);
        });
    }

    private void initDataListener() {
        portManager.dataChange.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                settingStyleByData(convertInstructToUi(newValue));
            }
        });
    }

    private void settingFlex(Map<String, MappingVo> mappingVoMap) {
        String id = fieldFlexMode.getId();
        if (mappingVoMap.containsKey(id)) {
            fieldFlexMode.setText(mappingVoMap.get(id).getUiText());
            fieldFlexMode.getStyleClass().add("color-blue");
        } else {
            fieldFlexMode.setText(null);
            fieldFlexMode.getStyleClass().removeAll("color-blue");
        }
    }

    private void settingSignal(Map<String, MappingVo> mappingVoMap) {
        String id = fieldSignalMode.getId();
        SimpleBooleanProperty simpleBoolean = new SimpleBooleanProperty(mappingVoMap.containsKey(id));
        fieldSignalMode.disableProperty().bind(simpleBoolean.not());
        fieldSignalMode.getStyleClass().removeAll("color-blue","color-green","color-mud");
        if (mappingVoMap.containsKey(id)) {
            fieldSignalMode.setText(mappingVoMap.get(id).getUiText());
            fieldSignalMode.getStyleClass().add("color-blue");
        } else {
            fieldSignalMode.setText(null);
        }
    }

    private void settingOff(Map<String, MappingVo> mappingVoMap) {
        String id = fieldOff.getId();
        if (mappingVoMap.containsKey(id)) {
            fieldOff.setText(mappingVoMap.get(id).getUiText());
            if("off".equalsIgnoreCase(mappingVoMap.get(id).getUiText())){
                stageManager.get(IndexStage.class).reload().show();
            }else {
                fieldOff.getStyleClass().add("color-green");
            }
        }
    }

    private void settingFrequency(Map<String, MappingVo> mappingVoMap) {
        fieldFrequency.getParent().getStyleClass().removeAll("screen-error","screen-red");
        if ("13".equals(mappingVoMap.get(fieldFrequency.getId()).getInstructCode())) {
            fieldFrequency.getParent().getStyleClass().add("screen-red");
            return;
        }
        String id = fieldFrequency.getId();
        if (mappingVoMap.containsKey(id)) {
            fieldFrequency.setText(mappingVoMap.get(id).getUiText());
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (10 <= Integer.valueOf(instructCode) && Integer.valueOf(instructCode) <= 12) {
                fieldFrequency.getParent().getStyleClass().add("screen-error");
            }
        }
        settingFrequencyWithFlex(mappingVoMap);
    }

    private void settingFrequencyWithFlex(Map<String, MappingVo> mappingVoMap) {
        if ("Manual".equals(mappingVoMap.get(fieldFlexMode.getId()).getUiText())) {
            if("11".equals(mappingVoMap.get(fieldFrequency.getId()).getInstructCode())){
                fieldFrequency.setText("BAND ERROR");
                fieldFrequency.getParent().getStyleClass().add("screen-error");
            }else {
                fieldFrequency.setText("Manual mode");
                fieldFrequency.getParent().getStyleClass().removeAll("screen-error");
            }
        }
    }

    private void settingAnt(Map<String, MappingVo> mappingVoMap) {
        if (mappingVoMap.containsKey(fieldAnt1.getId())) {
            fieldAnt1.getStyleClass().add("color-green");
        } else {
            fieldAnt1.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt2.getId())) {
            fieldAnt2.getStyleClass().add("color-green");
        } else {
            fieldAnt2.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt3.getId())) {
            fieldAnt3.getStyleClass().add("color-green");
        } else {
            fieldAnt3.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt4.getId())) {
            fieldAnt4.getStyleClass().add("color-green");
        } else {
            fieldAnt4.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt5.getId())) {
            fieldAnt5.getStyleClass().add("color-green");
        } else {
            fieldAnt5.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt6.getId())) {
            fieldAnt6.getStyleClass().add("color-green");
        } else {
            fieldAnt6.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt7.getId())) {
            fieldAnt7.getStyleClass().add("color-green");
        } else {
            fieldAnt7.getStyleClass().removeAll("color-green");
        }

        if (mappingVoMap.containsKey(fieldAnt8.getId())) {
            fieldAnt8.getStyleClass().add("color-green");
        } else {
            fieldAnt8.getStyleClass().removeAll("color-green");
        }
    }

    private void settingAntDispaly(Map<String, MappingVo> mappingVoMap) {
        MappingVo count = mappingVoMap.get("intDispalyCount");
        Integer display = ObjectUtils.isEmpty(count) ? 0 : Integer.valueOf(count.getInstructCode());
        SimpleBooleanProperty simple4 = new SimpleBooleanProperty(display >= 4);
        SimpleBooleanProperty simple6 = new SimpleBooleanProperty(display >= 6);
        SimpleBooleanProperty simple8 = new SimpleBooleanProperty(display >= 8);
        fieldAnt1.disableProperty().bind(simple4.not());
        fieldAnt2.disableProperty().bind(simple4.not());
        fieldAnt3.disableProperty().bind(simple4.not());
        fieldAnt4.disableProperty().bind(simple4.not());
        fieldAnt5.disableProperty().bind(simple6.not());
        fieldAnt6.disableProperty().bind(simple6.not());
        fieldAnt7.disableProperty().bind(simple8.not());
        fieldAnt8.disableProperty().bind(simple8.not());
    }

    private void initIndexListener() {
        fieldAnt1.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt2.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt3.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt4.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt5.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt6.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt7.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldAnt8.setOnAction(event -> {
            MappingVo mapping = mappingManager.indexReceive.stream().filter(vo -> vo.getId().equals(((Button) event.getSource()).getId())).findFirst().get();
            String[] data = mappingManager.indexSend(4,mapping.getInstructCode());
            portManager.sendData(data);
        });
        fieldOff.setOnAction(event -> {
            String[] data = mappingManager.indexSend(5,"1");
            portManager.sendData(data);
        });
    }
}
