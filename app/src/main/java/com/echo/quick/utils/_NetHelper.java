package com.echo.quick.utils;


import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by TanzJ on 18-5-22.
 */

public class _NetHelper {

//    private static String IP = Config.getNetConfigProperties().getProperty("ip");
//    private static String IP = "193.112.12.207";
    private static String IP = "172.16.4.19";
//    private static String PORT = Config.getNetConfigProperties().getProperty("port");

    private static String PORT = "8080";

    public static String DOMAIN = "http://"+IP+':'+PORT + '/';

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private static OkHttpClient okHttpClient = new OkHttpClient();


    static public class PostHelper {

        private Request.Builder builder;

        //执行发送POST HTTP请求的方法
        //json形式发送
        public void doPost(JSONObject info, String url, Callback callback) {

            RequestBody requestBody = RequestBody.create(JSON, info.toJSONString());
            Request request;
            request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            requestBody.toString();
            call.enqueue(callback);
            builder = null;
        }
        //form形式执行异步请求发送
        public void doPost(String content, String url, Callback callback) {

            RequestBody requestBody = RequestBody.create(FORM,content);
            LogUtils.d(requestBody.toString()+"    "+content);
            Request request;
            request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
            builder = null;
        }

        //form形式执行同步请求发送
        public void doPostString(String content, String url, Callback callback) {

            RequestBody requestBody = RequestBody.create(FORM,content);
            Request request;
            request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            requestBody.toString();
            try {
                call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder = null;
        }

        private static void execute(Request request){
            okhttp3.Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                String repo = response.body().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static public class GetHelper {

        private Request.Builder builder;

        //执行发送GET HTTP请求的方法
        public void doGet(String url, Callback callback){

            Request request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .get()
                    .build();

            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
            builder = null;
        }

        /**
         *  如果想要额外的设置Request对象的属性，可以使用这个方法
         */
        public void setBuilder(Request.Builder builder) {
            this.builder = builder;
        }
    }

    static public class PatchHelper {

        private Request.Builder builder;

        //执行发送PATCH HTTP请求的方法
        public void doPatch(String accessToken,JSONObject body,String url,Callback callback) {

            RequestBody requestBody = RequestBody.create(JSON, body.toJSONString());
            Request request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .addHeader("Authorization","Bearer "+ accessToken)
                    .patch(requestBody)
                    .build();

            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
            builder = null;
        }

        /**
         *  如果想要额外的设置Request对象的属性，可以使用这个方法
         */
        public void setBuilder(Request.Builder builder) {
            this.builder = builder;
        }
    }

    static public class UploadAvatarHelper {
        private Request.Builder builder;

        //执行发送PATCH HTTP请求的方法
        public void doUpload(String accessToken, File file, String url, Callback callback) {

            MediaType type=MediaType.parse("image/jpg");
            RequestBody fileBody = RequestBody.create(type, file);
            RequestBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"file\"; filename=\"avatar.jpg\"")
                            , fileBody)
                    .build();
            Request request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .addHeader("Authorization","Bearer "+ accessToken)
                    .patch(multipartBody)
                    .build();


            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
            builder = null;
        }

        /**
         *  如果想要额外的设置Request对象的属性，可以使用这个方法
         */
        public void setBuilder(Request.Builder builder) {
            this.builder = builder;
        }
    }

    //上传文件的方法先注释
//    static public class UploadFileHelper {
//        private Request.Builder builder;
//
//        //执行发送PATCH HTTP请求的方法
//        public void doUpload(String accessToken, File file, String url, Callback callback) {
//
//            String contentType  = new MimetypesFileTypeMap().getContentType(file);
//            MediaType type=MediaType.parse(contentType);
//            RequestBody fileBody = RequestBody.create(type, file);
//            RequestBody multipartBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addPart(Headers.of(
//                            "Content-Disposition",
//                            "form-data; name=\"file\";" +
//                            " filename=\""+file.getName()+"\"")
//                            , fileBody)
//                    .build();
//            Request request = (builder!=null?builder:new Request.Builder())
//                    .url(url)
//                    .addHeader("Authorization","Bearer "+ accessToken)
//                    .post(multipartBody)
//                    .build();
//
//
//            Call call = okHttpClient.newCall(request);
//            call.enqueue(callback);
//            builder = null;
//        }
//
//        /**
//         *  如果想要额外的设置Request对象的属性，可以使用这个方法
//         */
//        public void setBuilder(Request.Builder builder) {
//            this.builder = builder;
//        }
//    }

    static public class DeleteHelper {

        private Request.Builder builder;

        //执行发送PATCH HTTP请求的方法
        public void doDelete(String accessToken,JSONObject body,String url,Callback callback) {

            RequestBody requestBody = null;
            if (body!=null)
                requestBody = RequestBody.create(JSON, body.toJSONString());
                Request request = (builder!=null?builder:new Request.Builder())
                    .url(url)
                    .addHeader("Authorization","Bearer "+ accessToken)
                    .delete(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(callback);
            builder = null;
        }

        /**
         *  如果想要额外的设置Request对象的属性，可以使用这个方法
         */
        public void setBuilder(Request.Builder builder) {
            this.builder = builder;
        }

    }
}
