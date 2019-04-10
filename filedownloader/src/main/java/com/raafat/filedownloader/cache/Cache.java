package com.raafat.filedownloader.cache;

import android.support.v4.util.LruCache;

import com.raafat.filedownloader.core.Response;

/**
 * Created by Raafat Alhoumaidy on 4/6/2019 @2:25 AM.
 */
public class Cache {

    private static Cache mInstance;
    private LruCache<String, Response> lruCache;
    private int cacheSize = 4 * 1024 * 1024; // 4MiB

    public Cache(){
        this.lruCache  = new LruCache<>(cacheSize);
    }

    public Cache(int cacheSize){
        this.cacheSize = cacheSize;
        this.lruCache = new LruCache<>(cacheSize);
    }

    public static Cache getInstance(){
        if(mInstance == null)
            mInstance = new Cache();
        return mInstance;
    }


    public LruCache<String, Response> getLruCache() {
        return lruCache;
    }

    public void setLruCache(LruCache<String, Response> lruCache) {
        this.lruCache = lruCache;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }
}
