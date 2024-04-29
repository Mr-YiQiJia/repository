package com.sata.serial;


import com.sata.common.BeanUtil;
import com.sata.common.ini.Connects;
import gnu.io.SerialPort;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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
@Component
public class SerialPortManager {
    private Connects CONNECTS = BeanUtil.getBean(Connects.class);
    // 当前连接的串口对象
    private SerialPort serialPort;
    // 当前连接的串口端口
    public SimpleObjectProperty<String> curentPort = new SimpleObjectProperty(CONNECTS.getSerialPort());
    // 当前连接的串口波特率
    public SimpleObjectProperty<Integer> curentBaudRate = new SimpleObjectProperty(CONNECTS.getSerialBaudRate());
    // 数据加密方式
    public SimpleBooleanProperty hex = new SimpleBooleanProperty(true);
    // 数据监听通知
    public SimpleObjectProperty<String> dataChange = new SimpleObjectProperty(null);
    // 串口状态通知
    public SimpleBooleanProperty connectStatus = new SimpleBooleanProperty(false);
    // 自动连接
    public SimpleObjectProperty<Boolean> autoConnect = new SimpleObjectProperty(CONNECTS.getAutoConnect());
    // 系统串口波特率列表
    public final static List<Integer> baudRateList = Arrays.asList(new Integer[]{300, 600, 1200, 2400, 4800, 9600, 19200, 38400, 43000, 56000, 57600, 115200});

    public SerialPortManager() {
        init();
        autoConnect();
    }

    private void init(){
        connectStatus.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                this.serialPort =  SerialPortUtils.openPort(curentPort.getValue(), curentBaudRate.getValue());
                SerialPortUtils.addListener(serialPort);
            } else {
                SerialPortUtils.closePort(serialPort);
            }
        });
    }

    private void autoConnect(){
        if(Boolean.TRUE.equals(autoConnect.getValue())){
            connectStatus.set(true);
        }
    }

    public void sendData(String data) {
        sendData(data.split(" "));
    }

    public void sendData(String[] data) {
        if (hex.getValue()) {
            SerialPortUtils.send(serialPort, hexToByte(data));
        } else {
            SerialPortUtils.send(serialPort, StringUtils.join(data, " ").getBytes());
        }
    }

    public void readData() {
        dataChangeNotice(SerialPortUtils.read(serialPort));
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
