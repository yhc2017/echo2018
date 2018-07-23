package com.echo.quick.pojo;

import org.litepal.crud.LitePalSupport;

/**
 * 项目名称：echo2018
 * 类描述：单词日志表
 * 创建人：zhou-jx
 * 创建时间：2018/7/23 15:09
 * 修改人：zhou-jx
 * 修改时间：2018/7/23 15:09
 * 修改备注：
 */

public class Words_Log extends LitePalSupport{

    private String wordId;
    private String word;
    private int num;
    private String prepare1;
    private int prepare2;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPrepare1() {
        return prepare1;
    }

    public void setPrepare1(String prepare1) {
        this.prepare1 = prepare1;
    }

    public int getPrepare2() {
        return prepare2;
    }

    public void setPrepare2(int prepare2) {
        this.prepare2 = prepare2;
    }
}
