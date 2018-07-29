package com.echo.quick.pojo;

/**
 * Created by HUAHUA on 2018/7/28.
 */

public class Pager {
    public String mtitle;
    public String mcontent;
    public String mtranslation;

    public Pager(String mtitle,String mcontent,String mtranslation){
        this.mtitle=mtitle;
        this.mcontent=mcontent;
        this.mtranslation=mtranslation;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public String getMtranslation() {
        return mtranslation;
    }

    public void setMtranslation(String mtranslation) {
        this.mtranslation = mtranslation;
    }
}
