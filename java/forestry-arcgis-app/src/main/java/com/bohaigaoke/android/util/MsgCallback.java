package com.bohaigaoke.android.util;

import android.widget.ListView;

public abstract class MsgCallback {
    public abstract void callBack(Boolean result);

    public void callBack(Boolean result, Object[] listItem, Integer index) {
    }

    ;

    public void callBack(Boolean result, Object[] listItem, Integer[] indexs) {
    }

    ;

    public void callBack(Boolean result, ListView listItem, Integer index) {
    }

    ;

    public void callBack(Boolean result, ListView listItem, Integer[] indexs) {
    }

    ;
}
