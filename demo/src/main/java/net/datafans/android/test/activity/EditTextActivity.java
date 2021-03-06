package net.datafans.android.test.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import net.datafans.android.common.widget.controller.PlainTableViewController;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.test.R;

public class EditTextActivity extends PlainTableViewController<String> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_activity_plain);

        View inputView = getLayoutInflater().inflate(R.layout.activity_edit_text, null);
        containerParent.addView(inputView);
    }


    @Override
    protected int getStatusBarColor() {
        return Color.RED;
    }

    @Override
    protected String getNavTitle() {
        return "测试";
    }

    @Override
    protected Fragment getRootFragment() {
        return new EditTextFragment();
    }

    @Override
    public int getRows(int section) {
        return 10;
    }

    @Override
    public TableViewCell<String> getTableViewCell(int section, int row) {
        return new EditTextTableViewCell();
    }

    @Override
    public String getEntity(int section, int row) {
        return "hello";
    }

    @Override
    public void onClickRow(int section, int row) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
