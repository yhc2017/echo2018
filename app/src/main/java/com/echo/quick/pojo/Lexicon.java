package com.echo.quick.pojo;

import java.io.Serializable;

/**
 * Class name: echo2018
 * Specific description :词库对象
 * 创建人: HUAHUA
 * @Time : 2018/12/19
 */

//@Data
public class Lexicon implements Serializable{
    public String topicId;
    public String topicName;
    public String tableName;
    public String wordAllCount;

    public Lexicon(){

    }

    public Lexicon(String topicId, String topicName, String tableName, String wordAllCount){
        this.topicId=topicId;
        this.topicName=topicName;
        this.tableName=tableName;
        this.wordAllCount=wordAllCount;
    }

}
