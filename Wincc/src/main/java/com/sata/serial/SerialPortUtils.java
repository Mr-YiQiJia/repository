package com.sata.serial;

import com.sata.common.I18N;
import com.sata.exception.InValiDataException;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import lombok.SneakyThrows;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * @author YQJ
 * @Description: 串口通信管理
 * @date 2020年8月24日 下午11:00:24
 */
public class SerialPortUtils {
    private static final Logger log = Logger.getLogger(SerialPortUtils.class.toString());
    private final static String CONNECT_ERROR = I18N.getString("error.serial.connect");
    private final static String SEND_ERROR = I18N.getString("error.serial.send");
    private final static String READ_ERROR = I18N.getString("error.serial.read");

    /**
     * @return ArrayList<String>
     * @Description: 查找所有可用端口
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
     * @param portName
     * @param baudrate
     * @return SerialPort
     * @Description: 打开串口
     * @date 2020年9月4日 上午12:16:23
     */
    @SneakyThrows
    public static final SerialPort openPort(String portName, Integer baudrate) {
        // 通过端口名识别端口
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        // 打开端口，并给端口名字和一个timeout（打开操作的超时时间）
        CommPort commPort = portIdentifier.open(portName, 2000);
        // 判断是不是串口
        if (!(commPort instanceof SerialPort)) {
            return null;
        }
        SerialPort serialPort = (SerialPort) commPort;
        // 设置一下串口的波特率等参数
        // 数据位：8
        // 停止位：1
        // 校验位：None
        serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        return serialPort;
    }

    /**
     * @param serialPort
     * @return void
     * @Description: 关闭串口
     * @date 2020年9月4日 上午12:16:45
     */
    public static void closePort(SerialPort serialPort) {
        if (ObjectUtils.isEmpty(serialPort)) {
            log.info("serialPort is empty.closing nothing");
            return;
        }
        serialPort.close();
    }

    /**
     * @param serialPort
     * @return void
     * @Description: 向串口发送数据
     * @date 2020年9月4日 上午12:16:59
     */
    public static void send(SerialPort serialPort, byte[] data) {
        if (ObjectUtils.isEmpty(serialPort)) {
            log.info("serialPort is empty.sendind failed");
            throw new InValiDataException(SEND_ERROR);
        }
        try (OutputStream out = serialPort.getOutputStream();) {
            out.write(data);
            out.flush();
        } catch (IOException e) {
            throw new InValiDataException(SEND_ERROR, e);
        }
    }


    /**
     * @param serialPort
     * @return byte[]
     * @Description: 从串口读取数据
     * @date 2020年9月4日 上午12:18:11
     */
    public static byte[] read(SerialPort serialPort) {
        try (InputStream in = serialPort.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024 * 2];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new InValiDataException(READ_ERROR, e);
        }
    }

    /**
     * @param serialPort
     * @return void
     * @Description: 添加监听器
     * @date 2020年9月4日 上午12:18:28
     */
    @SneakyThrows
    public static void addListener(SerialPort serialPort) {
        if (ObjectUtils.isEmpty(serialPort)) {
            log.info("serialPort is empty.listener adding failed");
            throw new InValiDataException(CONNECT_ERROR);
        }
        // 给串口添加监听器
        serialPort.addEventListener(new SerialPortListener());
        // 设置当有数据到达时唤醒监听接收线程
        serialPort.notifyOnDataAvailable(true);
        // 设置当通信中断时唤醒中断线程
        serialPort.notifyOnBreakInterrupt(true);
    }
}
