package com.echo.quick.quickly.utils;

import android.os.Handler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件名：OKhttpManager
 * 创建人：周少侠
 * 创建时间：2018/7/17 14:36
 * 描述：简单OKhttp封装
 *
 * 修改人：
 * 修改时间：
 * 修改内容：
 * 
**/

public class OKhttpManager {
//定义成员变量
    private OkHttpClient mClient;
    private  volatile  static OKhttpManager sManager;//防止多个线程同时访问
    private static Handler handler;//开启线程交互 这里的handler 是os包下面的
//    使用构造方法，完成初始化
    public OKhttpManager() {
        mClient=new OkHttpClient();
        handler=new Handler();
    }
//    使用单例模式，通过获取的方式拿到对象
    public static  OKhttpManager getInstance(){
        OKhttpManager instance=null;
        if (sManager==null){
            synchronized (OKhttpManager.class){
                if (instance==null){
                    instance=new OKhttpManager();
                    sManager=instance;
                }
            }
        }
        return  instance;
    }
//    定义要调用的接口
    interface  Func1{
    void  onResponse(String result);
}
    interface Func2{
        void onResponse(byte [] result);
    }
    interface Func3{
        void  onResponse(JSONObject jsonObject);
    }
//    使编写的代码运行在主线程
//    处理请求网络成功的方法，返回的结果是Json串
    private static void onSuccessJsonStringMethod(final String jsonValue, final Func1 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try {
                        callBack.onResponse(jsonValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * 返回响应的结果是Json对象
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonObjectMethod(final String jsonValue, final Func3 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    try{
                        callBack.onResponse(new JSONObject(jsonValue));
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 返回响应的结果是byte[]数组
     * @param data
     * @param callBack
     */
    private void onSuccessByteMethod(final byte[] data,final Func2 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack!=null){
                    callBack.onResponse(data);
                }
            }
        });
    }
//    然后是调用方法

    /**
     * 请求指定的url返回的结果是Json
     * @param url
     * @param callBack
     */
    public  void  asyncJsonStringByURL(String url,final Func1 callBack){
        final  Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJsonStringMethod(response.body().string(),callBack);
                }
            }
        });
    }

    /**
     * 请求返回的是json对象
     *
     * @param url
     * @param callBack
     */
    public void asyncJsonObjectByURL(String url, final Func3 callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });
    }

    /**
     * 请求返回的是byte字节数组
     */
    public void asyncGetByteByURL(String url, final Func2 callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessByteMethod(response.body().bytes(), callBack);
                }
            }
        });
    }

//    表单提交像一般的姓名和密码
    /**
     * 表单提交
     * @param url
     * @param params
     * @param callBack
     */
    public void sendComplexForm(String url, Map<String,String> params,final Func1 callBack){
        FormBody.Builder form_builder=new FormBody.Builder();//表单对象包含以input开始的对象以html为主
//        非空键值对
        if (params != null && !params.isEmpty()) {
            for(Map.Entry<String,String> entry : params.entrySet()){
                form_builder.add(entry.getKey(),entry.getValue());
            }
        }

        RequestBody request_body = form_builder.build();
        Request request = new Request.Builder().url(url).post(request_body).build();//采用post方式提交
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response!=null&&response.isSuccessful()){
                    onSuccessJsonStringMethod(response.body().string(), callBack);
                }
            }
        });
    }

}