package com.lisn.signing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;



public class Main2Activity extends AppCompatActivity {



    private SeekBar sb;
    private ImageView iv;
    private Button btn;
    private Spinner sp;
    private String[] color ;

    private Bitmap bm;
    private Bitmap copy;
    private Canvas canvas;
    private Paint paint;
    private File file;
    private int yanse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //拿到在xml文件中定义的颜色数组
        color = getResources().getStringArray(R.array.color) ;
        //实例化
        initData();
        //设置监听器
        setLister();
        //画画
        loadingImage();

    }
    private void loadingImage() {
        // 加载原始图片
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        // 需要创建一个和原始的图片大小一样的空白图片(一张纸,上面没有任何数据)
        copy = bm.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        // 需要一个画板,画板上铺上白纸
        canvas= new Canvas(copy);
        // 创建画笔
        paint= new Paint();

        // 给imageView空间加载一个滑动监听器
        iv.setOnTouchListener(new View.OnTouchListener() {
            int startx;
            int starty;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 拿到动作
                int type = event.getAction();
                switch (type) {
                    case MotionEvent.ACTION_DOWN:
                        startx = (int) event.getX();
                        starty = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int endx = (int) event.getX();
                        int endy = (int) event.getY();
                        //画画
                        canvas.drawLine(startx, starty, endx, endy, paint);
                        startx = (int) event.getX();
                        starty = (int) event.getY();
                        iv.setImageBitmap(copy);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });

    }
    private void setLister() {
        //下拉框
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你点击的是：" +  color[position], Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 1:
                        paint.setColor(Color.GREEN);
                        break;
                    case 2:
                        paint.setColor(Color.BLUE);
                        break;
                    case 3:
                        paint.setColor(Color.BLACK);
                        break;
                    case 4:
                        paint.setColor(Color.YELLOW);
                        break;
                    case 0:
                        paint.setColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        //保存
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //指定图片的存储路径
                    file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/draw.png");
                    FileOutputStream fos = new FileOutputStream(file);
                    copy.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show() ;
                } catch (Exception e) {
                }
                //欺骗系统,告诉系统插入一个sd卡
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_MEDIA_MOUNTED);
                intent.setData(Uri.fromFile(file));
                sendBroadcast(intent);
            }
        });
    }
    private void initData() {
        sb = (SeekBar) findViewById(R.id.sb);
        btn = (Button) findViewById(R.id.btn);
        sp = (Spinner) findViewById(R.id.sp);
        iv = (ImageView) findViewById(R.id.iv);
    }
}
