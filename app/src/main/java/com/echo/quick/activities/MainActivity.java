package com.echo.quick.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.echo.quick.contracts.MainContract;
import com.echo.quick.contracts.OnlineWordContract;
import com.echo.quick.presenters.OnlineWordPresenterImpl;
import com.echo.quick.utils.App;

import java.util.HashMap;

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

    private Button btn_login,btn_test,btn_quick;

    public App app;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App)getApplication();



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
                intent = new Intent(MainActivity.this, WordsActivity.class);
                startActivity(intent);
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
                OnlineWordContract.OnlineWordPresenter onlineWordPresenter = new OnlineWordPresenterImpl();
                final HashMap<String, String> map = new HashMap<>();
                map.put("userId", "444");
                map.put("classId", "11");
                app.setList(onlineWordPresenter.getOnlineWord(map));
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }




}
