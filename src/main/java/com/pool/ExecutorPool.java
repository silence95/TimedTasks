package com.pool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorPool {

    private ExecutorPool() {}
    
    public static ExecutorPool pool = null;
    
    private ScheduledExecutorService scheduledExecutorService;
    
    public static ConcurrentHashMap<String, ScheduledFuture<?>> cacheHashMap = new ConcurrentHashMap<>();
    
    public synchronized static ExecutorPool getInstance() {
       if(pool == null) {
           pool = new ExecutorPool();
           pool.init();
       }
       return pool;
    }
    
    private void init() {
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2; 
        scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
    }
    
    public void addTask(String taskName,Runnable runnable,long initialDelay,int period) {
        System.err.println("增加一个任务" + taskName + "在" + initialDelay + "s后开始执行，间隔执行时间为" + period + "s");
        ScheduledFuture<?> future =  scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);
        cacheHashMap.put(taskName, future);
    }
}
