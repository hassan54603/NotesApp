package com.example.notesapp;

import android.app.Application;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Controller extends Application {
    private static final String TAG = Application.class.getSimpleName();
    private static Controller instance;
    RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized Controller getInstance(){
        return instance;
    }

    private RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue (Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}

