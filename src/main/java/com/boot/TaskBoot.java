package com.boot;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.ServerDao;

public class TaskBoot {

    @Autowired ServerDao serverDao;
    
    @PostConstruct
    public void init() {
        String serverIp = getServerIp();
        if("".equals(serverIp)) {
            System.out.println("获得服务器ip失败");
            return ;
        }
        int serverId = serverDao.queryServerId(serverIp);
        if(serverId == 0) {
            serverDao.save(serverIp, "8080");
        }
        start();
        return ;
    }
    
    public void start() {
        
    }

    private String getServerIp() {
        String serverIp = "";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces(); // 
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                if (!ni.isUp()) { // 网络接口是否已经开启并运行
                    continue;
                }
                Enumeration<InetAddress> iddrs = ni.getInetAddresses();
                while(iddrs.hasMoreElements()) {
                    InetAddress ip = iddrs.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1 && !(ip instanceof Inet6Address)) { // 不是本地地址，不是环回地址
                        serverIp = ip.getHostAddress();
                        break;
                    } else {
                        ip = null;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return serverIp;
    }
}
