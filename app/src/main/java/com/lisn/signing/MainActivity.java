package com.lisn.signing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends Activity {
    private ImageView iv;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private int width1;
    private int height1;

    private String titleColor = "#a266c4";

    private void title() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_title);
        ImageView iv = (ImageView) findViewById(R.id.back);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("电子签名");
        String sColor = getIntent().getStringExtra("titleColor");
        if (!TextUtils.isEmpty(sColor)) {
            titleColor = sColor;
        }
        ll.setBackgroundColor(Color.parseColor(titleColor));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView iv_info = (ImageView) findViewById(R.id.iv_info);
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    public int dip2px(float dpValue) {
        final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dip(float pxValue) {
        final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int flag88 = 88;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == flag88) {
                int width = iv.getWidth();
                int height = iv.getHeight();
                System.out.println("iv width=" + width + " iv height=" + height);
                baseBitmap = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);
                System.out.println("图宽度：" + width);
                System.out.println("图高度：" + height);
                canvas = new Canvas(baseBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title();
        iv = (ImageView) findViewById(R.id.iv);
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        int width = (wm.getDefaultDisplay().getWidth());
        int height = (wm.getDefaultDisplay().getHeight());

//        ViewTreeObserver vto2 = iv.getViewTreeObserver();
//        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                iv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                System.out.println("--------"+iv.getHeight()+","+iv.getWidth());
//                width1=iv.getWidth();
//                height1=iv.getHeight();
//            }
//        });

//        ViewTreeObserver vto = iv.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            public boolean onPreDraw() {
//                 height1 = iv.getMeasuredHeight();
//                 width1 = iv.getMeasuredWidth();
//                System.out.println("==============="+height1+","+width1);
//                return true;
//            }
//        });

        handler.sendEmptyMessageDelayed(flag88, 500);
        // 创建一个可以被修改的bitmap
//        baseBitmap = Bitmap.createBitmap(width, height-355,
//        baseBitmap = Bitmap.createBitmap(width, height,


        // 知道用户手指在屏幕上移动的轨迹
        iv.setOnTouchListener(new View.OnTouchListener() {
            // 设置手指开始的坐标
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 手指第一次接触屏幕
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        canvas.drawPoint(startX, startY, paint);
                        iv.setImageBitmap(baseBitmap);
                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕上滑动
                        int newX = (int) event.getX();
                        int newY = (int) event.getY();

                        canvas.drawLine(startX, startY, newX, newY, paint);
                        // 重新更新画笔的开始位置
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        iv.setImageBitmap(baseBitmap);
                        break;
                    case MotionEvent.ACTION_UP: // 手指离开屏幕
                        break;

                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void clear(View view) {
//        baseBitmap.recycle();
        canvas.drawColor(Color.WHITE);
        iv.setImageBitmap(baseBitmap);
    }

    public void save(View view) {
        String qm_path = "null";
        try {
            String path = this.getFilesDir().getPath();
//            File file = new File(Environment.getExternalStorageDirectory(),
            File file1 = new File(path + File.separator + "Qm");
            if (!file1.exists()) {
                file1.mkdirs();
            }


            String child = System.currentTimeMillis() + ".jpg";
            Log.e("ccccc", "save: "+file1.getAbsolutePath()+File.separator+ child);
            qm_path=(file1.getAbsolutePath()+File.separator+ child);
            File file = new File(file1, child);

            FileOutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            Toast.makeText(this, "保存图片成功", Toast.LENGTH_SHORT).show();
            //模拟一个消息通知系统sd卡被重新挂载了
            Intent intent = new Intent();
//            intent.setAction(intent.ACTION_MEDIA_MOUNTED);
            intent.setAction(intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(Environment
                    .getExternalStorageDirectory()));
            sendBroadcast(intent);

        } catch (Exception e) {
            Toast.makeText(this, "保存图片失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e("000000", "save: " + e);
        }
    }

}

