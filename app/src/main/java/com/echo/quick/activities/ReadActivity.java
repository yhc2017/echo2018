package com.echo.quick.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.echo.quick.adapter.StrangeFragmentAdapter;
import com.echo.quick.fragment.ReadingFragment;
import com.echo.quick.pojo.Pager;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;
import com.echo.quick.utils.ToucherActionIcon;

import java.util.ArrayList;
/**
 * Class name: ReadActivity
 * Specific description :阅读页面
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/25 22:38
 * 修改人：茹韶燕
 * @version : 把那个特定的控件单独拿出，作为一个工具类
 * @since ：[quick|阅读模块]
 */
public class ReadActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragmentList;
    private ViewPager viewPager;
    private StrangeFragmentAdapter adapter;
    public static final String TAG = "小球的位置：";
    ToucherActionIcon toucherActionIcon;//移动小球的控件类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initView();
        initball();
        viewPager = (ViewPager)findViewById(R.id.vp_coupon);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new PagerListener());
    }

    /**
     * Method name : initball
     * Specific description :小球的控件初始化
     */
    private void initball(){
        toucherActionIcon = (ToucherActionIcon) findViewById(R.id.action_icon);
        toucherActionIcon.setX(900);
        toucherActionIcon.setY(20);
        //移动小球的控件
        toucherActionIcon = new ToucherActionIcon(this);
        toucherActionIcon.setOnMyClickListener(new ToucherActionIcon.OnMyListener() {
            @Override
            public void myClick(Boolean isclick) {
                if (isclick){
                    Intent intent = new Intent(ReadActivity.this, ReadingTranslateActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * Method name : initView
     * Specific description :viewpager添加fragmnet
     */
    private void initView() {
        fragmentList = new ArrayList<Fragment>();
        App app = (App)getApplication();
        String content = app.getContent();
        LogUtils.d(content);
        fragmentList.add(newInstance(content));
        fragmentList.add(newInstance(content));
        adapter = new StrangeFragmentAdapter(getSupportFragmentManager(),fragmentList);

    }
    /**
     * Method name : newInstance
     * Specific description :传递数据给fragment
     *@param
     *@param
     *@return
     */
    public static ReadingFragment newInstance(String content) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Class name :
     * Specific description :实现viewpager页面变化的监听
     */
    public class PagerListener implements ViewPager.OnPageChangeListener {

        /**
         * 在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用
         * @param position 当前页面
         * @param positionOffset 当前页面偏移的百分比
         * @param positionOffsetPixels 当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 更新界面
//            maction.layout(l, t, r, b);
        }

        /**
         * 页面跳转完毕的时候调用
         * @param position 选中的页面的Position（位置编号）
         */
        @Override
        public void onPageSelected(int position) {
        }

        /**
         * viewpager是否处于滚动状态
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

