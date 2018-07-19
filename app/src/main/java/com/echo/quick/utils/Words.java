package com.echo.quick.utils;

/**
 * Class name: Words
 * Specific description :单词对象
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/18 14:42
 * 修改人：茹韶燕
 * @version : （添加其他属性）2.0 2018/7/19 10:34
 * @since ：[quick|背单词模块]
 */

public class Words {
    private String word;
    private String symbol;    //音标
    private String explain;   //释义
    //其他属性
    private String eg1;   //例句
    private String eg1_chinese;   //例句
    private String eg2;   //例句
    private String eg2_chinese;   //例句

    /**
     * Method name : 构造方法一
     */
    public Words(){

    }
    /**
     * Method name : 构造方法二
     * Specific description :用于背单词界面的item的model
     *@param   word  String  单词
     *@param   symbol  String  音标
     *@param   explain  String  释义
     */
    public Words(String word,String symbol,String explain){
        this.word = word;
        this.symbol = symbol;
        this.explain = explain;
    }
    /**
     * Method name : 构造方法三
     * Specific description :用于单词详情界面model
     *@param   word  String  单词
     *@param   symbol  String  音标
     *@param   explain  String  释义
     *@param   eg1  String  例句
     */
    public Words(String word,String symbol,String explain,String eg1,String eg1_chinese,String eg2,String eg2_chinese){
        this.word = word;
        this.symbol = symbol;
        this.explain = explain;
        this.eg1 = eg1;
        this.eg1_chinese = eg1_chinese;
        this.eg2 = eg2;
        this.eg2_chinese = eg2_chinese;
    }
    public String getEg1() {
        return eg1;
    }

    public void setEg1(String eg1) {
        this.eg1 = eg1;
    }

    public String getEg1_chinese() {
        return eg1_chinese;
    }

    public void setEg1_chinese(String eg1_chinese) {
        this.eg1_chinese = eg1_chinese;
    }

    public String getEg2() {
        return eg2;
    }

    public void setEg2(String eg2) {
        this.eg2 = eg2;
    }

    public String getEg2_chinese() {
        return eg2_chinese;
    }

    public void setEg2_chinese(String eg2_chinese) {
        this.eg2_chinese = eg2_chinese;
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
