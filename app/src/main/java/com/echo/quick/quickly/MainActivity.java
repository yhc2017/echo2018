package com.echo.quick.quickly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 * @author zhou-jx
 * @time 2018/7/16 20:22*
 * @description 自动创建的MainActivity
 *
**/

public class MainActivity extends AppCompatActivity {
    Button mbt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbt1 = (Button)findViewById(R.id.bt_words);
        mbt1.setOnClickListener(new View.OnClickListener() {
            Intent intent = null;
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,WordsActivity.class);
                startActivity(intent);
            }
        });
    }
}
