package utils;

import android.widget.Toast;

import com.example.lpc.faceidentify.MyApplication;

/**
 * Created by lpc on 2016/5/26.
 *
 * 提示工具类
 */
public class ToastUtils {

    public static void showShortToast(String info) {
        Toast.makeText(MyApplication.getInstance(), info, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String info){
        Toast.makeText(MyApplication.getInstance(),info,Toast.LENGTH_LONG).show();
    }



}
