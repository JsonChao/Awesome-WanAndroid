package json.chao.com.wanandroid.test;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.ui.main.activity.AboutUsActivity;

/**
 * @author quchao
 * @date 2018/6/6
 */
public class TestActivity extends AppCompatActivity {

    private CheckBox mCheckBox;
    private FrameLayout mFragmentGroup;
    private String lifeCycleStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeCycleStatus = "onCreate";
        setContentView(R.layout.activity_test);
        mCheckBox = ((CheckBox) findViewById(R.id.checkBox));
        mFragmentGroup = ((FrameLayout) findViewById(R.id.fragment_group));

    }

    @Override
    protected void onStart() {
        super.onStart();
        lifeCycleStatus = "onStart";
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifeCycleStatus = "onResume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifeCycleStatus = "onPause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifeCycleStatus = "onStop";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lifeCycleStatus = "onRestart";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifeCycleStatus = "onDestroy";
    }

    public String getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void jumpAboutActivity(View view) {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    public void showToast(View view) {
        Toast.makeText(this, "hahaha", Toast.LENGTH_SHORT).show();
    }

    public void showDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Dialog")
                .setMessage("showDialog")
                .create();
        alertDialog.show();
    }

    public void inverse(View view) {
        mCheckBox.setChecked(!mCheckBox.isChecked());
    }

    public void startTestFragment(TestFragment testFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_group, testFragment);
        transaction.commitAllowingStateLoss();
    }


}
