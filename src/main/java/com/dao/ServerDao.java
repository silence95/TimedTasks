package com.dao;


public interface ServerDao {

    public int queryServerId(String serverIp);
    
    public void save(String serverIp,String serverPort);
}
