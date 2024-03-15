package com.ambaitsystem.onlinetransfer.app;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.ambaitsystem.onlinetransfer.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import javax.net.ssl.HttpsURLConnection;

public class MyApplication_OnlineTransfer extends Application {
    private static final String ONESIGNAL_APP_ID = "d0e81f60-d437-4916-91ad-5669b1cb20a1";

    public static final String TAG = MyApplication_OnlineTransfer.class
            .getSimpleName();

    private static MyApplication_OnlineTransfer mInstance;
    private RequestQueue mRequestQueue;
    private MyPreferenceManager pref;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        HttpsURLConnection.setDefaultSSLSocketFactory(new com.amitech.allevents.utill.NoSSLv3Compat.NoSSLv3Factory());
    }

    public static synchronized MyApplication_OnlineTransfer getInstance() {
        return mInstance;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
