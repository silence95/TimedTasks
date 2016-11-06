package com.pool;

import java.util.Iterator;
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
    
    public void close() {
        if(cacheHashMap != null && !cacheHashMap.isEmpty()) {
            Iterator<ScheduledFuture<?>> ite = cacheHashMap.values().iterator();
            while(ite.hasNext()) {
                ite.next().cancel(false);
            }
        }
        
        try {
            boolean isTerminat = scheduledExecutorService.awaitTermination(10, TimeUnit.SECONDS);
            if(isTerminat) {
                System.out.println("定时任务全部结束");
            } else {
                System.out.println("定时任务结束失败");
            }

            scheduledExecutorService.shutdown();
            if(!scheduledExecutorService.isShutdown())
                scheduledExecutorService.shutdownNow();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
