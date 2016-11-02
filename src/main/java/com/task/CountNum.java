package com.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dao.PropDao;
import com.vo.Prop;

@Component
@Scope("prototype")
public class CountNum extends AbstractTask {

    @Autowired PropDao propDao;
    private int val;
    
    private void init() {
        List<Prop> valList = propDao.getValByTaskName(getTaskName());
        if(valList != null && !valList.isEmpty()) {
            val = Integer.parseInt(valList.get(0).getProp_value());
        } else {
            val = 5;
        }
        System.out.println(getTaskName() + " " + val);
            
    }
    
    public void run() {
        init();
        for(int i = 0 ; i < val; i++) {
            System.out.println(getTaskName() + i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
