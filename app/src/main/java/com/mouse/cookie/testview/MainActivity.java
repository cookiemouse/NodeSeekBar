package com.mouse.cookie.testview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mouse.cookie.nodeseekbar.NodeSeekBar;

public class MainActivity extends AppCompatActivity{

    private NodeSeekBar mNodeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNodeSeekBar = (NodeSeekBar) findViewById(R.id.nsb_mainactivity);

        mNodeSeekBar.setOnProgressChangedListener(new NodeSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int i) {
                Log.i("Tag_MainActivity", "进度为：" + i);
            }
        });
    }
}
