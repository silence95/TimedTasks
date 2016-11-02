package com.boot;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.dao.ServerDao;
import com.pool.ExecutorPool;
import com.task.MainRunner;

@Component
public class TaskBoot implements ApplicationContextAware{

    @Autowired private ServerDao serverDao;
    private ApplicationContext applicationContext;
    
    @PostConstruct
    public void init() {
        String serverIp = getServerIp();
        if("".equals(serverIp)) {
            System.out.println("获得服务器ip失败");
            return ;
        }
        String serverId = serverDao.queryServerIdByIp(serverIp);
        if(serverId == null || "".equals(serverId)) {
            serverDao.save(serverIp, "8080");
            serverId = serverDao.queryServerIdByIp(serverIp);
        }
        start(Integer.parseInt(serverId));
        return ;
    }
    
    public void start(int serverId) {
        MainRunner mainRunner = (MainRunner)applicationContext.getBean("mainRunner");
        mainRunner.setServerId(serverId);
        ExecutorPool.getInstance().addTask("mainRunner", mainRunner, 0L, 10);
    /*    MainRunner mainRunnerClone = null;
        try {
            mainRunnerClone = (MainRunner) mainRunner.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        MainRunner mainRunner2 = (MainRunner)applicationContext.getBean("mainRunner");
        System.out.println((mainRunner == mainRunnerClone) + " " + (mainRunner.getTestService() == mainRunnerClone.getTestService()));
        System.out.println((mainRunner == mainRunner2) + " " + (mainRunner.getTestService() == mainRunner2.getTestService())); prototype 效果跟 clone 相同，内部的对象还是相等*/

    }

    private String getServerIp() {
        String serverIp = "";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces(); // 
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback()) { // 网络接口是否已经开启并运行
                    continue;
                }
                Enumeration<InetAddress> iddrs = ni.getInetAddresses();
                while(iddrs.hasMoreElements()) {
                    InetAddress ip = iddrs.nextElement();
                    if (!(ip instanceof Inet6Address)) { // 不是本地地址，不是环回地址
                        serverIp = ip.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return serverIp;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
