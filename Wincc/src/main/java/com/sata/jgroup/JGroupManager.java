package com.sata.jgroup;

import cn.hutool.core.net.NetUtil;
import com.sata.common.BeanUtil;
import com.sata.common.ini.Startup;
import lombok.SneakyThrows;
import org.jgroups.JChannel;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.net.InetAddress;
import java.util.Arrays;


/**
 * 程序运行实例管理器
 * @author yiqijia
 * @date 2024/5/6 15:05
 */
@Component
public class JGroupManager {
    private JChannel channel;
    // 单实例运行软件
    private boolean singleInstance = BeanUtil.getBean(Startup.class).getSingleInstance();

    public JGroupManager() {
        this.channel = connect();
    }

    @SneakyThrows
    private JChannel connect(){
        JChannel channel = new JChannel();
        channel.connect(getMachineCode());
        if(singleInstance){
            boolean singleMember = !(channel.getView().getMembers().size() > 1);
            if(!singleMember){
                channel.close();
                Runtime.getRuntime().exit(0);
            }
        }
        return channel;
    }

    @SneakyThrows
    private String getMachineCode(){
        String machineCode = String.join(",", Arrays.asList(System.getProperty("user.name"), NetUtil.getMacAddress(InetAddress.getLocalHost())));
        return DigestUtils.md5DigestAsHex(machineCode.getBytes());
    }
}
