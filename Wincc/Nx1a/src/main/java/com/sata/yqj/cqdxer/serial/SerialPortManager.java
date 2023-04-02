package com.sata.yqj.cqdxer.serial;

import com.sata.yqj.cqdxer.common.IniUtils;
import gnu.io.SerialPort;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author YQJ
 * @Description: 串口管理服务
 * @date 2020年9月4日 上午12:11:30
 */
public class SerialPortManager {
    private static final Logger log = Logger.getLogger(SerialPortManager.class.toString());
    // 当前连接的串口对象
    private SerialPort currentSerialBean;
    // 当前连接的串口端口
    private final static String initPort = IniUtils.Config.getConnect("SerialPort");
    public final static SimpleObjectProperty<String> curentPort = new SimpleObjectProperty(initPort);
    // 当前连接的串口波特率
    private final static Integer initBaudRate = IniUtils.Config.getConnect("SerialBaudRate",Integer.class);
    public final static SimpleObjectProperty<Integer> curentBaudRate = new SimpleObjectProperty(initBaudRate);
    // 数据加密方式
    public final static SimpleBooleanProperty hex = new SimpleBooleanProperty(true);
    // 数据监听通知
    public final static SimpleObjectProperty<String> dataChange = new SimpleObjectProperty(null);
    // 串口状态通知
    public final static SimpleBooleanProperty connectStatus = new SimpleBooleanProperty(false);
    // 自动连接
    private final static Boolean initAutoConnect = IniUtils.Config.getConnect("AutoConnect",Boolean.class);
    public final static SimpleObjectProperty<Boolean> autoConnect = new SimpleObjectProperty(initAutoConnect);
    // 系统串口波特率列表
    public final static List<Integer> baudRateList = Arrays.asList(new Integer[]{300, 600, 1200, 2400, 4800, 9600, 19200, 38400, 43000, 56000, 57600, 115200});
    // 系统端口列表
    public final static List<String> portList = SerialPortUtils.findPorts();

    public void setCurrentSerialBean(SerialPort currentSerialBean) {
        this.currentSerialBean = currentSerialBean;
        connectStatus.set(ObjectUtils.isNotEmpty(currentSerialBean));
    }

    /**
     * Description: 构造私有化
     */
    private SerialPortManager() {
        autoConnect();
    }

    static class Holder {
        private static final SerialPortManager instance = new SerialPortManager();
    }

    /**
     * @return SerialPortManager
     * @Description: 统一对象创建
     * @date 2020年9月4日 上午12:13:36
     */
    public static SerialPortManager getManager() {
        return Holder.instance;
    }

    private void autoConnect(){
        if(BooleanUtils.isTrue(autoConnect.getValue())){
            try {
                open();
            } catch (Exception e){
                log.info("auto connecting is failed");
            }
        }
    }

    public void close(){
        SerialPortUtils.closePort(currentSerialBean);
        setCurrentSerialBean(null);
    }

    public void open() {
        if (ObjectUtils.isEmpty(curentPort.getValue()) || ObjectUtils.isEmpty(curentBaudRate.getValue())) {
            log.info("serialPort portName or baudrate is empty.connecting failed");
            return;
        }
        this.close();
        setCurrentSerialBean(SerialPortUtils.openPort(curentPort.getValue(), curentBaudRate.getValue()));
        SerialPortUtils.addListener(currentSerialBean);
    }

    public void sendData(String data) {
        sendData(data.split(" "));
    }

    public void sendData(String[] data) {
        if (hex.getValue()) {
            SerialPortUtils.send(currentSerialBean, hexToByte(data));
        } else {
            SerialPortUtils.send(currentSerialBean, StringUtils.join(data, " ").getBytes());
        }
    }

    public void readData() {
        dataChangeNotice(SerialPortUtils.read(currentSerialBean));
    }

    private void dataChangeNotice(byte[] datas) {
        if (hex.getValue()) {
            dataChange.set(byteToHex(datas));
        } else {
            dataChange.set(new String(datas).toUpperCase(Locale.ROOT));
        }
    }

    private String byteToHex(byte[] bytes) {
        List<String> hexList = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            hexList.add(Integer.toHexString(0xFF & bytes[i]));
        }
        return StringUtils.join(hexList, " ").toUpperCase(Locale.ROOT);
    }

    private byte[] hexToByte(String[] hexs) {
        byte[] ret = new byte[hexs.length];
        for (int i = 0; i < hexs.length; i++) {
            ret[i] = (byte) Integer.parseInt(hexs[i], 16);
        }
        return ret;
    }
}
