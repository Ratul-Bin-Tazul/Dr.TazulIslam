package com.ratulbintazul.www.drtazulislam.Util;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * Created by SAMSUNG on 9/30/2017.
 */

public class VideoCaching extends Application {

    public static HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        VideoCaching app = (VideoCaching) context.getApplicationContext();
        return VideoCaching.proxy == null ? (VideoCaching.proxy = VideoCaching.newProxy(context)) : VideoCaching.proxy;
    }

    public static HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer(context);
    }
}