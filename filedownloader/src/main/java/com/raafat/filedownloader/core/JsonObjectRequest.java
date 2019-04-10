package com.raafat.filedownloader.core;

import com.raafat.filedownloader.cache.Cache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @3:09 AM.
 */
public class JsonObjectRequest extends Request<JSONObject> {

    private Response.SuccessListener<JSONObject> successListener;

    public JsonObjectRequest(
            String url,
            Response.SuccessListener<JSONObject> listner,
            Response.ErrorListner errorListner
    ) {
        super(url, errorListner);
        this.successListener = listner;
    }

    @Override
    public Response<JSONObject> parseData(byte[] data) {
        try {
            String jsonString = new String(data, StandardCharsets.UTF_8);


            return new Response<>(new JSONObject(jsonString));
        } catch (JSONException je) {
            return new Response<>(je);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Response cachedResponse = Cache.getInstance().getLruCache().get(this.getKey());
        if (cachedResponse != null && cachedResponse.getData() != null) {
            successListener.onResponse(((JSONObject) (cachedResponse).getData()));
            this.cancel(true);
            this.finish();
        }
    }

    @Override
    protected void onPostExecute(Response<JSONObject> response) {
        super.onPostExecute(response);
        // remove the request from requestQueue
        this.finish();
        if (isShouldCache()) {
            Cache.getInstance().getLruCache().put(this.getKey(), response);
        }
        if (response.getData() != null)
            successListener.onResponse(response.getData());
        else if (response.getError() != null)
            errorListner.onErrorResponse(response.getError());
    }


}
