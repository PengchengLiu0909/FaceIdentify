package com.example.lpc.faceidentify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage;
    private Button mBtnIdentify;
    private Button mBtnChoosePhoto;
    private Button mBtnTakePhoto;
    private TextView mTextInfoShow;
    private FrameLayout mFrameWaitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews(){
        mImage = (ImageView)findViewById(R.id.photos);
        mBtnChoosePhoto = (Button)findViewById(R.id.choosePhoto);
        mBtnIdentify = (Button)findViewById(R.id.identify);
        mBtnTakePhoto = (Button)findViewById(R.id.takePhoto);
        mTextInfoShow = (TextView)findViewById(R.id.infoShow);
    }

    public void initEvents(){

    }

    @Override
    public void onClick(View v) {

    }




}
