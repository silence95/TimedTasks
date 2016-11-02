package com.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vo.Task;

@Repository("taskDao")
public interface TaskDao {

    public List<Task> getAllTaskByServerId(int serverId);
}
