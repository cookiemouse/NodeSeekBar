package com.mouse.cookie.testview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mouse.cookie.nodeseekbar.NodeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private NodeSeekBar mNodeSeekBar, mNodeSeekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNodeSeekBar = (NodeSeekBar) findViewById(R.id.nsb_mainactivity);
        mNodeSeekBar2 = (NodeSeekBar) findViewById(R.id.nsb_mainactivity_2);

        List<String> lists = new ArrayList<>();
        lists.add("X1");
        lists.add("X2");
        lists.add("X3");
        lists.add("X4");
        mNodeSeekBar.setString(lists);
        mNodeSeekBar2.setString(lists);

        mNodeSeekBar.setOnProgressChangedListener(new NodeSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int i) {
                Log.i("Tag_MainActivity", "进度为：" + i);
            }
        });
    }
}
