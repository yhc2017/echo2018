package com.echo.quick.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Class name: WordList
 * Specific description :传递给frgment的一个bean
 * 创建人: HUAHUA
 * @version :1.0 , 2018/8/2 14:33
 * 修改人：
 * @version :
 * @since ：[产品|模块版本]
 */
public  class WordList implements Serializable {

   private List<Words> data;

    public List<Words> getData() {
        return data;
    }

    public void setData(List<Words> data) {
        this.data = data;
    }
}