package com.example.lpc.faceidentify;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lpc on 2016/5/25.
 */
public class CameraUtils {

    private int mCurrentCamera = Contants.BACK_CAMERA;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    Camera camera;

    private int imageWidth = Contants.IMAGE_WIDTH;


    public CameraUtils(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
    }


    //选择摄像头进行拍照
    public void switchCamera(int id){

    }

}
