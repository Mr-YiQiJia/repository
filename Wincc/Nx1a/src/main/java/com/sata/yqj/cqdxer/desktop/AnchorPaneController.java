package com.sata.yqj.cqdxer.desktop;

import java.awt.Robot;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.sata.yqj.cqdxer.communication.DataAvailableListener;
import com.sata.yqj.cqdxer.communication.SerialPortManager;
import com.sata.yqj.cqdxer.communication.SystemUtils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @Description: 界面控制器
 * @author YQJ
 * @date 2020年9月4日 上午12:22:37
 */
public class AnchorPaneController implements Initializable {
    //键盘事件，存储值
    public Set<KeyCode> pressedKeys = new HashSet<>();
    // 统一按钮内容,按钮ID
    private static final String BTN_ANT = "ANT";
    private Integer BTN_ANT_COUNT;
    public Double stage_width;
    public Double stage_height;
    public Double stage_x;
    public Double stage_y;
    private SerialPortManager serialPortManager = SerialPortManager.getSerialPortManager();
    @FXML
    private ComboBox<String> port;
    @FXML
    private ComboBox<Integer> baudRate;
    @FXML
    private ComboBox<String> model;
    @FXML
    private ComboBox<String> menu;
    @FXML
    private HBox top;
    @FXML
    public FlowPane leftMenu;
    @FXML
    private Circle portStatus;
    @FXML
    public Text contontLab1;
    @FXML
    public Label contontLab2;
    @FXML
    public Label contontLab3;
    @FXML
    private Text dropDown;
    @FXML
    private JFXNodesList hoverNode;
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initTop();
        initLeft();
        initConten();
        cacheRead();
        connectStatusListener();
    }
    
    private void initLeft() {
        leftMenu.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            ObservableList<Node> children = leftMenu.getChildren();
            for (int i = 0; i < children.size(); i++) {
                if(event.getDeltaY() < 0) {
                    Button btn = (Button) children.get(i);
                    btn.setFont(new Font(btn.getFont().getSize()-1));
                }else {
                    Button btn = (Button) children.get(i);
                    btn.setFont(new Font(btn.getFont().getSize()+1));
                }
            }
            event.consume();
        });
        
        ObservableList<Node> children = leftMenu.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Button btn = (Button) children.get(i);
            btn.setId(BTN_ANT + "_" + (i+1));
            btn.setText(BTN_ANT + (i+1));
            btn.setOnAction(event -> {
                assemblyDataAndSend(btn,3);
            });
        }
    }

    /**
     * @Description: 组装发送数据
     * @param btn
     * @return String[]    
     * @date 2020年10月18日 下午7:35:58
     */
    private void assemblyDataAndSend(Button btn,int changeIndex) {
        List<String> data = new ArrayList<>();
        // 帧头
        data.add("FA");
        data.add("F"+changeIndex);
        //内容
        String modelValue = model.getSelectionModel().getSelectedItem();
        if (StringUtils.isBlank(modelValue)) {
            data.add("99");
        }else {
            data.add(ModelTable.getContent(modelValue));
        }
        String menuValue = menu.getSelectionModel().getSelectedItem();
        if (StringUtils.isBlank(menuValue)) {
            data.add("99");
        }else {
            data.add(MenuTable.getContent(menuValue));
        }
/// 需要弹框提醒    
        if(!MenuTable._MNL.getMenu().equals(menuValue) && changeIndex == 3) {
            Optional<ButtonType> result = Alter.popup("是否发送数据");
            if (result.get() == ButtonType.CANCEL){
                return;
            }
        }
        Integer index = (null == btn ? 0 :Integer.valueOf(btn.getId().split("_")[1]));
        data.add(AntSendTable.getContent(index));
        JFXButton btnJFX = (JFXButton) hoverNode.getChildren().get(0);
        data.add(SwitchTable.getContent(btnJFX.getText()));
        // 帧尾
        data.add("FB");
        byte[] hexStringToBytes = SystemUtils.hexStringToBytes(data.toArray(new String[data.size()]));
        serialPortManager.sendData(hexStringToBytes);
    }
    
    private void initConten() {
        //设置悬浮按钮键
        JFXButton button = new JFXButton(SwitchTable._2.getSwitchBtn());
        button.getStyleClass().add("hover-button-style");
        button.setOnMouseClicked(event -> {
            JFXButton btn = (JFXButton) event.getSource();
            if(SwitchTable._1.getSwitchBtn().equals(btn.getText())) {
                btn.setText(SwitchTable._2.getSwitchBtn());
                btn.setStyle("-fx-background-color: rgb(255,0,0);");
                contontLab1.setStyle("-fx-fill : rgb(125,124,132)");
                contontLab2.setStyle("-fx-text-fill : rgb(125,124,132)");
                contontLab3.setStyle("-fx-text-fill : rgb(125,124,132)");
            }else {
                btn.setText(SwitchTable._1.getSwitchBtn());
                btn.setStyle("-fx-background-color: rgb(0,255,0);");
                contontLab1.setStyle("-fx-fill : GREEN");
                contontLab2.setStyle("-fx-text-fill : rgb(255,198,23)");
                contontLab3.setStyle("-fx-text-fill : rgb(255,198,23)");
            }
            assemblyDataAndSend(null,4);
        });
        hoverNode.addAnimatedNode(button);
        
        //字体缩放(ctrl+滚动)
        contontLab1.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            if(event.getDeltaY() < 0) {
                contontLab1.setFont(new Font(contontLab1.getFont().getName(),contontLab1.getFont().getSize()-1));
            }else {
                contontLab1.setFont(new Font(contontLab1.getFont().getName(),contontLab1.getFont().getSize()+1));
            }
            event.consume();
        });
        
        contontLab2.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            if(event.getDeltaY() < 0) {
                contontLab2.setFont(new Font(contontLab2.getFont().getName(),contontLab2.getFont().getSize()-1));
            }else {
                contontLab2.setFont(new Font(contontLab2.getFont().getName(),contontLab2.getFont().getSize()+1));
            }
            event.consume();
        });
        contontLab3.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            if(event.getDeltaY() < 0) {
                contontLab3.setFont(new Font(contontLab3.getFont().getName(),contontLab3.getFont().getSize()-1));
            }else {
                contontLab3.setFont(new Font(contontLab3.getFont().getName(),contontLab3.getFont().getSize()+1));
            }
            event.consume();
        });
    }

    private void initTop() {
        initPort();
        initbaudRate();
        model.getItems().addAll(ModelTable.getContents());
        model.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (null != newValue) {
                    Integer dispalyNum = ModelTable.getDispalyCount(newValue.toString());
                    ObservableList<Node> children = leftMenu.getChildren();
                    for (int i = 0; i < children.size(); i++) {
                        Button btn = (Button) children.get(i);
                        if(i > dispalyNum-1) {
                            btn.setVisible(false);
                            btn.setManaged(false);
                        }else {
                            btn.setVisible(true);
                            btn.setManaged(true);
                        }
                    }
                    assemblyDataAndSend(null,1);
                }
            }
            
        });
        menu.getItems().addAll(MenuTable.getContents());
        menu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (null != newValue) {
                    assemblyDataAndSend(null,2);
                }
            }
        });
        
        //隐藏top属性框,设置下拉显示
        dropDown.setOnMouseClicked(event -> {
            if(top.isVisible()) {
                top.setVisible(false);
                top.setManaged(false);
                dropDown.setRotate(90);
            }else {
                top.setVisible(true);
                top.setManaged(true);
                dropDown.setRotate(-90);
            }
            event.consume();
        });
    }
    
    /**
     * 
     * @Description: 初始化下拉框(port)
     * @return void
     * @date 2020年8月30日 下午8:21:30
     */
    private void initPort() {
        port.setItems(FXCollections.observableArrayList(serialPortManager.getAvailablePort()));
        port.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if(null == port.getValue() || null == baudRate.getValue()) {
                    return;
                }
                serialPortConnection((String)newValue, baudRate.getValue());
            }
            
        });
    }

    /**
     * 
     * @Description: 初始化下拉框(baudRate)
     * @return void
     * @date 2020年8月30日 下午8:21:30
     */
    private void initbaudRate() {
        Integer[] baudRateList = new Integer[] {300,600,1200,2400,4800,9600,19200,38400,43000,56000,57600,115200};
        Arrays.sort(baudRateList);
        baudRate.getItems().addAll(baudRateList);
        baudRate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if(null == port.getValue() || null == baudRate.getValue()) {
                    return;
                }
                serialPortConnection(port.getValue(), (Integer) newValue);
            }
        });
    }

    /**
     * @Description: 连接串口
     * @param currentPort
     * @param baudRate   
     * @return void    
     * @date 2020年10月18日 下午8:04:42
     */
    private void serialPortConnection(String currentPort, Integer baudRate){
        if(null == currentPort || null == baudRate) {
            return;
        }
        serialPortManager.portCloseAndOpen(currentPort, baudRate, getDataAvailableListener());
        displayClean();
    }
    
    /**
     * @Description: 获取监听，设置界面变化
     * @return DataAvailableListener    
     * @date 2020年10月18日 下午8:04:04
     */
    private DataAvailableListener getDataAvailableListener() {
        DataAvailableListener dataAvailableListener = new DataAvailableListener() {
            @Override
            public void dataAvailable() {
                String[] content = serialPortManager.readData();
                Scene scene = leftMenu.getScene();
                for (int i = 2; i < content.length-1; i++) {
                    switch (i) {
                    case 2:
                        String modelValue = ModelTable.getModelValue(content[i]);
                        Platform.runLater(()->{
                            model.getSelectionModel().select(modelValue);
                        });
                        break;
                    case 3:
                        String menuValue = MenuTable.getMenuValue(content[i]);
                        Platform.runLater(()->{
                            menu.getSelectionModel().select(menuValue);
                        });
                        break;
                    case 12:
                        String frequencyValue = FrequencyTable.getFrequencyValue(content[i]);
                        String bandValue = FrequencyTable.getBandValue(content[i]);
                        Platform.runLater(()->{
                            contontLab2.setText(bandValue);
                            contontLab3.setText(frequencyValue);
                        });
                        break;
                    case 13:
                        String switchValue = SwitchTable.getSwitchValue(content[i]);
                        Platform.runLater(()->{
                            JFXButton btnJFX = (JFXButton) hoverNode.getChildren().get(0);
                            if(SwitchTable._1.getSwitchBtn().equals(switchValue)) {
                                btnJFX.setText(SwitchTable._1.getSwitchBtn());
                                btnJFX.setStyle("-fx-background-color: rgb(0,255,0);");
                                contontLab1.setStyle("-fx-fill : GREEN");
                                contontLab2.setStyle("-fx-text-fill : rgb(255,198,23)");
                                contontLab3.setStyle("-fx-text-fill : rgb(255,198,23)");
                            }else {
                                btnJFX.setText(SwitchTable._2.getSwitchBtn());
                                btnJFX.setStyle("-fx-background-color: rgb(255,0,0);");
                                contontLab1.setStyle("-fx-fill : rgb(125,124,132)");
                                contontLab2.setStyle("-fx-text-fill : rgb(125,124,132)");
                                contontLab3.setStyle("-fx-text-fill : rgb(125,124,132)");
                            }
                        });
                        break;
                    default:
                        if (3<i && i<12) {
                            Button btn = (Button) scene.lookup("#" + BTN_ANT + "_" + (i - 3));
                            if(btn != null) {
                                String antColor = AntDispalyTable.getAntColor(content[i]);
                                btn.setStyle("-fx-background-color:"+antColor+";");
                            } 
                        }
                        break;
                    }
                }
            }
        };
        return dataAvailableListener;
    }
    
    /**
     * @Description: 显示清理   
     * @return void    
     * @date 2020年9月12日 下午5:54:22
     */
    private void displayClean() {
        if (null != leftMenu) {
            for (Node node : leftMenu.getChildren()) {
                node.setStyle("-fx-background-color:rgb(131,78,0);");
            }
        }
        model.getSelectionModel().clearSelection();
        menu.getSelectionModel().clearSelection();
        contontLab2.setText(FrequencyTable._0.getBand());
        contontLab3.setText(FrequencyTable._0.getFrequency());
    }
    
    /**
     * @Description: 动态刷新串口连接状态
     * @return void    
     * @date 2020年9月12日 下午5:27:36
     */
    private void connectStatusListener() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300), ev -> {
                    if(null == serialPortManager.getCurrentSerialPort()) {
                        portStatus.setFill(Color.RED);
                    }else {
                        portStatus.setFill(Color.SPRINGGREEN);
                    }
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        });
    }
    
    /**
     * @Description: 系统初始化读取缓存，设置默认值 
     * @return void    
     * @date 2020年9月12日 下午5:53:33
     */
    private void cacheRead() {
        Map<String, String> cacheRead = SystemUtils.cacheRead();
        if(null != cacheRead) {
            if(cacheRead.containsKey("port")) {
                String port = cacheRead.get("port");
                if(null != port) {
                    this.port.getSelectionModel().select(port);
                }
            }
            if(cacheRead.containsKey("baudRate")) {
                String baudRate = cacheRead.get("baudRate");
                if(null != baudRate) {
                    this.baudRate.getSelectionModel().select(Integer.valueOf(baudRate));
                }
            }
            if(cacheRead.containsKey("model")) {
                String model = cacheRead.get("model");
                if(null != model) {
                    this.model.getSelectionModel().select(model);
                }
            }
            if(cacheRead.containsKey("menu")) {
                String menu = cacheRead.get("menu");
                if(null != menu) {
                    this.menu.getSelectionModel().select(menu);
                }
            }
            if(cacheRead.containsKey("stage_width")) {
                String stage_width = cacheRead.get("stage_width");
                if(null != stage_width) {
                    this.stage_width = Double.valueOf(stage_width);
                }
            }
            if(cacheRead.containsKey("stage_height")) {
                String stage_height = cacheRead.get("stage_height");
                if(null != stage_height) {
                    this.stage_height = Double.valueOf(stage_height);
                }
            }
            if(cacheRead.containsKey("stage_x")) {
                String stage_x = cacheRead.get("stage_x");
                if(null != stage_x) {
                    this.stage_x = Double.valueOf(stage_x);
                }
            }
            if(cacheRead.containsKey("stage_y")) {
                String stage_y = cacheRead.get("stage_y");
                if(null != stage_y) {
                    this.stage_y = Double.valueOf(stage_y);
                }
            }
            if(cacheRead.containsKey("contontLab1_font_name") && cacheRead.containsKey("contontLab1_font_size")) {
                String contontLab1_font_name = cacheRead.get("contontLab1_font_name");
                String contontLab1_font_size = cacheRead.get("contontLab1_font_size");
                contontLab1.setFont(new Font(contontLab1_font_name,Double.valueOf(contontLab1_font_size)));
            }
            if(cacheRead.containsKey("contontLab2_font_name") && cacheRead.containsKey("contontLab2_font_size")) {
                String contontLab2_font_name = cacheRead.get("contontLab2_font_name");
                String contontLab2_font_size = cacheRead.get("contontLab2_font_size");
                contontLab2.setFont(new Font(contontLab2_font_name,Double.valueOf(contontLab2_font_size)));
            }
            if(cacheRead.containsKey("contontLab3_font_name") && cacheRead.containsKey("contontLab3_font_size")) {
                String contontLab3_font_name = cacheRead.get("contontLab3_font_name");
                String contontLab3_font_size = cacheRead.get("contontLab3_font_size");
                contontLab3.setFont(new Font(contontLab3_font_name,Double.valueOf(contontLab3_font_size)));
            }
        }
        if(null != this.stage_width && null != this.stage_height) {
            if(null == this.stage_x && null == this.stage_y) {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                this.stage_x = (screenBounds.getWidth() - stage_width) / 2; 
                this.stage_y = (screenBounds.getHeight() - stage_height) / 2; 
            }
        }
    }
    
    /**
     * @Description: 写出缓存数据   
     * @return void    
     * @date 2020年10月18日 下午8:03:40
     */
    public void cacheWrite() {
        Map<String,String> cacheWrite = new HashMap<>();
        String port = this.port.getValue();
        if(StringUtils.isNoneBlank(port)) {
            cacheWrite.put("port", port);
        }
        Integer baudRate = this.baudRate.getValue();
        if(null != baudRate) {
            cacheWrite.put("baudRate", baudRate.toString());
        }
        String model = this.model.getValue();
        if(StringUtils.isNoneBlank(model)) {
            cacheWrite.put("model", model);
        }
        String menu = this.menu.getValue();
        if(StringUtils.isNoneBlank(menu)) {
            cacheWrite.put("menu", menu);
        }
        if(null != contontLab1) {
            cacheWrite.put("contontLab1_font_name", contontLab1.getFont().getName());
            cacheWrite.put("contontLab1_font_size", String.valueOf(contontLab1.getFont().getSize()));
        }
        if(null != contontLab2) {
            cacheWrite.put("contontLab2_font_name", contontLab1.getFont().getName());
            cacheWrite.put("contontLab2_font_size", String.valueOf(contontLab1.getFont().getSize()));
        }
        if(null != contontLab3) {
            cacheWrite.put("contontLab3_font_name", contontLab1.getFont().getName());
            cacheWrite.put("contontLab3_font_size", String.valueOf(contontLab1.getFont().getSize()));
        }
        Stage stage = (Stage) this.port.getScene().getWindow();
        // 软件最小化(不缓存位置)
        if(!stage.isIconified()) {
            cacheWrite.put("stage_height", String.valueOf(stage.getHeight()));
            cacheWrite.put("stage_width", String.valueOf(stage.getWidth()));
            cacheWrite.put("stage_x", String.valueOf(stage.getX()));
            cacheWrite.put("stage_y", String.valueOf(stage.getY()));
        }
        SystemUtils.cacheWrite(cacheWrite);
    }
}

