package com.echo.quick.pojo;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目名称：echo2018
 * 类描述：生词表,使用LitePal框架
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 14:57
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 14:57
 * 修改备注：
 */

@EqualsAndHashCode(callSuper=true)
@Data
public class Words_Status extends LitePalSupport implements Serializable {

    private String wordId;
    private String word;
    private String symbol;
    private String explain;
    private String pron;
    private String eg1;
    private String eg1Chinese;
    private String eg2;
    private String eg2Chinese;
    private String status;
    private String topicId;
    private String recordTime;

    public Words_Status(){

    }

//    public Words_Status(String wordId, String pron, String word, String symbol, String explain, String eg1, String eg1_chinese, String eg2, String eg2_chinese,String status, String topicId){
//        this.wordId = wordId;
//        this.word = word;
//        this.symbol = symbol;
//        this.explain = explain;
//        this.pron = pron;
//        this.eg1 = eg1;
//        this.eg1Chinese = eg1_chinese;
//        this.eg2 = eg2;
//        this.eg2Chinese = eg2_chinese;
//        this.status = status;
//        this.topicId = topicId;
//    }

    public Words_Status(String wordId, String pron, String symbol, String word, String explain, String eg1, String eg1Chinese, String eg2, String eg2Chinese, String status, String topicId, String recordTime) {
        this.wordId = wordId;
        this.word = word;
        this.symbol = symbol;
        this.explain = explain;
        this.pron = pron;
        this.eg1 = eg1;
        this.eg1Chinese = eg1Chinese;
        this.eg2 = eg2;
        this.eg2Chinese = eg2Chinese;
        this.status = status;
        this.topicId = topicId;
        this.recordTime = recordTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getEg1() {
        return eg1;
    }

    public void setEg1(String eg1) {
        this.eg1 = eg1;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public String getEg1Chinese() {
        return eg1Chinese;
    }

    public void setEg1Chinese(String eg1Chinese) {
        this.eg1Chinese = eg1Chinese;
    }

    public String getEg2() {
        return eg2;
    }

    public void setEg2(String eg2) {
        this.eg2 = eg2;
    }

    public String getEg2Chinese() {
        return eg2Chinese;
    }

    public void setEg2Chinese(String eg2Chinese) {
        this.eg2Chinese = eg2Chinese;
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
