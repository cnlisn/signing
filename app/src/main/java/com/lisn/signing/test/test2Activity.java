package com.lisn.signing.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lisn.signing.R;

import java.util.Timer;
import java.util.TimerTask;

public class test2Activity extends AppCompatActivity implements View.OnClickListener {

    private long delay = 1000 * 3;

    private int what = 88;
    private int c;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == what) {
                tv.setText("c=" + (c++));
            }
        }
    };
    private TextView tv;
    private Button start;
    private Button stop;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (timer != null) {
                    timer.purge();
                    timer.cancel();
                    timer = null;
                }
                timer = new Timer();
                timer.schedule(new MyTimerTask(), 0, delay);
                break;
            case R.id.stop:
                if (timer != null) {
                    timer.purge();
                    timer.cancel();
                }
                break;
        }
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.sendEmptyMessage(what);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.purge();
        timer.cancel();
    }
}

