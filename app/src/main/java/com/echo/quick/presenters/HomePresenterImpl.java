package com.echo.quick.presenters;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echo.quick.contracts.HomeContract;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.utils.App;
import com.echo.quick.utils.SPUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private HomeContract.IHomeView iHomeView;

    private App app;

    {
        app = (App) App.getContext();
    }

    public HomePresenterImpl(){}

    public HomePresenterImpl(HomeContract.IHomeView iHomeView){
        this.iHomeView = iHomeView;
    }

    @Override
    public int getYear() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return Integer.valueOf(df.format(new Date()));
    }

    @Override
    public int getMouth() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("MM");
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
     *@param   wordcount int
     *@return datenum int
     */
    @Override
    public int calMyPlanNmu(String date, int wordcount) throws ParseException {
        //相差天数
        int datenum = 0;
        String s1=date+"-12";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String s2 = df.format(System.currentTimeMillis());
        Date d1=df.parse(s1);
        Date d2=df.parse(s2);
        int hh = (int) ((d1.getTime()-d2.getTime())/(60*60*1000*24));

        //已背单词数量
        IWordsStatusDao statusDao = new WordsStatusImpl();
        int overWords = statusDao.selectCountByStatusAndTopicId("review_grasp", app.getTopicId());

        //四舍五入取整得到每日目标数
        datenum = Math.round((wordcount - overWords)/hh);
        System.out.printf("--------------每日的目标数："+"("+wordcount+"-"+overWords+")/"+hh+"="+datenum);

        return datenum;
    }

    @Override
    public int calculateEndNum(String date) throws ParseException {
        //相差天数
        int dateNum = 0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

        Date planDate = df.parse( date+"-12");
        Date currentDate = df.parse(df.format(System.currentTimeMillis()));

        int hh = (int) ((planDate.getTime()-currentDate.getTime())/(60*60*1000*24));

        return hh;
    }
}
