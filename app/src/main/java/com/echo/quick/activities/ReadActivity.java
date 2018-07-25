package com.echo.quick.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
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
import com.echo.quick.utils.LogUtils;
import java.util.ArrayList;
/**
 * Class name: ReadActivity
 * Specific description :阅读页面
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/25 22:38
 * 修改人：
 * @version :
 * @since ：[quick|阅读模块]
 */
public class ReadActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragmentList;
    private ViewPager viewPager;
    private StrangeFragmentAdapter adapter;
    //可以移动的小球
    private ImageView maction;
    public static final String TAG = "小球的位置：";
    ToucherActionIcon toucherActionIcon;//移动小球的类

    private ViewGroup mViewGroup;//他最大的活动范围
    private int xDelta;//横坐标
    private int yDelta;//纵坐标
    private long startTime = 0;//小球，按下去的时间
    private long endTime = 0;//小球，离开的时间
    private boolean isclick;//是否为点击事件



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
        maction = (ImageView) findViewById(R.id.action_icon);
        mViewGroup = (ViewGroup) findViewById(R.id.root);
        maction = (ImageView) findViewById(R.id.action_icon);
        maction.setX(900);
        maction.setY(20);
        //移动小球的类
        toucherActionIcon = new ToucherActionIcon();
        maction.setOnTouchListener(toucherActionIcon);
    }

    /**
     * Method name : initView
     * Specific description :viewpager添加fragmnet
     */
    private void initView() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new ReadingFragment());
        fragmentList.add(new ReadingFragment());
        fragmentList.add(new ReadingFragment());
        fragmentList.add(new ReadingFragment());
        adapter = new StrangeFragmentAdapter(getSupportFragmentManager(),fragmentList);

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

    /**
     * Method name :
     * Specific description :存简单数据 小
    /**
     * class name : ToucherActionIcon
     * Specific description :actionbutton的移动和双击解决
     */
    public class ToucherActionIcon implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final int x = (int) event.getRawX();
            final int y = (int) event.getRawY();
            LogUtils.d(TAG, "onTouch: x= " + x + "y=" + y);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    xDelta = x - params.leftMargin;
                    yDelta = y - params.topMargin;
                    startTime = System.currentTimeMillis();
                    LogUtils.d("开始时间：" + startTime);
                    LogUtils.d(TAG, "ACTION_DOWN: xDelta= " + xDelta + "yDelta=" + yDelta);
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    int xDistance = x - xDelta;
                    int yDistance = y - yDelta;
                    LogUtils.d(TAG, "ACTION_MOVE: xDistance= " + xDistance + "yDistance=" + yDistance);
                    layoutParams.leftMargin = xDistance;
                    layoutParams.topMargin = yDistance;
                    view.setLayoutParams(layoutParams);
                    break;
                case MotionEvent.ACTION_UP:// 手指离开屏幕对应事件
                    // 记录最后图片在窗体的位置
                    endTime = System.currentTimeMillis();
                    LogUtils.d("结束时间：" + endTime);
                    //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                    if ((endTime - startTime) > 0.1 * 1000L) {
                        isclick = false;
                    } else {
                        isclick = true;
                    }
                    System.out.println("执行顺序up");
                    if (isclick) {
                        Intent intent = new Intent(ReadActivity.this, ReadingTranslateActivity.class);
                        startActivity(intent);
                    }
                    break;
            }


            mViewGroup.invalidate();
            return true;
        }


    }



}

