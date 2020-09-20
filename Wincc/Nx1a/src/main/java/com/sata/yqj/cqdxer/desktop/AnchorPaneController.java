package com.sata.yqj.cqdxer.desktop;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.sata.yqj.cqdxer.communication.DataAvailableListener;
import com.sata.yqj.cqdxer.communication.SerialPortManager;
import com.sata.yqj.cqdxer.communication.SerialPortUtils;
import com.sata.yqj.cqdxer.communication.SystemUtils;

import gnu.io.SerialPort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
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
    public Double stage_width = 900.0;
    public Double stage_height = 600.0;
    public Double stage_x;
    public Double stage_y;
    private SerialPortManager serialPortManager = SerialPortManager.getSerialPortManager();
    @FXML
    private ComboBox<String> port;
    @FXML
    private ComboBox<Integer> bps;
    @FXML
    private ComboBox<String> model;
    @FXML
    private ComboBox<String> menu;
    @FXML
    private VBox leftMenu;
    @FXML
    private Sphere status;
    @FXML
    private Label contontLab1;
    @FXML
    private Label contontLab2;
    @FXML
    private Label contontLab3;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initTop();
        initLeft();
        initConten();
        cacheRead();
        connectStatusListener();
    }
    
    private void initLeft() {
        ObservableList<Node> children = leftMenu.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Button btn = (Button) children.get(i);
            btn.setId(BTN_ANT + "_" + (i+1));
            btn.setText(BTN_ANT + (i+1));
            btn.styleProperty().addListener(new ChangeListener<Object>() {

                @Override
                public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                    // TODO Auto-generated method stub
                    System.out.println(1111);
                }
            });
        }
        /*int btn_amount = 8;
        for (int i = 1; i <= btn_amount; i++) {
            Button btn = new Button();
            btn.setId(BTN_ANT + "_" + i);
            btn.setText(BTN_ANT + i);
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            btn.setStyle(String.format("-fx-font-size: %dpx;", (int)(0.04 * primaryScreenBounds.getHeight())));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    serialPortManager.sendData("测试发送".getBytes());
                }
            });
        }*/
    }

    private void initConten() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        contontLab1.setFont(new Font(0.15 * primaryScreenBounds.getHeight()));
        contontLab1.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            if(event.getDeltaY() < 0) {
                contontLab1.setFont(new Font(contontLab1.getFont().getSize()-1));
            }else {
                contontLab1.setFont(new Font(contontLab1.getFont().getSize()+1));
            }
            event.consume();
        });
        contontLab2.setFont(new Font(0.1 * primaryScreenBounds.getHeight()));
        contontLab2.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            if(event.getDeltaY() < 0) {
                contontLab2.setFont(new Font(contontLab2.getFont().getSize()-1));
            }else {
                contontLab2.setFont(new Font(contontLab2.getFont().getSize()+1));
            }
            event.consume();
        });
        contontLab3.setFont(new Font(0.2 * primaryScreenBounds.getHeight()));
        contontLab3.setOnScroll(event -> {
            if(!this.pressedKeys.contains(KeyCode.CONTROL)) {
                return;
            }
            if(event.getDeltaY() < 0) {
                contontLab3.setFont(new Font(contontLab3.getFont().getSize()-1));
            }else {
                contontLab3.setFont(new Font(contontLab3.getFont().getSize()+1));
            }
            event.consume();
        });
    }

    private void initTop() {
        initPort();
        initBps();
        initModel();
        initMenu();
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
                if(null == port.getValue() || null == bps.getValue()) {
                    return;
                }
                serialPortConnection((String)newValue, bps.getValue());
            }
            
        });
    }

    /**
     * 
     * @Description: 初始化下拉框(bps)
     * @return void
     * @date 2020年8月30日 下午8:21:30
     */
    private void initBps() {
        Integer[] bpsList = new Integer[] {300,600,1200,2400,4800,9600,19200,38400,43000,56000,57600,115200};
        Arrays.sort(bpsList);
        bps.getItems().addAll(bpsList);
        bps.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if(null == port.getValue() || null == bps.getValue()) {
                    return;
                }
                serialPortConnection(port.getValue(), (Integer) newValue);
            }
        });
    }

    /**
     * 
     * @Description: 初始化下拉框(model)
     * @return void
     * @date 2020年8月30日 下午8:21:30
     */
    private void initModel() {
        model.getItems().addAll("1x4","1x6","1x8");
    }

    private void initMenu() {
        menu.getItems().addAll("BCD", "232", "CIV", "MNL", "RESET");
    }

    private void serialPortConnection(String currentPort, Integer bps){
        if(null == currentPort || null == bps) {
            return;
        }
        serialPortManager.portCloseAndOpen(currentPort, bps, getDataAvailableListener());
        btnDefaultCss();
    }
    
    private DataAvailableListener getDataAvailableListener() {
        DataAvailableListener dataAvailableListener = new DataAvailableListener() {
            @Override
            public void dataAvailable() {
                String content = serialPortManager.readData();
                Scene scene = leftMenu.getScene();
                Button btn = (Button) scene.lookup("#" + BTN_ANT + "_" + content);
                if(btn != null) {
                    btnDefaultCss();
                    btn.getStyleClass().add("btn-background-color-green");
                }
            }
        };
        return dataAvailableListener;
    }
    
    /**
     * @Description: 按钮css删除，恢复默认css   
     * @return void    
     * @date 2020年9月12日 下午5:54:22
     */
    private void btnDefaultCss() {
        if (null == leftMenu) {
            return;
        }
        for (Node node : leftMenu.getChildren()) {
            node.getStyleClass().removeAll("btn-background-color-green","btn-background-color-red");
        }
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
                        status.setMaterial(new PhongMaterial(Color.RED));
                    }else {
                        status.setMaterial(new PhongMaterial(Color.SPRINGGREEN));
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
            if(cacheRead.containsKey("bps")) {
                String bps = cacheRead.get("bps");
                if(null != bps) {
                    this.bps.getSelectionModel().select(Integer.valueOf(bps));
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
        }
        
        if(null == this.stage_x && null == this.stage_y) {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            this.stage_x = (screenBounds.getWidth() - stage_width) / 2; 
            this.stage_y = (screenBounds.getHeight() - stage_height) / 2; 
        }
    }
    
    public void cacheWrite() {
        Map<String,String> cacheWrite = new HashMap<>();
        String port = this.port.getValue();
        if(StringUtils.isNoneBlank(port)) {
            cacheWrite.put("port", port);
        }
        Integer bps = this.bps.getValue();
        if(null != bps) {
            cacheWrite.put("bps", bps.toString());
        }
        String model = this.model.getValue();
        if(StringUtils.isNoneBlank(model)) {
            cacheWrite.put("model", model);
        }
        String menu = this.menu.getValue();
        if(StringUtils.isNoneBlank(menu)) {
            cacheWrite.put("menu", menu);
        }
        Stage stage = (Stage) this.port.getScene().getWindow();
        cacheWrite.put("stage_height", String.valueOf(stage.getHeight()));
        cacheWrite.put("stage_width", String.valueOf(stage.getWidth()));
        cacheWrite.put("stage_x", String.valueOf(stage.getX()));
        cacheWrite.put("stage_y", String.valueOf(stage.getY()));
        SystemUtils.cacheWrite(cacheWrite);
    }
}
