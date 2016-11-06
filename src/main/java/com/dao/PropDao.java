package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vo.Prop;

public interface PropDao {

    public List<Prop> getValByTaskName(String taskName);
    
    public void updateVal(@Param("taskName")String taskName,@Param("valueName")String valueName, @Param("value")String value);
}
