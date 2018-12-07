package com.echo.quick.activities;

import com.alibaba.fastjson.JSON;
import com.echo.quick.pojo.Words_Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LogUserActionTest {

    List list = new ArrayList();

    @Test
    public void test_case(){
//        JSONArray array = new JSONArray();
        List array = new ArrayList();
        List<Words_Log> list = genarateList();
        for (Words_Log log :list){
            System.out.println(JSON.toJSONString(log));
            array.add(JSON.toJSONString(log));
        }
//        System.out.println("list->>>"+array);
    }

    public List genarateList(){
        Words_Log logs = new Words_Log();
        for (int i = 0; i<=10;i++){
            logs.setLeftNum(1);
            logs.setTopicId("005");
            logs.setWord("TanzJ"+i);
            logs.setWordId(i+"");
            list.add(logs);
//            System.out.println(JSON.toJSONString(logs));
        }
        return list;
    }
}
