package com.raafat.filedownloader.core;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.raafat.filedownloader.cache.Cache;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Raafat Alhoumaidy on 4/6/2019 @3:02 AM.
 */
public abstract class Request<T> extends AsyncTask<Void, Void, Response<T>> {

    protected Response.ErrorListner errorListner;

    private int requestQueueIdx;
    private String url;
    private boolean shouldCache = true;

    public Request() {

    }

    public Request(String url) {
        this.url = url;
    }

    public Request(String url, Response.ErrorListner errorListner) {
        this(url);
        this.errorListner = errorListner;

    }


    public Request(String url, Response.ErrorListner errorListner, boolean shouldCache) {
        this(url, errorListner);
        this.shouldCache = shouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        this.shouldCache = shouldCache;
    }

    public boolean isShouldCache() {
        return shouldCache;
    }


    public String getUrl() {
        return url;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        assert obj != null;
        return this.url.equals(((Request) obj).getUrl()) && this.requestQueueIdx == ((Request) obj).requestQueueIdx;
    }

    public String getKey() {
        return Utils.hashString(url);
    }


    public int getRequestQueueIdx() {
        return requestQueueIdx;
    }

    public void setRequestQueueIdx(int requestQueueIdx) {
        this.requestQueueIdx = requestQueueIdx;
    }


    public void finish() {
        if (requestQueueIdx >= 0)
            RequestQueue.getInstance().remove(this);
    }


    /**
     * Override this function to parse the response according to the request type
     **/
    public abstract Response<T> parseData(byte[] data);

    public void execute() {
        try {
            super.execute();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            errorListner.onErrorResponse(e);
        }
    }

    /**
     * here we make connection to the url and get the byte[] array and parse it using the parse data abstract function
     **/
    @Override
    protected Response<T> doInBackground(Void... voids) {
        try {
            URL url = new URL(getUrl());
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            InputStream inputStream = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                byteArrayOutputStream.write(data, 0, count);
            }
            return parseData(byteArrayOutputStream.toByteArray());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new Response<>(e);
        } catch (IOException e) {
            e.printStackTrace();
            return new Response<>(e);
        }

    }


}
