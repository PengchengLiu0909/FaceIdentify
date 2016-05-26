package com.example.lpc.faceidentify;

import android.app.Application;

/**
 * Created by lpc on 2016/5/26.
 */
public class MyApplication extends Application{


    private static MyApplication mInstance;

    public MyApplication(){
        mInstance = this;
    }

    public static MyApplication getInstance(){
        if (mInstance == null){
            synchronized (MyApplication.class){
                if (mInstance == null){
                    mInstance = new MyApplication();
                }
            }
        }
        return mInstance;
    }


    /**
     * 退出程序
     * */
    public static void exitApp(){

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
