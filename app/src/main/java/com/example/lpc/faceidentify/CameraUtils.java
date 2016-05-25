package com.example.lpc.faceidentify;


import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lpc on 2016/5/25.
 */
public class CameraUtils {

    private int mCurrentCamera = Contants.BACK_CAMERA;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    Camera camera;

    private int imageWidth = Contants.IMAGE_WIDTH;
    private int imageHeight = Contants.IMAGE_HEIGHT;
    private int rate = Contants.RATE;

    public CameraUtils(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
    }


    //选择摄像头进行拍照
    public void switchCamera(int id){
        mCurrentCamera = id;
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int i =0;i < cameraCount ;i++){
            Camera.getCameraInfo(i,cameraInfo);
            if (mCurrentCamera == Contants.BACK_CAMERA){
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                    camera = Camera.open(i);
                }
            }else
            {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                    camera = Camera.open(i);   //打开前置摄像头
                }
            }
        }

        Camera.Parameters parameters = camera.getParameters() ;
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        //对分辨率进行排序
        Collections.sort(sizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                return lhs.width * lhs.height - rhs.width * rhs.height;
            }
        });

        for (int i = 0;i<sizes.size();i++){
            if (sizes.get(i).width >= imageWidth || sizes.get(i).height >= imageHeight || i == sizes.size() - 1){
                imageWidth = sizes.get(i).width;
                imageHeight = sizes.get(i).height;
            }
        }

        parameters.setPictureSize(imageWidth,imageHeight);
        parameters.setPreviewFrameRate(rate);
        parameters.setJpegQuality(80);
        camera.setParameters(parameters);

        startPreview();
    }

    public void startPreview(){
        if (camera != null){
            try{
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    //进行拍照
    public void takePhoto(){
        camera.takePicture(null,null,new MyTakePhotoCallback());
    }

    public final class MyTakePhotoCallback implements Camera.PictureCallback{

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    }

}
