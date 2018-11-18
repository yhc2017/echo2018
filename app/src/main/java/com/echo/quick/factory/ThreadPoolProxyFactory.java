package com.echo.quick.factory;

import com.echo.quick.utils.ThreadPoolProxy;

/**
 * 文件名：ThreadPoolProxyFactory.java
 * 创建人：Hello周少侠
 * 创建时间：2018/8/26 19:11
 * 类描述：
 *
 * 修改人：
 * 修改时间：
 * 修改内容：
 *
**/

public class ThreadPoolProxyFactory {
    private static ThreadPoolProxy mNormalThreadPoolProxy;
    private static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 得到普通线程池代理对象mNormalThreadPoolProxy
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 得到下载线程池代理对象mDownLoadThreadPoolProxy
     */
    public static ThreadPoolProxy getDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPoolProxy == null) {
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }
}