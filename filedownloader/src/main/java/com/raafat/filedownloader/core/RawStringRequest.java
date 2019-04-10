package com.raafat.filedownloader.core;

import com.raafat.filedownloader.cache.Cache;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @3:09 AM.
 */
public class RawStringRequest extends Request<String> {

    private Response.SuccessListener<String> successListener;

    public RawStringRequest(
            String url,
            Response.SuccessListener<String> listner,
            Response.ErrorListner errorListner
    ) {
        super(url, errorListner);
        this.successListener = listner;
    }

    @Override
    public Response<String> parseData(byte[] data) {
        String jsonString = new String(data, StandardCharsets.UTF_8);

        return new Response<>(jsonString);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Response cachedResponse = Cache.getInstance().getLruCache().get(this.getKey());
        if (cachedResponse != null) {
            successListener.onResponse(((String) (cachedResponse).getData()));
            this.cancel(true);
            this.finish();
        }
    }

    @Override
    protected void onPostExecute(Response<String> response) {
        super.onPostExecute(response);
        // remove the request from requestQueue
        this.finish();
        if (isShouldCache()) {
            Cache.getInstance().getLruCache().put(this.getKey(), response);
        }
        successListener.onResponse(response.getData());
    }


}
