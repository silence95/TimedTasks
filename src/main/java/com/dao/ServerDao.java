package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vo.Server;

@Repository("serverDao")
public interface ServerDao {

    public Server queryServerIdByIp(String serverIp);
    
    public void save(@Param("serverIp")String serverIp, @Param("serverPort")String serverPort);
    
    public void updateIsOn(@Param("isOn")String isOn, @Param("serverId")String serverId);
    
}
