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

public class MenuCatController implements Initializable {
    public final static String CAT_MAPPING = JsonReaderUtils.read("mapping/cat.json");
    public final static String[] CAT_SEND = new String[]{"FA", "F3", "00", "00", "00", "FB"};
    private Configuration cfg = Configuration.builder().mappingProvider(new JacksonMappingProvider(JsonReaderUtils.MAPPER)).build();
    private DocumentContext parse = JsonPath.using(cfg).parse(CAT_MAPPING);
    @FXML
    private AnchorPane tabCat;
    @FXML
    private Button fieldFlex;
    @FXML
    private Button fieldYaesu;
    @FXML
    private Button fieldIcom;
    @FXML
    private Button fieldElecraft;
    @FXML
    private Button fieldKenwood;
    @FXML
    private Button fieldBData;
    @FXML
    private Button fieldManual;

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
            String id = tabCat.getId();
            if (mappingVoMap.isEmpty() || mappingVoMap.containsKey(id)) {
                settingCat(mappingVoMap);
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
        fieldFlex.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldFlex.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldYaesu.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldYaesu.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldIcom.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldIcom.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldElecraft.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldElecraft.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldKenwood.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldKenwood.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldBData.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldBData.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
        fieldManual.setOnAction(event -> {
            List<String> code = parse.read("$..[?(@.id == '" + fieldManual.getId() + "')].instructCode");
            String[] data = CAT_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            SerialPortManager.getManager().sendData(data);
        });
    }

    public void settingCat(Map<String, MappingVo> mappingVoMap) {
        if (mappingVoMap.containsKey(fieldFlex.getId())) {
            fieldFlex.getStyleClass().add("color-orange");
        } else {
            fieldFlex.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldYaesu.getId())) {
            fieldYaesu.getStyleClass().add("color-orange");
        } else {
            fieldYaesu.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldIcom.getId())) {
            fieldIcom.getStyleClass().add("color-orange");
        } else {
            fieldIcom.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldElecraft.getId())) {
            fieldElecraft.getStyleClass().add("color-orange");
        } else {
            fieldElecraft.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldKenwood.getId())) {
            fieldKenwood.getStyleClass().add("color-orange");
        } else {
            fieldKenwood.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldBData.getId())) {
            fieldBData.getStyleClass().add("color-orange");
        } else {
            fieldBData.getStyleClass().removeAll("color-orange");
        }

        if (mappingVoMap.containsKey(fieldManual.getId())) {
            fieldManual.getStyleClass().add("color-orange");
        } else {
            fieldManual.getStyleClass().removeAll("color-orange");
        }
    }
}
