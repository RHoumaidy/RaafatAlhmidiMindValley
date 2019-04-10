package com.raafat.filedownloader.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raafat Alhoumaidy on 4/6/2019 @3:19 AM.
 * This class holds all the requests that are being made to the server
 */
public class RequestQueue {

    /**
     * we use this integer and the key of the request (the url) to uniquely identify a single request in case we have multiple requests to the same url
     */
    private static int idx = 0;
    private static RequestQueue mInsance;
    private List<Request> requestsList = new ArrayList<>();

    public static RequestQueue getInstance() {
        if (mInsance == null)
            mInsance = new RequestQueue();
        return mInsance;
    }

    public void add(Request request) {
        requestsList.add(request);
        request.setRequestQueueIdx(idx ++);
        request.execute();
    }

    public void remove(Request request) {
       requestsList.remove(request);
    }

}
