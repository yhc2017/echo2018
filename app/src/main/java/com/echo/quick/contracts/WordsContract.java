package com.echo.quick.contracts;

import com.echo.quick.pojo.Words;

/**
 * 项目名称：echo2018
 * 类描述：管理WordsActivity的View和事件
 * 创建人：zhou-jx
 * 创建时间：2018/7/25 14:34
 * 修改人：zhou-jx
 * 修改时间：2018/7/25 14:34
 * 修改备注：
 */

public interface WordsContract {

    interface IWordsView{}

    interface IWordsPresenter{

        /**
         * 方法名称：liefSwipe
         * 方法描述: 处理向左滑动的事件
         * 参数1： 参数说明
         *
         **/
        void liefSwipe(Words w);

        /**
         * 方法名称：rightSwipe
         * 方法描述: 处理向右滑动的事件
         * 参数1： 参数说明
         *
         **/
        void rightSwipe(Words w);

    }

}