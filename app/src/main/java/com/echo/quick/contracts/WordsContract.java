package com.echo.quick.contracts;

import android.content.Context;

import com.echo.quick.pojo.Words_Status;

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

    interface IWordsView{

        void RefreshPage(String result);
        void sendLogResult();

    }

    interface IWordsPresenter{

        /**
         * 方法名称：liefSwipe
         * 方法描述: 处理向左滑动的事件
         * 参数1： 参数说明
         *
         **/
        void leftSwipe(Words_Status w);

        /**
         * 方法名称：rightSwipe
         * 方法描述: 处理向右滑动的事件
         * 参数1： 参数说明
         *
         **/
        void rightSwipe(Words_Status w);

        void endOnce(Context context);

        void play(Context context, String url);

    }

}
