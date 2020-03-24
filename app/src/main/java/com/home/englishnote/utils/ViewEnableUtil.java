package com.home.englishnote.utils;

import android.view.View;

public abstract class ViewEnableUtil {

    public static void setViewsVisible(boolean visible, View... views) {
        for (View view : views) {
            view.setVisibility((visible) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public static void setViewsFocusable(boolean enable, View... views) {
        for (View view : views) {
            view.setFocusable(enable);
            view.setFocusableInTouchMode(enable);
            view.setClickable(enable);
            view.setLongClickable(enable);
        }
    }

}
