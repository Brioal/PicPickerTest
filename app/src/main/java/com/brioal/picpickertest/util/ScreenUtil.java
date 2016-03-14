package com.brioal.picpickertest.util;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

/**
 * Created by brioal on 16-3-12.
 */
public class ScreenUtil {
    public static int screenWidh;
    public static int screenHeight;
    private static Context mContext  = null;
    public static void init(Context context) {
        mContext= context;
    }

    public static int getScreenWidh() {
        if (screenWidh != 0) {
            return screenWidh;
        }
        Display display = ((AppCompatActivity) mContext).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenWidh = point.x;

        return screenWidh;
    }


    public static int getScreenHeight() {
        if (screenHeight != 0) {
            return screenHeight;
        }

        Display display = ((AppCompatActivity) mContext).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        screenHeight = point.y;
        return screenHeight;
    }
}
