package com.boot;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.dao.TaskDao;
import com.pool.ExecutorPool;
import com.vo.Task;

public class MainRunner implements Runnable,ApplicationContextAware{
 
    @Autowired TaskDao taskDao;
    private int serverId;
    private ApplicationContext applicationContext;
    
    public void run() {
        // 获得该服务器上的全部任务
        List<Task> tasks = taskDao.getAllTaskByServerId(serverId);
        if(tasks == null || !tasks.isEmpty()) {
            System.out.println("该服务器 " + serverId + " 没有要运行的任务");
            return ;
        }
        for(Task task : tasks) {
            createTask(task);
        }
    }

    private void createTask(Task task) {
        ExecutorPool pool = ExecutorPool.getInstance();
        Runnable runnable = (Runnable)applicationContext.getBean(task.getTaskName());
        pool.addTask(task.getTaskName(), runnable, 0L, task.getIntervalTime());
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
}
