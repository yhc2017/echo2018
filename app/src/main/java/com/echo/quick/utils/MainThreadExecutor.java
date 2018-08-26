package com.echo.quick.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * 文件名：MainThreadExecutor.java
 * 创建人：Hello周少侠
 * 创建时间：2018/8/25 19:13
 * 类描述：
 * 
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 
**/

public class MainThreadExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}