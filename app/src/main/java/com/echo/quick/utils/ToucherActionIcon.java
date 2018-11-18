package com.echo.quick.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Class name: ToucherActionIcon
 * Specific description :有个自定义的可以移动和点击的ImageView
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/27 19:39
 * 修改人：
 * @version :
 * @since ：[quick|工具组件]
 */

@SuppressLint("AppCompatCustomView")
public class ToucherActionIcon extends ImageView{
    private OnMyListener mOnmylistenter;
    private int xDelta;//横坐标
    private int yDelta;//纵坐标
    private long startTime = 0;//小球，按下去的时间
    private long endTime = 0;//小球，离开的时间
    private boolean isclick;//是否为点击事件

    public ToucherActionIcon(Context context) {
        super(context);
    }
    /**
     * Method name : ToucherActionIcon
     * Specific description :必须要重写这个构造方法，不然会报错
     *@param   context
     *@param   attrs
     */
    public ToucherActionIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToucherActionIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ToucherActionIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    /**
     * Method name : onTouchEvent
     * Specific description :重写这个方法，实现可以移动控件，并解决点击和触碰我的冲突问题
     *@return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触碰的位置x和y，每次的按下去和移动操作，都会初始化一次按下去的坐标
        final int x = (int) event.getRawX();
        final int y = (int) event.getRawY();
        //确定是触摸事件以后
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            //动作为按下
            case MotionEvent.ACTION_DOWN:
                //获取这个控件的左上角的坐标
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this
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
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this
                        .getLayoutParams();
                //得到按下去的点减原来他们就存在的相对距离，得到左上角的点的坐标
                int xDistance = x - xDelta;
                int yDistance = y - yDelta;
                //将其设置为控件的左上角的坐标
                layoutParams.leftMargin = xDistance;
                layoutParams.topMargin = yDistance;
                //把得到的新的位置设入
                this.setLayoutParams(layoutParams);
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
                System.out.println("执行顺序up"+isclick);
                if (isclick) {
                    ballClick();
            }
                break;

        }
        this.invalidate();
        return true;

    }

    public void ballClick(){
        if (null == mOnmylistenter) {
            //调用接口中的方法
            LogUtils.d("小球失败动作！！！"+mOnmylistenter);
        }else {
            //调用接口中的方法
            LogUtils.d("小球跳转页面到翻译阅读界面！！！"+mOnmylistenter);
        }
    }


    /**
     * interface name : OnMyListener
     * Specific description :自定义接口，实现这个接口必须实现接口的方法
     */
    public  interface OnMyListener{
        void myClick(Boolean b);
    }

    /**
     * Method name : setOnMyClickListener
     * Specific description :利用这个方法，暴露给外面的调用者
     */
    public void setOnMyClickListener(OnMyListener listener) {
        LogUtils.d("用到小球的监听"+mOnmylistenter);
        this.mOnmylistenter = listener;
    }


}
