package com.echo.quick.pojo;

import org.litepal.crud.LitePalSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目名称：echo2018
 * 类描述：单词日志表
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 15:09
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 15:09
 * 修改备注：
 */
@EqualsAndHashCode(callSuper=true)
@Data
public class Words_Log extends LitePalSupport{


    private String wordId;

    private String word;
    private int leftNum;
    private int rightNum;
    private String topicId;
    private String recordTime;

    public Words_Log(){}

    public Words_Log(String wordId,String word,int leftNum,int rightNum,String topicId){
        this.wordId = wordId;
        this.word = word;
        this.leftNum = leftNum;
        this.rightNum = rightNum;
        this.topicId = topicId;
    }


    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(int leftNum) {
        this.leftNum = leftNum;
    }

    public int getRightNum() {
        return rightNum;
    }

    public void setRightNum(int rightNum) {
        this.rightNum = rightNum;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
