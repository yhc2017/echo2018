package com.echo.quick.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class name: Words
 * Specific description :单词对象
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/18 14:42
 * 修改人：茹韶燕
 * @version : （添加其他属性）2.0 2018/7/19 10:34
 * @since ：[quick|背单词模块]
 */

@EqualsAndHashCode()
@Data
public class Words {

    private String wordId;
    private String word;
    private String symbol;    //音标
    private String explain;   //释义
    private String pron;
    //其他属性
    private String eg1;   //例句
    private String eg1_chinese;   //例句
    private String eg2;   //例句
    private String eg2_chinese;   //例句
    private String topicId;

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
    public Words(String wordId, String pron, String word,String symbol,String explain,String eg1,String eg1_chinese,String eg2,String eg2_chinese,String topicId){
        this.wordId = wordId;
        this.word = word;
        this.symbol = symbol;
        this.explain = explain;
        this.pron = pron;
        this.eg1 = eg1;
        this.eg1_chinese = eg1_chinese;
        this.eg2 = eg2;
        this.eg2_chinese = eg2_chinese;
        this.topicId = topicId;
    }
}
