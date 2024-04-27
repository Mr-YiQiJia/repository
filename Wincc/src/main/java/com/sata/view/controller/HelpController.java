package com.sata.view.controller;

import com.jayway.jsonpath.JsonPath;
import com.sata.common.BeanUtil;
import com.sata.common.I18N;
import com.sata.common.ini.Startup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelpController  implements Initializable {
    private final static Startup startup = BeanUtil.getBean(Startup.class);
    private ObservableList datas = FXCollections.observableArrayList(JsonPath.parse(I18N.getString("about.content.error.datas")).read("$", List.class));
    @FXML
    private TableView errorsTable;
    @FXML
    private TableColumn errorCode;
    @FXML
    private TableColumn errorDesc;
    @FXML
    private Text aboutFooter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutFooter.setText(Optional.ofNullable(startup.getFooter()).orElse(I18N.getString("about.footer")));
        errorCode.setCellValueFactory(new MapValueFactory<String>("code"));
        errorDesc.setCellValueFactory(new MapValueFactory<String>("description"));
        errorsTable.setItems(datas);
    }
}
