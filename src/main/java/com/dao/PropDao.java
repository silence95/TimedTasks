package com.dao;

import java.util.List;
import com.vo.Prop;

public interface PropDao {

    public List<Prop> getValByTaskName(String taskName);
}
