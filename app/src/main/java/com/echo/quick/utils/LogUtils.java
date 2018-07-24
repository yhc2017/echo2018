package com.echo.quick.utils;

import android.util.Log;

/**
 * 文件名：LogUtils
 * 创建人：周少侠
 * 创建时间：2018/7/18 15:03
 * 类描述：一个简单的工具类
 * 
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 
**/

public class LogUtils {
 
	private LogUtils() {
		/* 无法实例化 */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
 
	private static boolean isDebug = false;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "quick info";
 
	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if (isDebug)
			Log.i(TAG, msg);
	}
 
	public static void d(String msg)
	{
		if (isDebug)
			Log.d(TAG, msg);
	}
 
	public static void e(String msg)
	{
		if (isDebug)
			Log.e(TAG, msg);
	}
 
	public static void v(String msg)
	{
		if (isDebug)
			Log.v(TAG, msg);
	}
 
	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}
 
	public static void d(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}
 
	public static void e(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}
 
	public static void v(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

}