package com.echo.quick.quickly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.echo.quick.quickly.utils.OKhttpManager;

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

public class MainActivity extends AppCompatActivity {
    Button mbt1;


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
    private OKhttpManager manager = OKhttpManager.getInstance();

    private Button btn_login;

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

        mbt1 = (Button)findViewById(R.id.bt_words);
        mbt1.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, WordsActivity.class);
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

    }



}
