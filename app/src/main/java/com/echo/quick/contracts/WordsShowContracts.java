package com.echo.quick.contracts;

import com.echo.quick.pojo.Words_New;

/**
 * 项目名称：echo2018
 * 类描述：建两个接口，管理单词详情页面和变化和数据操作
 * 创建人：zhou-jx
 * 创建时间：2018/7/24 14:22
 * 修改人：zhou-jx
 * 修改时间：2018/7/24 14:22
 * 修改备注：
 */

public interface WordsShowContracts {

    public interface IWordsShowView{

        /**
         * 方法名称：initView
         * 方法描述: 传入一个words，判断该词是否为生词
         * 参数1： String
         * @return void
         **/
        public void initView(Boolean res);


    }

    public interface IWordsShowPresenter{

        /**
         * 方法名称：isExist
         * 方法描述: 判断是否为生词
         * 参数1： 参数说明
         * @return [返回类型说明]
         **/
        public void isExist(String word);

        /**
         * 方法名称：addNewWord
         * 方法描述: 传入一个word对象，添加到数据库里
         * 参数1： 一个生词对象
         * @return boolean
         **/
        public boolean addNewWord(Words_New wordsNew);

        /**
         * 方法名称：delNewWord
         * 方法描述: 传入一个word，从数据库中删除
         * 参数1： 一个生词对象
         * @return boolean
         **/
        public boolean delNewWord(String word);

    }


}
