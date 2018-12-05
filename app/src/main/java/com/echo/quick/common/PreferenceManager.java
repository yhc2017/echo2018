package com.echo.quick.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.echo.quick.utils.App;


/**
 * Class name: PreferenceManager
 * Specific description :轻量级的数据存储，采用key-value的形式存储，这是工具类
 * 创建人: HUAHUA
 * @Time :1.0 , 2018/11/26 19:43
 */
public class PreferenceManager {
    private static SharedPreferences.Editor editor;
    private static SharedPreferences mSharedPreferences;
    private final String SHAREDPREFERENCE_NAME = "EC";

    @SuppressLint("CommitPrefEdits")
    private PreferenceManager(){
        mSharedPreferences = App.app.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }
    private static PreferenceManager instance;

    /**
     * Method name : getInstance()
     * Specific description :获取实例
     */
    public synchronized static PreferenceManager getInstance(){
        if(instance == null){
            instance = new PreferenceManager();
        }
        return instance;
    }

    /**
     * shared 保存
     * @param key
     * @param object
     */
    public void put(String key, Object object) {

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * shared 获取
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return mSharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mSharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mSharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mSharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mSharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return mSharedPreferences.getString(key, null);
        }

    }


    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有的数据
     */
    public void clear() {
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否存在
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }
}
