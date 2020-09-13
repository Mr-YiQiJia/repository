package com.sata.yqj.cqdxer.communication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnu.io.SerialPort;

/**
 * @Description: 串口管理服务
 * @author YQJ
 * @date 2020年9月4日 上午12:11:30
 */
public class SerialPortManager {
    // 当前连接的串口
    private SerialPort currentSerialPort;
    private String currentPortName;
    private Integer currentBps;
    private static SerialPortManager serialPortManager;
    
    /**
     * Description: 构造私有化
     */
    private SerialPortManager() {
    }

    /**
     * @Description: 统一对象创建
     * @return SerialPortManager
     * @date 2020年9月4日 上午12:13:36
     */
    public static SerialPortManager getSerialPortManager() {
        if (serialPortManager == null) {
            serialPortManager = new SerialPortManager();
        }
        return serialPortManager;
    }

    public SerialPort getCurrentSerialPort() {
        return currentSerialPort;
    }

    public void setCurrentSerialPort(SerialPort currentSerialPort) {
        this.currentSerialPort = currentSerialPort;
    }
    
    public String getCurrentPortName() {
        return currentPortName;
    }

    public void setCurrentPortName(String currentPortName) {
        this.currentPortName = currentPortName;
    }

    public Integer getCurrentBps() {
        return currentBps;
    }

    public void setCurrentBps(Integer currentBps) {
        this.currentBps = currentBps;
    }

    /**
     * 
     * @Description: 串口关闭和连接
     * @param portName
     * @param baudrate
     * @param listener
     * @return SerialPort
     * @throws Exception 
     * @date 2020年9月4日 上午12:14:49
     */
    public SerialPort portCloseAndOpen(String portName, int baudrate, DataAvailableListener listener){
        if (null != this.currentSerialPort) {
            SerialPortUtils.closePort(this.currentSerialPort);
        }
        this.currentSerialPort = SerialPortUtils.openPort(portName, baudrate);
        this.currentPortName = portName;
        this.currentBps = baudrate;
        if (null != listener && null != this.currentSerialPort) {
            SerialPortUtils.addListener(currentSerialPort, listener);
        }
        return this.currentSerialPort;
    }
    
    public void sendData(byte[] data){
        if (null != this.currentSerialPort) {
            SerialPortUtils.sendToPort(currentSerialPort, data);
        }
    }
    
    public List<String> getAvailablePort(){
        return SerialPortUtils.findPorts();
    }
    
    public void setCache() {
        if(null == this.currentPortName || null == currentBps) {
            return ;
        }
        Map<String,String> cacheWrite = new HashMap<>();
        cacheWrite.put("currentPortName", currentPortName);
        cacheWrite.put("currentBps", currentBps.toString());
        SystemUtils.cacheWrite(cacheWrite);
    }
}
