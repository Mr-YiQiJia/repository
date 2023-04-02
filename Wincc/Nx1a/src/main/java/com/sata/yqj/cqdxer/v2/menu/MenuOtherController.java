package com.sata.yqj.cqdxer.v2.menu;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.sata.yqj.cqdxer.common.JsonReaderUtils;
import com.sata.yqj.cqdxer.mapping.MappingVo;
import com.sata.yqj.cqdxer.serial.SerialPortManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MenuOtherController implements Initializable {
    public final static String OTHER_MAPPING = JsonReaderUtils.read("mapping/other.json");
    public final static String[] OTHER_SEND = new String[]{"FA", "F4", "00", "00", "00", "00", "00", "00", "FB"};
    private Configuration cfg = Configuration.builder().mappingProvider(new JacksonMappingProvider(JsonReaderUtils.MAPPER)).build();
    private DocumentContext parse = JsonPath.using(cfg).parse(OTHER_MAPPING);
    @FXML
    private AnchorPane tabOther;
    @FXML
    private Button fieldAnt4;
    @FXML
    private Button fieldAnt6;
    @FXML
    private Button fieldAnt8;
    @FXML
    private Button fieldAutoOff;
    @FXML
    private Button fieldTestOff;
    @FXML
    private Button fieldReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initListener();
        initDataListener();
    }

    private void initDataListener() {
        SerialPortManager.getManager().dataChange.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                settingStyleByData(convertInstructToUi(newValue));
            }
        });
    }

    private void settingStyleByData(Map<String, MappingVo> mappingVoMap) {
        Platform.runLater(() -> {
            String id = tabOther.getId();
            if (mappingVoMap.isEmpty() || mappingVoMap.containsKey(id)) {
                settingHardware(mappingVoMap);
                settingTestOff(mappingVoMap);
                settingAutoOff(mappingVoMap);
                settingReset(mappingVoMap);
            }
        });
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
            List<MappingVo> read = parse.read("$..[?(@.instructIndex == '" + i + "' && @.instructCode == '" + instruct + "')]", tr);
            Map<String, MappingVo> collect = read.stream().collect(Collectors.toMap(MappingVo::getId, vo -> vo));
            mapping.putAll(collect);
        }
        return mapping;
    }

    public void initListener() {
        fieldAnt4.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt4.getId() + "')].instructCode");
            String[] data = OTHER_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt6.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt6.getId() + "')].instructCode");
            String[] data = OTHER_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldAnt8.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldAnt8.getId() + "')].instructCode");
            String[] data = OTHER_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldTestOff.setOnAction(event -> {
            String[] data = OTHER_SEND.clone();
            data[4] = "1";
            SerialPortManager.getManager().sendData(data);
        });
        fieldAutoOff.setOnAction(event -> {
            String[] data = OTHER_SEND.clone();
            data[5] = "1";
            SerialPortManager.getManager().sendData(data);
        });
        fieldReset.setOnAction(event -> {
            String[] data = OTHER_SEND.clone();
            data[6] = "1";
            SerialPortManager.getManager().sendData(data);
        });
    }

    public void settingHardware(Map<String, MappingVo> mappingVoMap) {
        if (mappingVoMap.containsKey(fieldAnt4.getId())) {
            fieldAnt4.getStyleClass().add("color-orange");
        } else {
            fieldAnt4.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldAnt6.getId())) {
            fieldAnt6.getStyleClass().add("color-orange");
        } else {
            fieldAnt6.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldAnt8.getId())) {
            fieldAnt8.getStyleClass().add("color-orange");
        } else {
            fieldAnt8.getStyleClass().removeAll("color-orange");
        }
    }

    public void settingTestOff(Map<String, MappingVo> mappingVoMap) {
        String id = fieldTestOff.getId();
        fieldTestOff.getStyleClass().removeAll("color-orange");
        if (mappingVoMap.containsKey(id)) {
            String uiText = mappingVoMap.get(id).getUiText();
            fieldTestOff.setText(uiText);
            if("Test On".equalsIgnoreCase(uiText)){
                fieldTestOff.getStyleClass().add("color-orange");
            }
        } else {
            fieldTestOff.setText("Test Off");
        }
    }

    public void settingAutoOff(Map<String, MappingVo> mappingVoMap) {
        String id = fieldAutoOff.getId();
        fieldAutoOff.getStyleClass().removeAll("color-orange");
        if (mappingVoMap.containsKey(id)) {
            String uiText = mappingVoMap.get(id).getUiText();
            fieldAutoOff.setText(uiText);
            if("Auto On".equalsIgnoreCase(uiText)){
                fieldAutoOff.getStyleClass().add("color-orange");
            }
        } else {
            fieldAutoOff.setText("Auto Off");
        }
    }

    public void settingReset(Map<String, MappingVo> mappingVoMap) {
        String id = fieldReset.getId();
        fieldReset.getStyleClass().removeAll("color-orange");
        if (mappingVoMap.containsKey(id)) {
            fieldReset.getStyleClass().add("color-orange");
        }
    }
}
