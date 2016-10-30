package com.dao;

import java.util.List;

import com.vo.Task;


public interface TaskDao {

    public List<Task> getAllTaskByServerId(int serverId);
}
