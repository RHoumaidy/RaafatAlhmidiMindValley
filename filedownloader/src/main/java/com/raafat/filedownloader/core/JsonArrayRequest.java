package com.raafat.filedownloader.core;

import android.graphics.Bitmap;

import com.raafat.filedownloader.cache.Cache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @3:09 AM.
 */
public class JsonArrayRequest extends Request<JSONArray> {

    private Response.SuccessListener<JSONArray> successListener;

    public JsonArrayRequest(
            String url,
            Response.SuccessListener<JSONArray> listner,
            Response.ErrorListner errorListner
    ) {
        super(url, errorListner);
        this.successListener = listner;
    }

    @Override
    public Response<JSONArray> parseData(byte[] data) {
        try {
            String jsonString = new String(data, StandardCharsets.UTF_8);


            return new Response<>(new JSONArray(jsonString));
        } catch (JSONException je) {
            return new Response<>(je);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Response cachedResponse = Cache.getInstance().getLruCache().get(this.getKey());
        if (cachedResponse != null && cachedResponse.getData() != null) {
            successListener.onResponse(((JSONArray) (cachedResponse).getData()));
            this.cancel(true);
            this.finish();
        }
    }

    @Override
    protected void onPostExecute(Response<JSONArray> response) {
        super.onPostExecute(response);
        // remove the request from requestQueue
        this.finish();
        if (isShouldCache()) {
            Cache.getInstance().getLruCache().put(this.getKey(), response);
        }
        if (response.getData() != null)
            successListener.onResponse(response.getData());
        else if(response.getError() != null)
            errorListner.onErrorResponse(response.getError());
    }


}
