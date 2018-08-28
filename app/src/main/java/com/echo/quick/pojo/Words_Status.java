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
}
