package com.example.lpc.faceidentify;

import android.graphics.Bitmap;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by lpc on 2016/5/25.
 */
public class FaceDetect {

    //进行人脸识别，识别结果回调到json中
    public static void detect(final Bitmap image,final IDetectResult result){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpRequests requests = new HttpRequests(Contants.app_key,Contants.app_secret,true,true);
                    Bitmap bitmap = Bitmap.createBitmap(image,0,0,image.getWidth(),image.getHeight());
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    byte[] buffer = outputStream.toByteArray();
                    PostParameters parameters = new PostParameters();
                    parameters.setImg(buffer);
                    JSONObject jsonObject = requests.detectionDetect(parameters);

                    if (result != null){
                        result.success(jsonObject);
                    }
                }catch (FaceppParseException exception){
                    exception.printStackTrace();
                    if (result == null){
                        result.failed(exception);
                    }
                }finally {

                }
            }
        }).start();

    }



    public interface IDetectResult{
        public void success(JSONObject object);
        public void failed(FaceppParseException exception);
    }
}
