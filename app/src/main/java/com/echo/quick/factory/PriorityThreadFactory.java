package com.echo.quick.factory;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

/**
 * 文件名：PriorityThreadFactory.java
 * 创建人：Hello周少侠
 * 创建时间：2018/8/25 19:13
 * 类描述：
 * 
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 
**/

public class PriorityThreadFactory implements ThreadFactory {

    private final int mThreadPriority;

    public PriorityThreadFactory(int threadPriority) {
        mThreadPriority = threadPriority;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(mThreadPriority);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                runnable.run();
            }
        };
        return new Thread(wrapperRunnable);
    }

}