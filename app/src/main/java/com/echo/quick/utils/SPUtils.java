package com.echo.quick.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件名：SPUtils
 * 创建人：周少侠
 * 创建时间：2018/7/18 15:10
 * 类描述：SharedPreferences封装类SPUtils
 * 对外公布出put，get，remove，clear等等方法
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 
**/

public class SPUtils
{
    /**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "quick_data";
	public static final String LEXICON_FILE_NAME = "quick_lexicon_data";
 
	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 *
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object)
	{
 
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
 
		if (object instanceof String)
		{
			editor.putString(key, (String) object);
		} else if (object instanceof Integer)
		{
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean)
		{
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float)
		{
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long)
		{
			editor.putLong(key, (Long) object);
		} else
		{
			editor.putString(key, object.toString());
		}
 
		SharedPreferencesCompat.apply(editor);
	}
 
	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject)
	{
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
 
		if (defaultObject instanceof String)
		{
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer)
		{
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean)
		{
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float)
		{
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long)
		{
			return sp.getLong(key, (Long) defaultObject);
		}
 
		return null;
	}

	/**
	 * 保存List
	 * @param tag
	 * @param datalist
	 */
	public static <T> void setDataList(Context context, String tag, List<T> datalist) {
		SharedPreferences sp = context.getSharedPreferences(LEXICON_FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (null == datalist || datalist.size() <= 0)
			return;

		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(datalist);
		editor.putString(tag, strJson);
		SharedPreferencesCompat.apply(editor);

	}

	/**
	 * 获取List
	 * @param tag
	 * @return
	 */
	public static <T> List<T> getDataList(Context context, String tag) {
		SharedPreferences sp = context.getSharedPreferences(LEXICON_FILE_NAME,
				Context.MODE_PRIVATE);
		List<T> datalist=new ArrayList<>();
		String strJson = sp.getString(tag, null);
		if (null == strJson) {
			return datalist;
		}
		Gson gson = new Gson();
		datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
		}.getType());
		return datalist;

	}

	/**
	 * 存储Map集合
	 * @param key 键
	 * @param map 存储的集合
	 * @param <K> 指定Map的键
	 * @param <T> 指定Map的值
	 */

	public static <K extends Serializable,T extends Serializable> void setMap(Context context, String key , Map<K ,T> map){
		SharedPreferences sp = context.getSharedPreferences(LEXICON_FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		if (map == null || map.isEmpty() || map.size() < 1){
			return;
		}

		Gson gson = new Gson();
		String strJson  = gson.toJson(map);
		editor.putString(key ,strJson);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 获取Map集合
	 * */
	public static <K extends Serializable,T extends Serializable> Map<K,T> getMap(Context context,String key){
		SharedPreferences sp = context.getSharedPreferences(LEXICON_FILE_NAME,
				Context.MODE_PRIVATE);
		Map<K,T> map = new HashMap<>();
		String strJson = sp.getString(key,null);
		if (strJson == null){
			return map;
		}
		Gson gson = new Gson();
		map = gson.fromJson(strJson,new TypeToken<Map<K,T> >(){}.getType());
		return map;
	}
 
	/**
	 * 移除某个key值已经对应的值
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key)
	{
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}
 
	/**
	 * 清除用户所有数据
	 * 不清除词库数据
	 * @param context
	 */
	public static void clear(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}
 
	/**
	 * 查询某个key是否已经存在
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key)
	{
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}
 
	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}
 
	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 *
	 */
	private static class SharedPreferencesCompat
	{
		private static final Method sApplyMethod = findApplyMethod();
 
		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod()
		{
			try
			{
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e)
			{
			}
 
			return null;
		}
 
		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor)
		{
			try
			{
				if (sApplyMethod != null)
				{
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e)
			{
			} catch (IllegalAccessException e)
			{
			} catch (InvocationTargetException e)
			{
			}
			editor.commit();
		}
	}
 
}