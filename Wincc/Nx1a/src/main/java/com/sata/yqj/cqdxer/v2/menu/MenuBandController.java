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
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MenuBandController implements Initializable {
    public final static String BAND_MAPPING = JsonReaderUtils.read("mapping/band.json");
    public final static String[] BAND_SEND = new String[]{"FA", "F2", "00", "00", "00", "00", "FB"};
    private final static List<String> COMBO_LIST = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
    private Configuration cfg = Configuration.builder().mappingProvider(new JacksonMappingProvider(JsonReaderUtils.MAPPER)).build();
    private DocumentContext parse = JsonPath.using(cfg).parse(BAND_MAPPING);
    @FXML
    private AnchorPane tabBand;
    @FXML
    private Label fieldLabel160;
    @FXML
    private ComboBox<String> fieldCombo160;
    @FXML
    private Label fieldLabel80;
    @FXML
    private ComboBox<String> fieldCombo80;
    @FXML
    private Label fieldLabel60;
    @FXML
    private ComboBox<String> fieldCombo60;
    @FXML
    private Label fieldLabel40;
    @FXML
    private ComboBox<String> fieldCombo40;
    @FXML
    private Label fieldLabel30;
    @FXML
    private ComboBox<String> fieldCombo30;
    @FXML
    private Label fieldLabel20;
    @FXML
    private ComboBox<String> fieldCombo20;
    @FXML
    private Label fieldLabel17;
    @FXML
    private ComboBox<String> fieldCombo17;
    @FXML
    private Label fieldLabel15;
    @FXML
    private ComboBox<String> fieldCombo15;
    @FXML
    private Label fieldLabel12;
    @FXML
    private ComboBox<String> fieldCombo12;
    @FXML
    private Label fieldLabel10;
    @FXML
    private ComboBox<String> fieldCombo10;
    @FXML
    private Label fieldLabel6;
    @FXML
    private ComboBox<String> fieldCombo6;

    private final ChangeListener<String> listener160 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel160.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener80 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel80.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener60 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel60.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener40 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel40.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener30 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel30.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener20 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel20.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener17 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel17.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener15 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel15.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener12 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel12.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener10 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel10.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };
    private final ChangeListener<String> listener6 = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            List<String> code = parse.read("$..[?(@.id == '"+fieldLabel6.getId()+"')].instructCode");
            String[] data = BAND_SEND.clone();
            data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
            data[4] = newValue;
            SerialPortManager.getManager().sendData(data);
        }
    };

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
            String id = tabBand.getId();
            if (mappingVoMap.isEmpty() || mappingVoMap.containsKey(id)) {
                settingStoreLabel_160(mappingVoMap);
                settingStoreLabel_80(mappingVoMap);
                settingStoreLabel_60(mappingVoMap);
                settingStoreLabel_40(mappingVoMap);
                settingStoreLabel_30(mappingVoMap);
                settingStoreLabel_20(mappingVoMap);
                settingStoreLabel_17(mappingVoMap);
                settingStoreLabel_15(mappingVoMap);
                settingStoreLabel_12(mappingVoMap);
                settingStoreLabel_10(mappingVoMap);
                settingStoreLabel_6(mappingVoMap);
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
            if (i > 1) {
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

    private void initListener() {
        fieldCombo160.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo80.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo60.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo40.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo30.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo20.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo17.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo15.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo12.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo10.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
        fieldCombo6.setItems(FXCollections.observableArrayList(COMBO_LIST.stream().collect(Collectors.toList())));
    }

    public void settingStoreLabel_160(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo160.getId();
        fieldCombo160.getSelectionModel().selectedItemProperty().removeListener(listener160);
        fieldLabel160.getStyleClass().removeAll("color-orange");
        fieldCombo160.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo160.getSelectionModel().select(instructCode);
                fieldLabel160.getStyleClass().add("color-orange");
            }
        }
        fieldCombo160.getSelectionModel().selectedItemProperty().addListener(listener160);
    }

    public void settingStoreLabel_80(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo80.getId();
        fieldCombo80.getSelectionModel().selectedItemProperty().removeListener(listener80);
        fieldLabel80.getStyleClass().removeAll("color-orange");
        fieldCombo80.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo80.getSelectionModel().select(instructCode);
                fieldLabel80.getStyleClass().add("color-orange");
            }
        }
        fieldCombo80.getSelectionModel().selectedItemProperty().addListener(listener80);
    }

    public void settingStoreLabel_60(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo60.getId();
        fieldCombo60.getSelectionModel().selectedItemProperty().removeListener(listener60);
        fieldLabel60.getStyleClass().removeAll("color-orange");
        fieldCombo60.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo60.getSelectionModel().select(instructCode);
                fieldLabel60.getStyleClass().add("color-orange");
            }
        }
        fieldCombo60.getSelectionModel().selectedItemProperty().addListener(listener60);
    }

    public void settingStoreLabel_40(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo40.getId();
        fieldCombo40.getSelectionModel().selectedItemProperty().removeListener(listener40);
        fieldLabel40.getStyleClass().removeAll("color-orange");
        fieldCombo40.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo40.getSelectionModel().select(instructCode);
                fieldLabel40.getStyleClass().add("color-orange");
            }
        }
        fieldCombo40.getSelectionModel().selectedItemProperty().addListener(listener40);
    }

    public void settingStoreLabel_30(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo30.getId();
        fieldCombo30.getSelectionModel().selectedItemProperty().removeListener(listener30);
        fieldLabel30.getStyleClass().removeAll("color-orange");
        fieldCombo30.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo30.getSelectionModel().select(instructCode);
                fieldLabel30.getStyleClass().add("color-orange");
            }
        }
        fieldCombo30.getSelectionModel().selectedItemProperty().addListener(listener30);
    }

    public void settingStoreLabel_20(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo20.getId();
        fieldCombo20.getSelectionModel().selectedItemProperty().removeListener(listener20);
        fieldLabel20.getStyleClass().removeAll("color-orange");
        fieldCombo20.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo20.getSelectionModel().select(instructCode);
                fieldLabel20.getStyleClass().add("color-orange");
            }
        }
        fieldCombo20.getSelectionModel().selectedItemProperty().addListener(listener20);
    }

    public void settingStoreLabel_17(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo17.getId();
        fieldCombo17.getSelectionModel().selectedItemProperty().removeListener(listener17);
        fieldLabel17.getStyleClass().removeAll("color-orange");
        fieldCombo17.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo17.getSelectionModel().select(instructCode);
                fieldLabel17.getStyleClass().add("color-orange");
            }
        }
        fieldCombo17.getSelectionModel().selectedItemProperty().addListener(listener17);
    }

    public void settingStoreLabel_15(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo15.getId();
        fieldCombo15.getSelectionModel().selectedItemProperty().removeListener(listener15);
        fieldLabel15.getStyleClass().removeAll("color-orange");
        fieldCombo15.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo15.getSelectionModel().select(instructCode);
                fieldLabel15.getStyleClass().add("color-orange");
            }
        }
        fieldCombo15.getSelectionModel().selectedItemProperty().addListener(listener15);
    }


    public void settingStoreLabel_12(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo12.getId();
        fieldCombo12.getSelectionModel().selectedItemProperty().removeListener(listener12);
        fieldLabel12.getStyleClass().removeAll("color-orange");
        fieldCombo12.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo12.getSelectionModel().select(instructCode);
                fieldLabel12.getStyleClass().add("color-orange");
            }
        }
        fieldCombo12.getSelectionModel().selectedItemProperty().addListener(listener12);
    }

    public void settingStoreLabel_10(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo10.getId();
        fieldCombo10.getSelectionModel().selectedItemProperty().removeListener(listener10);
        fieldLabel10.getStyleClass().removeAll("color-orange");
        fieldCombo10.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo10.getSelectionModel().select(instructCode);
                fieldLabel10.getStyleClass().add("color-orange");
            }
        }
        fieldCombo10.getSelectionModel().selectedItemProperty().addListener(listener10);
    }

    public void settingStoreLabel_6(Map<String, MappingVo> mappingVoMap) {
        String id = fieldCombo6.getId();
        fieldCombo6.getSelectionModel().selectedItemProperty().removeListener(listener6);
        fieldLabel6.getStyleClass().removeAll("color-orange");
        fieldCombo6.getSelectionModel().clearSelection();
        if (mappingVoMap.containsKey(id)) {
            String instructCode = mappingVoMap.get(id).getInstructCode();
            if (COMBO_LIST.contains(instructCode)) {
                fieldCombo6.getSelectionModel().select(instructCode);
                fieldLabel6.getStyleClass().add("color-orange");
            }
        }
        fieldCombo6.getSelectionModel().selectedItemProperty().addListener(listener6);
    }

    @FXML
    private void fieldLabel160Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel160.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }

    @FXML
    private void fieldLabel80Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel80.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
    @FXML
    private void fieldLabel60Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel60.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
    @FXML
    private void fieldLabel40Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel40.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
    @FXML
    private void fieldLabel30Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel30.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }

    @FXML
    private void fieldLabel20Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel20.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }

    @FXML
    private void fieldLabel17Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel17.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }

    @FXML
    private void fieldLabel15Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel15.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
    @FXML
    private void fieldLabel12Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel12.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
    @FXML
    private void fieldLabel10Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel10.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
    @FXML
    private void fieldLabel6Clicked(){
        List<String> code = parse.read("$..[?(@.id == '"+fieldLabel6.getId()+"')].instructCode");
        String[] data = BAND_SEND.clone();
        data[3] = code.stream().findFirst().orElse(StringUtils.EMPTY);
        data[4] = "FF";
        SerialPortManager.getManager().sendData(data);
    }
}
