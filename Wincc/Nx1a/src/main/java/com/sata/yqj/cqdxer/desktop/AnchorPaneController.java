package com.sata.yqj.cqdxer.desktop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

import com.sata.yqj.cqdxer.communication.DataAvailableListener;
import com.sata.yqj.cqdxer.communication.SerialPortManager;
import com.sata.yqj.cqdxer.communication.SerialPortUtils;
import com.sata.yqj.cqdxer.communication.SystemUtils;

import gnu.io.SerialPort;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
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
    // 统一按钮内容,按钮ID
    private static final String BTN_ANT = "ANT";
    public static final double STAGE_WIDTH = 900;
    public static final double STAGE_HEIGHT = 600;
    public Double STAGE_X;
    public Double STAGE_Y;
    private SerialPortManager serialPortManager = SerialPortManager.getSerialPortManager();
    // 当前变化的按钮
    private Button currentBtn;
    @FXML
    private ComboBox<String> port;
    @FXML
    private ComboBox<Integer> bps;
    @FXML
    private ComboBox<String> model;
    @FXML
    private ComboBox<String> menu;
    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;
    @FXML
    public Slider slider;
    @FXML
    public Sphere status;
    @FXML
    private TextField send;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initPort();
        initBps();
        initModel();
        initMenu();
        initANT();
        initSiler();
        readCache();
        flushAvailablePort();
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
            
            /*private void repalceContent(ComboBox<String> comboBox,Object oldValue, Object newValue) {
                if(null == oldValue || null == newValue) {
                    return;
                }
                // 交换数组内容
                int oldIndex = 0;
                int newIndex = 0;
                ObservableList<?> items = port.getItems();
                for (int i = 0; i < items.size(); i++) {
                    if(items.get(i).equals(oldValue)) {
                        oldIndex = i;
                    }
                    if(items.get(i).equals(newValue)) {
                        newIndex = i;
                    }
                }
                Object temp = oldValue;
                comboBox.getItems().set(oldIndex, (String) newValue);
                comboBox.getItems().set(newIndex, (String) temp);
                
                // 交换完成，从新内容排序
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(comboBox.getItems());
                    }
                });
            }*/
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
    
    private void initSiler() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double heightMax = new BigDecimal(primaryScreenBounds.getHeight()/STAGE_HEIGHT).setScale(1, RoundingMode.HALF_UP).doubleValue();
        double withMax = new BigDecimal(primaryScreenBounds.getWidth()/STAGE_WIDTH).setScale(1, RoundingMode.HALF_UP).doubleValue();
        if(heightMax < withMax) {
            slider.setMax(heightMax);
        }else {
            slider.setMax(withMax);
        }
        slider.setMin(1);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,Number oldValue, Number newValue) {
                    Stage stage = (Stage) slider.getScene().getWindow();
                    if(!stage.isMaximized()) {
                        stage.setWidth(STAGE_WIDTH*newValue.doubleValue());
                        stage.setHeight(STAGE_HEIGHT*newValue.doubleValue());
                        stage.setX(STAGE_X/newValue.doubleValue());
                        stage.setY(STAGE_Y/newValue.doubleValue());
                    }
            }
        });
    }

    private void initANT() {
        int btn_amount = 8;
        for (int i = 1; i <= btn_amount; i++) {
            Button btn = new Button();
            btn.setId(BTN_ANT + "_" + i);
            btn.setText(BTN_ANT + i);
            btn.setPrefWidth(140.0);
            btn.setFont(new Font(24.0));
            btn.setPadding(new Insets(10, 0, 10, 0));
            VBox.setMargin(btn, new Insets(10, 0, 10, 0));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    serialPortManager.sendData(send.getText().getBytes());
                }
            });
            
            if(i <= btn_amount/2) {
                leftBox.getChildren().add(btn);
            }else {
                rightBox.getChildren().add(btn);
            }
        }
    }

    private void serialPortConnection(String currentPort, Integer currentBps){
        if(null == currentPort || null == currentBps) {
            return;
        }
        DataAvailableListener dataAvailableListener = new DataAvailableListener() {
            @Override
            public void dataAvailable(SerialPort serialPort) {

                byte[] readFromPort = SerialPortUtils.readFromPort(serialPort);

                String content = new String(readFromPort);

                if ("1".equals(content)) {
                    btnDefaultCss();
                    Scene scene = leftBox.getScene();
                    currentBtn = (Button) scene.lookup("#" + BTN_ANT + "_" + 1);
                    currentBtn.getStyleClass().add("btn-background-color-green");
                }
                if ("2".equals(content)) {
                    btnDefaultCss();
                    Scene scene = leftBox.getScene();
                    currentBtn = (Button) scene.lookup("#" + BTN_ANT + "_" + 2);
                    currentBtn.getStyleClass().add("btn-background-color-green");
                }
                if ("3".equals(content)) {
                    btnDefaultCss();
                    Scene scene = leftBox.getScene();
                    currentBtn = (Button) scene.lookup("#" + BTN_ANT + "_" + 3);
                    currentBtn.getStyleClass().add("btn-background-color-green");
                }
            }
        };
        serialPortManager.portCloseAndOpen(currentPort, currentBps, dataAvailableListener);
        
        btnDefaultCss();
    }
    /**
     * @Description: 按钮css删除，恢复默认css   
     * @return void    
     * @date 2020年9月12日 下午5:54:22
     */
    private void btnDefaultCss() {
        if (null != currentBtn) {
            currentBtn.getStyleClass().removeAll("btn-background-color-green","btn-background-color-red");
        }
    }
    
    /**
     * @Description: 动态刷新串口连接状态
     * @return void    
     * @date 2020年9月12日 下午5:27:36
     */
    private void flushAvailablePort() {
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
    private void readCache() {
        Map<String, String> cacheRead = SystemUtils.cacheRead();
        String cachePortName = cacheRead.get("currentPortName");
        String cacheBps = cacheRead.get("currentBps");
        if(null == cachePortName || null == cacheBps) {
            return ;
        }
        port.getSelectionModel().select(cachePortName);
        bps.getSelectionModel().select(Integer.valueOf(cacheBps));
    }
}
