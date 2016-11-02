package com.task;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dao.TaskDao;
import com.pool.ExecutorPool;
import com.service.TestService;
import com.vo.Task;

@Component
@Scope("prototype")
public class MainRunner implements Runnable,ApplicationContextAware,Cloneable{
 
    @Autowired TaskDao taskDao;
    @Autowired TestService testService;
    private int serverId;
    private ApplicationContext applicationContext;
    
    private ExecutorPool pool = ExecutorPool.getInstance();
    private ConcurrentHashMap<String, ScheduledFuture<?>> cacheHashMap = ExecutorPool.cacheHashMap;
    
    public void run() {
        // 获得该服务器上的全部任务
        List<Task> tasks = taskDao.getAllTaskByServerId(serverId);
        if(tasks == null || tasks.isEmpty()) {
            System.out.println("该服务器 " + serverId + " 没有要运行的任务");
            return ;
        }
        for(Task task : tasks) {
            createTask(task);
        }
    }

    private void createTask(Task task) {
        if("1".equals(task.getIsOn())) {
            dealTaskWhenOn(task);
        } else {
            dealTaskWhenOff(task);
        }
    }

    private void dealTaskWhenOff(Task task) {
        if(cacheHashMap.contains(task.getTaskName())) {
            ScheduledFuture<?> future = cacheHashMap.get(task.getTaskName());
            future.cancel(false); // 让正在执行中的任务执行完成后再停止
            cacheHashMap.remove(future);
        }
    }

    private void dealTaskWhenOn(Task task) {
        if(isExecutor(task.getTaskName())) // 正在执行中的任务
            return ;
        int count = 0;
        while(count < 2) {
            boolean success = createRunner(task);
            if(success)
                break;
            count++;
        }
    }

    private boolean createRunner(Task task) {
        try {
            AbstractTask taskObj = (AbstractTask)applicationContext.getBean(task.getTaskType());
            if(taskObj == null) {
                System.out.println("get task " + task.getTaskType() + " fail");
                return false;
            }
            taskObj.setServerId(serverId);
            taskObj.setTaskName(task.getTaskName());
            pool.addTask(task.getTaskName(), taskObj, 0L, task.getIntervalTime());
            ScheduledFuture<?> future = cacheHashMap.get(task.getTaskName());
            if(future != null && !future.isCancelled() && !future.isDone())
                return true;
            future.cancel(true);
        } catch(Exception e) {
            System.out.println("add task " + task.getTaskType() + " fail: " + e.getMessage());
        }
        return false;
    }

    private boolean isExecutor(String taskName) {
        if(!cacheHashMap.contains(taskName))
            return false;
        ScheduledFuture<?> future = cacheHashMap.get(taskName);
        if(future.isCancelled() || future.isDone()) // isCancelled——已取消，但最后一个任务还未执行完成; isDone——正常或意外中止，取消
            return false;
        return true;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
    
    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TestService getTestService() {
        return testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }
}
