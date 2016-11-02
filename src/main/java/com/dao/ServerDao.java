package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("serverDao")
public interface ServerDao {

    public String queryServerIdByIp(String serverIp);
    
    public void save(@Param("serverIp")String serverIp, @Param("serverPort")String serverPort);
}
