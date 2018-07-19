package com.echo.quick.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DebugUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.echo.quick.activities.R;

/**
 * Class name: CustomDialog
 * Specific description :用于展示的单词的详细信息的自定义dialog
 * 创建人: HUAHUA
 * @version :1.0 , 2018/7/19 11:12
 * 修改人：
 * @version :
 * @since ：[quick|背单词]
 */

public class CustomDialog extends Dialog {
    private Context context;
    private int height, width;
    private boolean cancelTouchout;
    private View view;

    public CustomDialog(Builder builder) {
        super(builder.context);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }


    public CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);

        setCanceledOnTouchOutside(cancelTouchout);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = height;
        lp.width = width;
        win.setAttributes(lp);
    }

    public static final class Builder {

        private Context context;
        private int height, width;
        private boolean cancelTouchout;
        private View view;
        private int resStyle = -1;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder heightDimenRes(int dimenRes) {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes) {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder addViewOnclick(int viewRes,View.OnClickListener listener){
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }


        public CustomDialog build() {
            if (resStyle != -1) {
                return new CustomDialog(this, resStyle);
            } else {
                return new CustomDialog(this);
            }
        }
    }
}

