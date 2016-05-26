package com.example.lpc.faceidentify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import utils.ToastUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage;
    private Button mBtnIdentify;
    private Button mBtnChoosePhoto;
    private Button mBtnTakePhoto;
    private TextView mTextInfoShow;
    private FrameLayout mFrameWaitting;

    private int mAgeAge;    //识别到年龄
    private String mSexSex;   //识别到的性别
    private Bitmap mPhoto;
    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    public void initViews(){
        mImage = (ImageView)findViewById(R.id.photos);
        mBtnChoosePhoto = (Button)findViewById(R.id.choosePhoto);
        mBtnIdentify = (Button)findViewById(R.id.identify);
        mBtnTakePhoto = (Button)findViewById(R.id.takePhoto);
        mTextInfoShow = (TextView)findViewById(R.id.infoShow);
    }

    /**
     * 初始化事件
     * */
    public void initEvents(){
        mBtnChoosePhoto.setOnClickListener(this);
        mBtnTakePhoto.setOnClickListener(this);
        mBtnIdentify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.identify:    //识别脸部信息

                break;
            case R.id.choosePhoto:   //从图库中选择照片
                break;
            case R.id.takePhoto:    //使用摄像头拍摄照片
                break;
            default:
                break;

        }
    }


    /**
     * 将包含识别到的信息进行转化
     * */
    public void jsonReBitmap(JSONObject jsonObject){
        Bitmap bitmap = Bitmap.createBitmap(mPhoto.getWidth(),mPhoto.getHeight(),mPhoto.getConfig());
        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(mPhoto,0,0,null);
        try{
            JSONArray faces = jsonObject.getJSONArray("faces");   //所有识别信息的json数组
            int faceCount = faces.length();      //识别到的人脸数
            for (int i=0;i<faceCount;i++){
                JSONObject face = faces.getJSONObject(i);
                JSONObject position = face.getJSONObject("position");   //脸部位置json
                float x = (float) position.getJSONObject("center").getDouble("x");  //脸部中心的x坐标
                float y = (float) position.getJSONObject("center").getDouble("y");   //y 坐标
                float w = (float) position.getDouble("width");    //脸部区域的宽度
                float h = (float) position.getDouble("height");   //脸部区域的高度

                x = x / 100 * bitmap.getWidth();
                y = y / 100 * bitmap.getHeight();
                w = w / 100 * bitmap.getWidth();
                h = h / 100 * bitmap.getHeight();
                Paint paint = new Paint();    //画脸部的方框
                paint.setColor(0xffffffff);    //画笔颜色为白色
                paint.setStrokeWidth(3);
                canvas.drawLine(x - w / 2, y - h / 2, x - w / 2, y + h / 2,
                        paint);
                canvas.drawLine(x - w / 2, y - h / 2, x + w / 2, y - h / 2,
                        paint);
                canvas.drawLine(x - w / 2, y + h / 2, x + w / 2, y + h / 2,
                        paint);
                canvas.drawLine(x + w / 2, y - h / 2, x + w / 2, y + h / 2,
                        paint);

                mAgeAge = face.getJSONObject("attribute").getJSONObject("age").getInt("value");  //根据脸部判断到的年龄
                mSexSex = face.getJSONObject("attribute").getJSONObject("gender").getString("value");  //判断到的性别
                if ("Male".equals(mSexSex)){
                    mSexSex = "汉子";
                }else{
                    mSexSex = "妹纸";
                }

                mPhoto = bitmap;
            }
            //将识别到的信息显示在textview上面
            mTextInfoShow.setText("发现人脸数:" + faceCount + " 性别:" +mSexSex + " 年龄:" + mAgeAge);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     * */
    public void compressImage(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImagePath,options);
        double radio = Math.max(options.outWidth * 1.0d / 1024f,options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(radio);
        options.inJustDecodeBounds = false;
        mPhoto = BitmapFactory.decodeFile(mImagePath,options);
    }

    private long  mLongExitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (System.currentTimeMillis() - mLongExitTime > 2000){
            mLongExitTime = System.currentTimeMillis();
            ToastUtils.showShortToast("再按一次退出");
        }else
        {
            MyApplication.getInstance().exitApp();
        }
        return super.onKeyDown(keyCode,event);
    }
}
