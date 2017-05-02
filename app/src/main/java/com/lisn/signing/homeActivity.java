package com.lisn.signing;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lisn.signaturelibrary.CamaraActivity;
import com.lisn.signaturelibrary.MyCamaraActivity;
import com.lisn.signaturelibrary.SigningActivity;

import java.io.File;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "homeActivity";
    private Button bt_qm;
    private static final int QMrequestCode=188;
    private Button btn_camara;
    private Button btn_video;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 189;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE=190;
    private Button bt_tsb;
    private Button bt_camara2;
    private static final int intent_camara2_code=191;
    private Button bt_MyCamara;
    private int intentMyCamaraCode=192;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        bt_qm = (Button) findViewById(R.id.bt_qm);
        btn_camara = (Button) findViewById(R.id.btn_camara);
        btn_video = (Button) findViewById(R.id.btn_video);
        bt_tsb = (Button) findViewById(R.id.bt_tsb);
        bt_camara2 = (Button) findViewById(R.id.bt_camara2);
        bt_MyCamara = (Button) findViewById(R.id.bt_MyCamara);

        bt_qm.setOnClickListener(this);
        btn_camara.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        bt_tsb.setOnClickListener(this);
        bt_camara2.setOnClickListener(this);
        bt_MyCamara.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_qm:
//                Intent intent = new Intent(homeActivity.this, QianMingActivity.class);
                Intent intent = new Intent(homeActivity.this, SigningActivity.class);
                startActivityForResult(intent,QMrequestCode);
                break;
            case R.id.btn_camara:
                Intent intent1 = new Intent();
                Uri uri = Uri.fromFile(new File("/mnt/sdcard/"+System.currentTimeMillis()+"zhaopian.jpg"));
                //告诉系统相机照完的照片存放位置
                // set the image file name
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);

//                //此处之所以诸多try catch，是因为各大厂商手机不确定哪个方法
//                try {
//                    intent1.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
//                    startActivityForResult(intent1, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//                } catch (Exception e) {
//                    try {
//                        intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
//                        startActivityForResult(intent1, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//
//                    } catch (Exception e1) {
//                        try {
//                            intent1.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE);
//                            startActivityForResult(intent1, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//                        } catch (Exception ell) {
//                            Toast.makeText(homeActivity.this, "请从相册选择", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }

                intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // 开启相机界面，A界面开启B界面，在B界面关闭的时候将数据回传给A
                startActivityForResult(intent1, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.btn_video:
                //create new Intent
                Intent intent2 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                //录像文件存到哪
                Uri fileUri = Uri.fromFile(new File("/mnt/sdcard/"+System.currentTimeMillis()+"luxiang.mp4"));

                //告诉系统要存哪
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

                // set the video image quality to high
                intent2.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                // 开启录像界面
                startActivityForResult(intent2, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.bt_tsb:
                new ColorPickerDialog(this, new ColorPickerDialog.OnColorChangedListener() {
                    public void colorChanged(int color) {
                        Log.e(TAG, "colorChanged: "+color );
                    }
                }, Color.WHITE).show();
                break;
            case R.id.bt_camara2:
                Intent intent_camara2 = new Intent(homeActivity.this, CamaraActivity.class);
                startActivityForResult(intent_camara2,intent_camara2_code);
                break;
            case R.id.bt_MyCamara:
                Intent myCamaraIntent = new Intent(homeActivity.this, MyCamaraActivity.class);
                startActivityForResult(myCamaraIntent,intentMyCamaraCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "requestCode: " + requestCode+"  resultCode="+resultCode);

        if (requestCode == QMrequestCode) {
            try {
                Log.e(TAG, "onActivityResult: " + data.getStringExtra("qm_path"));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "onActivityResult: " + e);
            }

        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {//照相的界面关闭后回传的数据
            Log.e(TAG, "照片存在" + data.getData().toString());
        } else if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {//照相的界面关闭后回传的数据
            Log.e(TAG, "录像存在" + data.getData().toString());
        }else if (requestCode==intentMyCamaraCode){
            Log.e(TAG, "intentMyCamaraCode: " );
        }
    }

}
