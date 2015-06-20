package net.datafans.android.common.widget.controller;

import net.datafans.android.common.R;
import net.datafans.android.common.config.AndroidCommon;
import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.lib.systembar.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class Controller extends Activity {

    protected ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        if (enableMargin())
            setContentView(R.layout.activity_base);
        else
            setContentView(R.layout.activity_base_no_margin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // actionBar = getActionBar();
        // actionBar.setHomeButtonEnabled(true);
        // actionBar.setDisplayHomeAsUpEnabled(true);

        initActionBar();
        initFragment();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("InlinedApi")
    private void initActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);

        tintManager.setTintColor(getStatusBarColor());

        // tintManager.setNavigationBarTintColor(Color.RED);

        // 设置一个样式背景给导航栏
        // tintManager.setNavigationBarTintResource(R.drawable.back_comment_tab);
        // 设置一个状态栏资源
        // tintManager.setStatusBarTintDrawable(MyDrawable);
    }

    private void initFragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, getRootFragment());
        transaction.commit();
    }

    protected abstract Fragment getRootFragment();

    protected boolean enableMargin() {
        return true;
    }

    protected int getStatusBarColor() {
        int color = AndroidCommon.getAppearence().getStatusBarTintColor();
        if (color != 0) {
            return color;
        }
        return Color.BLACK;
    }

    protected void onStatusOk(BaseResponse response, Class<?> type) {

    }

    protected void onStatusError(BaseResponse response) {
        Log.e("statusError", response.toString());
        Toast toast = Toast.makeText(this, response.getErrorMsg(),
                Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void onRequestError(int errorCode, byte[] errorResponse,
                                  Throwable throwable) {
        if (errorCode == -2) {
            Log.e("exception", "network exception");
            Toast toast = Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (errorCode == -1) {
            Log.e("exception", "data_parse_exception");
            Toast toast = Toast.makeText(this, "数据解析错误", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}