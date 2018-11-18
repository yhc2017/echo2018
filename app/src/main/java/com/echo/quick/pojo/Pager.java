package com.echo.quick.pojo;

import lombok.Data;

/**
 * Created by HUAHUA on 2018/7/28.
 */

@Data
public class Pager {
    public String mtitle;
    public String mcontent;
    public String mtranslation;

    public Pager(String mtitle,String mcontent,String mtranslation){
        this.mtitle=mtitle;
        this.mcontent=mcontent;
        this.mtranslation=mtranslation;
    }

}
