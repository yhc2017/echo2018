package com.echo.quick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.echo.quick.adapter.StrangeFragmentAdapter;
import com.echo.quick.fragment.ReadingFragment;
import com.echo.quick.utils.App;
import com.echo.quick.utils.LogUtils;

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
    private int xDelta;//横坐标
    private int yDelta;//纵坐标
    private long startTime = 0;//小球，按下去的时间
    private long endTime = 0;//小球，离开的时间
    private boolean isclick;//是否为点击事件
//    ToucherActionIcon toucherActionIcon;//移动小球的控件类
    ImageView maction;
    ActionIcon actionIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ActivityManager.getInstance().addActivity(this);
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
//        toucherActionIcon = (ToucherActionIcon) findViewById(R.id.action_icon);
//        toucherActionIcon.setX(900);
//        toucherActionIcon.setY(20);
//        //移动小球的控件
//        toucherActionIcon = new ToucherActionIcon(this);
//        toucherActionIcon.setOnMyClickListener(new ToucherActionIcon.OnMyListener() {
//            @Override
//            public void myClick(Boolean isclick) {
//                if (isclick){
//                    Intent intent = new Intent(ReadActivity.this, ReadingTranslateActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        maction = (ImageView) findViewById(R.id.action_icon);
        maction.setX(900);
        maction.setY(20);
        actionIcon = new ActionIcon();
        maction.setOnTouchListener(actionIcon);

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

    /**
     * 方法名称：
     * 方法描述:
     * 参数1： 参数说明
     * @return [返回类型说明]
     **/

    public class ActionIcon implements View.OnTouchListener{





        /**
         * Method name : onTouchEvent
         * Specific description :重写这个方法，实现可以移动控件，并解决点击和触碰我的冲突问题
         *@return boolean
         */

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            {
                //触碰的位置x和y，每次的按下去和移动操作，都会初始化一次按下去的坐标
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                //确定是触摸事件以后
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    //动作为按下
                    case MotionEvent.ACTION_DOWN:
                        //获取这个控件的左上角的坐标
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        //得到按下去的点和这个控件的左上点的坐标x,y的相对距离
                        xDelta = x - params.leftMargin;
                        yDelta = y - params.topMargin;
                        //按下去时候的系统时间
                        startTime = System.currentTimeMillis();
                        LogUtils.d("开始时间：" + startTime);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取这个控件的宽高
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view
                                .getLayoutParams();
                        //得到按下去的点减原来他们就存在的相对距离，得到左上角的点的坐标
                        int xDistance = x - xDelta;
                        int yDistance = y - yDelta;
                        //将其设置为控件的左上角的坐标
                        layoutParams.leftMargin = xDistance;
                        layoutParams.topMargin = yDistance;
                        //把得到的新的位置设入
                        view.setLayoutParams(layoutParams);
                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏幕对应事件
                        //up时候的系统时间
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
                            Intent intent = new Intent(ReadActivity.this,ReadingTranslateActivity.class);
                            startActivity(intent);


                        }
                        break;
                }

                view.invalidate();
                return true;
            }
        }
    }

}

