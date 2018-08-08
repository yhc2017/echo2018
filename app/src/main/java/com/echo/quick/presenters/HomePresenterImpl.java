package com.echo.quick.presenters;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.utils.App;
import com.echo.quick.utils.SPUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class name: echo2018
 * Specific description :<功能的详细描述>
 * 创建人: HUAHUA
 *
 * @Time : 2018/8/5
 * 修改人：
 * @Time :
 * @since ：[产品|模块版本]
 */


public class HomePresenterImpl implements HomeContract.IHomePresenter {

    HomeContract.IHomeView iHomeView;
    JSONArray jsonArray_wordsBox;
    Object o;

    public HomePresenterImpl(){}

    public HomePresenterImpl(HomeContract.IHomeView iHomeView){
        this.iHomeView = iHomeView;
    }

    @Override
    public int getYear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return Integer.valueOf(df.format(new Date()));
    }

    @Override
    public int getMouth() {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        return Integer.valueOf(df.format(new Date()));
    }

    @Override
    public void updatePlan() {
        iHomeView.updatePlan();
    }


    /**
     * Method name : calMyPlanNmu
     * Specific description :用于计算每天的单词目标数
     * 创建人：茹韶燕
     *@param   date String
     *@param   wordindex int
     *@return datenum int
     */
    @Override
    public int calMyPlanNmu(String date, int wordindex) throws ParseException {
        //相差天数
        int datenum = 0;
        String s1=date+"-12";
        String s2= getYear()+"-"+getMouth()+"-12";
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=new GregorianCalendar();
        Date d1=df.parse(s1);
        Date d2=df.parse(s2);
        int hh = (int) ((d1.getTime()-d2.getTime())/(60*60*1000*24));

        //全部单词数量
        o = SPUtils.get(App.getContext(), "wordsBox", "");
        jsonArray_wordsBox = JSONArray.parseArray(o.toString());
        JSONObject object = jsonArray_wordsBox.getJSONObject(wordindex);
        int allWords = Integer.valueOf(object.getString("wordAllCount"));

        //已背单词数量
        IWordsStatusDao statusDao = new WordsStatusImpl();
        int overWords = statusDao.selectCount("review_grasp");

        //四舍五入取整得到每日目标数
        datenum = Math.round((allWords - overWords)/hh);
        System.out.printf("--------------每日的目标数："+"("+allWords+"-"+overWords+")/"+hh+"="+datenum);

        return datenum;
    }
}
