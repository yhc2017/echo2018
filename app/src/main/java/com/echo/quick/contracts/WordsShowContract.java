package com.echo.quick.contracts;

import com.echo.quick.pojo.Words;
import com.echo.quick.pojo.Words_Status;

/**
 * 项目名称：echo2018
 * 类描述：建两个接口，管理单词详情页面和变化和数据操作
 * 创建人：zhou-jx
 * 创建时间：2018/7/24 14:22
 * 修改人：zhou-jx
 * 修改时间：2018/7/24 14:22
 * 修改备注：
 */

public interface WordsShowContract {

    interface IWordsShowView{

        /**
         * 方法名称：initView
         * 方法描述: 传入一个words，判断该词是否为生词
         * 参数1： String
         *
         **/
        void initVisibility(Boolean res);


    }

    interface IWordsShowPresenter{

        /**
         * 方法名称：isExist
         * 方法描述: 判断是否为生词
         * 参数1： 参数说明
         *
         **/
        void isExist(String word);

        /**
         * 方法名称：addNewWord
         * 方法描述: 传入一个word对象，添加到数据库里
         * 参数1： 一个生词对象
         * @return boolean
         **/
        boolean addNewWord(Words_Status words);

        /**
         * 方法名称：addNewWord
         * 方法描述: 传入一个word对象，添加到数据库里
         * 参数1： 一个生词对象
         * @return boolean
         **/
        boolean updateWord(Words_Status word);

        /**
         * 方法名称：addWordToStatus
         * 方法描述: 传入一个word对象，添加到数据库里
         * 参数1： 一个单词对象
         * @return boolean
         **/
        boolean addWordToStatus(Words word);

        /**
         * 方法名称：delNewWord
         * 方法描述: 传入一个word，从数据库中删除
         * 参数1： 一个生词对象
         * @return boolean
         **/
        boolean delNewWord(String word);

    }


}
