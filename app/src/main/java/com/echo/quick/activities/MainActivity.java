package com.echo.quick.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.echo.quick.contracts.MainContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.model.dao.impl.WordsLogImpl;
import com.echo.quick.model.dao.impl.WordsStatusImpl;
import com.echo.quick.model.dao.interfaces.IWordsLogDao;
import com.echo.quick.model.dao.interfaces.IWordsStatusDao;
import com.echo.quick.pojo.User;
import com.echo.quick.pojo.Words_Log;
import com.echo.quick.pojo.Words_Status;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;

import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 文件名：MainActivity
 * 创建人：周少侠
 * 创建时间：2018/7/16 22:54
 *
 * 修改人：
 * 修改时间：
 * 修改内容：
 *
**/

public class MainActivity extends AppCompatActivity implements MainContract.IMainView{
    Button mbt1,mbt2,mbtdialog;


    /**
     *属性注释说明
     *
     * 使用方法
     * 表单提交的方法向服务器提交账号或者是密码
     final HashMap<String, String> map = new HashMap<>();
     map.put("qs","1008611");
     manager.sendComplexForm(post_path,map, new OKhttpManager.Func1() {
    @Override
    public void onResponse(String result) {
    Log.e("TAG"+map.getClass().getName(),result);
    }
    });
     *
     */
//    private OKhttpManager manager = OKhttpManager.getInstance();

    private Button btn_login,btn_test,btn_quick,btn_study,btn_re_study,btn_test_db,btn_test_db2,btn_view;

    private TextView textView3;

    private ProgressBar progressBar;

    OnlineWordContract.OnlineWordPresenter onlineWordPresenter;


    public App app;

    Handler mHandler = null;
    Runnable mRunnable = null;

    /**
     * 需要申请的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
    };


    /**
     * 方法名称：
     * 方法描述:
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App)getApplication();

        onlineWordPresenter = new OnlineWordPresenterImpl(this);

        textView3 = (TextView)findViewById(R.id.textView3);
        btn_view = (Button)findViewById(R.id.btn_view);
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserMessageActivity.class));
            }
        });



        //预存在线单词（仅测试用）
//        OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
//        final HashMap<String, String> map = new HashMap<>();
//        map.put("userId", "444");
//        map.put("classId", "11");
//        app.setList(onlineWordPresenter.getOnlineWord(map));

        mbt1 = (Button)findViewById(R.id.bt_words);
        mbt1.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View view) {
                getWordStatus(true);
            }
        });
        mbt2 = (Button)findViewById(R.id.bt_words_two);
        mbt2.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View view) {
//                intent = new Intent(MainActivity.this, Words2Activity.class);
//                startActivity(intent);
            }
        });
        /**
         * Specific description :增加 mbtdialog 按钮测试dialog
         *可以在这里设置数据
         */
        mbtdialog = (Button)findViewById(R.id.bt_words_dialog);
        mbtdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, ReadActivity.class);
                startActivity(intent);
            }
        });
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        btn_test = (Button)findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MediaPlayer mediaPlayer = new MediaPlayer();
//                try {
//                    mediaPlayer.reset();
//                    mediaPlayer.setDataSource("https://dictionary.blob.core.chinacloudapi.cn/media/audio/george/e6/54/E65474CFB3DE4E42B9DBFB6BF4777C0C.mp3");
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });

        btn_quick = (Button)findViewById(R.id.btn_quick);
        btn_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

        Button btn_strange = (Button)findViewById(R.id.btn_strange);
        btn_strange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StrangeWordsListActivity.class));
            }
        });

        btn_study = (Button)findViewById(R.id.btn_study);
        btn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWordStatus(true);
            }
        });

        btn_re_study = (Button)findViewById(R.id.btn_re_study);
        btn_re_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWordStatus(false);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.PB_words);
        progressBar.setMax(3500);
        progressBar.setProgress(100);

        btn_test_db = (Button)findViewById(R.id.btn_test_db);
        btn_test_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWordsLogDao wordsLogDao = new WordsLogImpl();
                String str = "";
                for(Words_Log wordsLog:wordsLogDao.select()){
                    str += wordsLog.getWord()+"  "+wordsLog.getLeftNum()+"    "+wordsLog.getRightNum()+"topicId"+wordsLog.getTopicId()+"\n";
                }
                textView3.setText(str);
            }
        });

        btn_test_db2 = (Button)findViewById(R.id.btn_test_db2);
        btn_test_db2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IWordsStatusDao words = new WordsStatusImpl();
                String str = "";
                for(Words_Status word:words.select()){
                    str += word.getWord()+word.getStatus()+"\n";
                }
                textView3.setText(str);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void showPL(Boolean isShow){

    }


    @Override
    public void OverShowPL(Boolean isShow) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIcon(R.drawable.boy);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在生成推荐结果");
        if(isShow){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }
    }

    public void popWindow(final Context context, final Boolean learn){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("今日份已完成");
//        final EditText editText;
//        builder.setView(editText = new EditText(AthleticsActivity.this));
        builder.setIcon(R.drawable.boy);
        builder.setNeutralButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("继续下一组", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", app.getUserId());
                map.put("topicId", app.getTopicId());
                onlineWordPresenter.getOnlineWordReviewOrLearn(map, "learn");
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setIcon(R.drawable.boy);
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage("正在加载中");
                progressDialog.show();
                mHandler = new Handler();
                mRunnable = new Runnable() {
                    @Override
                    public void run()
                    {

                        IWordsStatusDao iWordsStatusDao = new WordsStatusImpl();
                        if(iWordsStatusDao.selectByStatusAndTopicId("learn_", app.getTopicId()).size() >1){
                            progressDialog.dismiss();
                            getWordStatus(learn);
                        }

                    }
                };
                mHandler.postDelayed(mRunnable, 1000);
            }
        });
        builder.show();
    }


    public void getWordStatus(Boolean learn){

        IWordsStatusDao statusDao = new WordsStatusImpl();
        List<Words_Status> wordLearn = statusDao.selectByStatusAndTopicId("learn_", app.getTopicId());
        List<Words_Status> wordReview = statusDao.selectByStatusAndTopicId("review", app.getTopicId());
        if(statusDao.selectCountByStatusAndTopicId("", app.getTopicId()) != 0){
            if(learn){
                wordLearn.addAll(wordReview);
                app.setStatusList(wordLearn);
            }else {
                wordReview.addAll(wordLearn);
                app.setStatusList(wordReview);
            }
            Intent intent = new Intent(MainActivity.this, WordsActivity.class);
            startActivity(intent);
        }else {
            popWindow(MainActivity.this, learn);
        }
    }

    protected void onPause() {
        super.onPause();
        try {
            mHandler.removeCallbacks(mRunnable);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
