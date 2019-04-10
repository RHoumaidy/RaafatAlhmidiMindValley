package com.raafat.filedownloader.core;

/**
 * Created by Raafat Alhoumaidy on 4/6/2019 @3:35 AM.
 */
public class Response<T> {

    private T data;
    private Exception error;


    public Response(T data) {
        this.data = data;
        this.error = null;
    }

    public Response(Exception e){
        this.data = null;
        this.error = e;
    }

    public T getData() {
        return data;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public interface SuccessListener<T> {
        /**
         * Called when a response is received.
         */
        void onResponse(T response);
    }

    public interface ErrorListner {
        /**
         * Callback method that an error has been occurred
         **/
        void onErrorResponse(Exception error);
    }

}
