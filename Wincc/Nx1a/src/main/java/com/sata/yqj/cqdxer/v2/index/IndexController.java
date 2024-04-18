package com.sata.yqj.cqdxer.v2.index;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.sata.yqj.cqdxer.common.I18N;
import com.sata.yqj.cqdxer.common.JsonReaderUtils;
import com.sata.yqj.cqdxer.mapping.MappingVo;
import com.sata.yqj.cqdxer.serial.SerialPortManager;
import com.sata.yqj.cqdxer.v2.help.HelpStage;
import com.sata.yqj.cqdxer.v2.stup.StupStage;
import com.sun.javafx.stage.StageHelper;
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
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class IndexController implements Initializable {
    public final static String INDEX_MAPPING = JsonReaderUtils.read("mapping/index.json");
    private String[] INDEX_SEND = new String[]{"FA", "F1", "00", "00", "00", "00", "FB"};
    private Configuration cfg = Configuration.builder().mappingProvider(new JacksonMappingProvider(JsonReaderUtils.MAPPER)).build();
    private DocumentContext parse = JsonPath.using(cfg).parse(INDEX_MAPPING);
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
        Stage parent = StageHelper.getStages().stream().findFirst().orElse(null);
        StupStage stupWindows = StupStage.getInstance();
        stupWindows.setOwner(parent);
        stupWindows.show();
    }

    @FXML
    private void toAbout() {
        Stage parent = StageHelper.getStages().stream().findFirst().orElse(null);
        HelpStage helpWindows = HelpStage.getInstance();
        helpWindows.setOwner(parent);
        helpWindows.show();
    }

    @FXML
    private void toMenuPrepared() {
        String[] data = INDEX_SEND.clone();
        data[2] = "1";
        SerialPortManager.getManager().sendData(data);
    }

    private Map<String, MappingVo> convertInstructToUi(String data) {
        Map<String, MappingVo> mapping = new HashMap<>();
        if (ObjectUtils.isEmpty(data)) {
            return mapping;
        }
        TypeRef<List<MappingVo>> tr = new TypeRef<List<MappingVo>>() {
        };
        String[] instructs = data.split(" ");
        for (int i = 0; i < instructs.length; i++) {
            String instruct = instructs[i];
            if (i == 2) {
                List<MappingVo> read = parse.read("$..[?(@.instructIndex == '" + i + "')]", tr);
                Map<String, MappingVo> collect = read.stream().collect(Collectors.toMap(MappingVo::getId, vo -> {
                    vo.setInstructCode(instruct);
                    return vo;
                }));
                mapping.putAll(collect);
                continue;
            }
            List<MappingVo> read = parse.read("$..[?(@.instructIndex == '" + i + "' && @.instructCode == '" + instruct + "')]", tr);
            Map<String, MappingVo> collect = read.stream().collect(Collectors.toMap(MappingVo::getId, vo -> vo));
            mapping.putAll(collect);
        }
        return mapping;
    }

    private void settingStyleByData(Map<String, MappingVo> mappingVoMap) {
        Platform.runLater(() -> {
            String fxId = IndexStage.getInstance().getFxId();
            if (mappingVoMap.isEmpty() || mappingVoMap.containsKey(fxId)) {
                if (mappingVoMap.containsKey(fieldOff.getId()) && "OFF".equalsIgnoreCase(mappingVoMap.get(fieldOff.getId()).getUiText())) {
                    mappingVoMap.clear();
                }
                // 如当前命令为主界面,但仍有其他界面未关闭，则关闭所有非主界面
                StageHelper.getStages().stream().skip(1).collect(Collectors.toCollection(CopyOnWriteArrayList::new)).forEach(stage -> {
                    stage.close();
                });
                settingAntDispaly(mappingVoMap);
                settingSignal(mappingVoMap);
                settingAnt(mappingVoMap);
                settingFrequency(mappingVoMap);
                settingFlex(mappingVoMap);
                settingOff(mappingVoMap);
            }
        });
    }

    private void initDataListener() {
        SerialPortManager.getManager().dataChange.addListener(new ChangeListener<String>() {
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
        fieldOff.getStyleClass().removeAll("color-green");
        if (mappingVoMap.containsKey(id)) {
            fieldOff.setText(mappingVoMap.get(id).getUiText());
            fieldOff.getStyleClass().add("color-green");
        } else {
            fieldOff.setText("OFF");
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
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt1.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt2.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt2.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt3.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt3.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt4.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt4.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt5.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt5.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt6.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt6.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt7.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt7.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt8.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt8.getId() + "')].instructCode");
            String[] data = INDEX_SEND.clone();
            data[4] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldOff.setOnAction(event -> {
            String[] data = INDEX_SEND.clone();
            data[5] = "1";
            SerialPortManager.getManager().sendData(data);
        });
    }
}
