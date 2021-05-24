package com.byted.camp.todolist.operation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.byted.camp.todolist.R;

public class SettingActivity extends AppCompatActivity {

    private static final String KEY_IS_NEED_SORT = "is_need_sort";

    private Switch commentSwitch;
    private SharedPreferences mSharedPreferences;

    public static String getSPName() {
        return KEY_IS_NEED_SORT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //todo 从sp读出数据更新isOpen字段
        boolean isOpen = false;
        mSharedPreferences = getSharedPreferences(getApplication().getPackageName(), Context.MODE_PRIVATE);

        commentSwitch = findViewById(R.id.switch_comment);
        commentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //todo 存储开关值进sp
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean(KEY_IS_NEED_SORT, isChecked);
                editor.apply();
                commentSwitch.setChecked(isChecked);
                setResult(Activity.RESULT_OK);
            }
        });
        commentSwitch.setChecked(isOpen);
    }

}
