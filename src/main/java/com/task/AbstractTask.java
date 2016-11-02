package com.task;

public abstract class AbstractTask implements Runnable,Cloneable{

    private String taskName;
    private int serverId;
    
    public String getTaskName() {
        return taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

}
