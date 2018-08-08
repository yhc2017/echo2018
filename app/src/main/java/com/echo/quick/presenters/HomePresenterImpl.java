package com.echo.quick.presenters;

import android.annotation.SuppressLint;
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
     *@param   wordindex int
     *@return datenum int
     */
    @Override
    public int calMyPlanNmu(String date, int wordindex) throws ParseException {
        //相差天数
        int datenum = 0;
        String s1=date+"-12";
        String s2= getYear()+"-"+getMouth()+"-12";
        @SuppressLint("SimpleDateFormat") DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=new GregorianCalendar();
        Date d1=df.parse(s1);
        Date d2=df.parse(s2);
        int hh = (int) ((d1.getTime()-d2.getTime())/(60*60*1000*24));

        //全部单词数量
        Object o = SPUtils.get(App.getContext(), "wordsBox", "[\n" +
                "    {\n" +
                "        \"topicId\": \"12\",\n" +
                "        \"topicName\": \"六级必备词汇\",\n" +
                "        \"tableName\": \"word_12\",\n" +
                "        \"wordAllCount\": 2087,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"11\",\n" +
                "        \"topicName\": \"四级必备词汇\",\n" +
                "        \"tableName\": \"word_11\",\n" +
                "        \"wordAllCount\": 13527,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"140\",\n" +
                "        \"topicName\": \"高考大纲词汇\",\n" +
                "        \"tableName\": \"word_140\",\n" +
                "        \"wordAllCount\": 3874,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"141\",\n" +
                "        \"topicName\": \"高考常用短语词汇\",\n" +
                "        \"tableName\": \"word_141\",\n" +
                "        \"wordAllCount\": 362,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"13\",\n" +
                "        \"topicName\": \"考研必备词汇\",\n" +
                "        \"tableName\": \"word_13\",\n" +
                "        \"wordAllCount\": 5475,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"14\",\n" +
                "        \"topicName\": \"TOEFL必备词汇\",\n" +
                "        \"tableName\": \"word_14\",\n" +
                "        \"wordAllCount\": 4883,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"16\",\n" +
                "        \"topicName\": \"GRE考试必备词汇\",\n" +
                "        \"tableName\": \"word_16\",\n" +
                "        \"wordAllCount\": 14992,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"15\",\n" +
                "        \"topicName\": \"雅思必备词汇\",\n" +
                "        \"tableName\": \"word_15\",\n" +
                "        \"wordAllCount\": 4541,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"17\",\n" +
                "        \"topicName\": \"四级高频词汇\",\n" +
                "        \"tableName\": \"word_cet4_high\",\n" +
                "        \"wordAllCount\": 685,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"topicId\": \"18\",\n" +
                "        \"topicName\": \"真题阅读词汇\",\n" +
                "        \"tableName\": \"word_paper_reading\",\n" +
                "        \"wordAllCount\": 65,\n" +
                "        \"preserve1\": null,\n" +
                "        \"preserve2\": null,\n" +
                "        \"preserve3\": null,\n" +
                "        \"preserve4\": null,\n" +
                "        \"preserve5\": null\n" +
                "    }\n" +
                "]");
        assert o != null;
        JSONArray jsonArray_wordsBox = JSONArray.parseArray(o.toString());
        JSONObject object = jsonArray_wordsBox.getJSONObject(wordindex);
        int allWords = Integer.valueOf(object.getString("wordAllCount"));

        //已背单词数量
        IWordsStatusDao statusDao = new WordsStatusImpl();
        int overWords = statusDao.selectCountByStatusAndTopicId("review_grasp", app.getTopicId());

        //四舍五入取整得到每日目标数
        datenum = Math.round((allWords - overWords)/hh);
        System.out.printf("--------------每日的目标数："+"("+allWords+"-"+overWords+")/"+hh+"="+datenum);

        return datenum;
    }
}
