package com.sata.yqj.cqdxer.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.CopyOnWriteArrayList;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
/**
 * 
 * @Description: 串口通信管理
 * @author YQJ
 * @date 2020年8月24日 下午11:00:24
 */
public class SerialPortUtils{
    private static SerialPortUtils serialPortUtils = null;

    static {
        // 在该类被ClassLoader加载时就初始化一个SerialTool对象
        if (serialPortUtils == null) {
            serialPortUtils = new SerialPortUtils();
        }
    }

    // 私有化SerialTool类的构造方法，不允许其他类生成SerialTool对象
    private SerialPortUtils() {
    }

    public static SerialPortUtils getSerialPortUtil() {
        if (serialPortUtils == null) {
            serialPortUtils = new SerialPortUtils();
        }
        return serialPortUtils;
    }

    /**
     * @Description: 查找所有可用端口
     * @return ArrayList<String>
     * @date 2020年9月4日 上午12:15:57
     */
    public static final List<String> findPorts() {
        // 获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        List<String> portNameList = new CopyOnWriteArrayList<String>();
        // 将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }
        return portNameList;
    }

    /**
     * @Description: 打开串口
     * @param portName
     * @param baudrate
     * @return SerialPort
     * @date 2020年9月4日 上午12:16:23
     */
    public static final SerialPort openPort(String portName, int baudrate) {
        try {
            // 通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            // 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);
            // 判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                // 设置一下串口的波特率等参数
                // 数据位：8
                // 停止位：1
                // 校验位：None
                serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                return serialPort;
            }
        } catch (Exception e) {
            //throw new RuntimeException("SerialPort not found", e);
        } 
        return null;
    }
    /**
     * @Description: 关闭串口
     * @param serialPort
     * @return void
     * @date 2020年9月4日 上午12:16:45
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
        }
    }

    /**
     * @Description: 向串口发送数据
     * @param serialPort
     * @param order
     * @throws IOException
     * @return void
     * @date 2020年9月4日 上午12:16:59
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException("SerialPort failed to send data", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                throw new RuntimeException("SerialPort failed to send closed", e);
            }
        }
    }

    /**
     * 
     * @Description: 从串口读取数据
     * @param serialPort
     * @return byte[]
     * @date 2020年9月4日 上午12:18:11
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = null;

        try {
            in = serialPort.getInputStream();
            int bufflenth = in.available(); // 获取buffer里的数据长度

            while (bufflenth != 0) {
                bytes = new byte[bufflenth]; // 初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (IOException e) {
            throw new RuntimeException("SerialPort failed to read data", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                throw new RuntimeException("SerialPort failed to read data [io closed failed]", e);
            }
        }
        return bytes;
    }

    /**
     * @Description: 添加监听器
     * @param serialPort
     * @param listener
     * @return void
     * @date 2020年9月4日 上午12:18:28
     */
    public static void addListener(SerialPort serialPort, DataAvailableListener listener) {
        try {
            // 给串口添加监听器
            serialPort.addEventListener(new SerialPortListener(listener));
            // 设置当有数据到达时唤醒监听接收线程
            serialPort.notifyOnDataAvailable(true);
            // 设置当通信中断时唤醒中断线程
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            throw new RuntimeException("SerialPort too many listener", e);
        }
    }
    public static void main(String[] args) {
        openPort("COM1", 115200);
    }
}
