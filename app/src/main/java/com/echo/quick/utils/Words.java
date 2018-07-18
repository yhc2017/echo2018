package com.echo.quick.utils;

/**
 * Created by HUAHUA on 2018/7/18.
 */

public class Words {
    private String word;
    private String symbol;
    private String explain;

    public Words(){

    }
    public Words(String word,String symbol,String explain){
        this.word = word;
        this.symbol = symbol;
        this.explain = explain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
}
