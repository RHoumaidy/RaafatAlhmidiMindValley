package com.raafat.filedownloader.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.raafat.filedownloader.cache.Cache;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Raafat Alhoumaidy on 4/6/2019 @4:39 AM.
 */
public class ImageRequest extends Request<Bitmap> {

    private final Response.SuccessListener<Bitmap> successListener;
    private final Bitmap.Config mDecodeConfig;
    private final int mMaxWidth;
    private final int mMaxHeight;
    private final ImageView.ScaleType mScaleType;


    public ImageRequest(
            String url,
            Response.SuccessListener<Bitmap> listener,
            int mMaxWidth,
            int mMaxHeight,
            ImageView.ScaleType mScaleType,
            Bitmap.Config mDecodeConfig,
            Response.ErrorListner errorListner
    ) {

        super(url, errorListner);
        this.mDecodeConfig = mDecodeConfig;
        this.mMaxWidth = mMaxWidth;
        this.mMaxHeight = mMaxHeight;
        this.mScaleType = mScaleType;
        this.successListener = listener;
    }

    public ImageRequest(
            String url,
            Response.SuccessListener<Bitmap> listener,
            int mMaxWidth,
            int mMaxHeight,
            Bitmap.Config mDecodeConfig,
            Response.ErrorListner errorListner
    ) {
        this(
                url,
                listener,
                mMaxWidth,
                mMaxHeight,
                ImageView.ScaleType.CENTER_INSIDE,
                mDecodeConfig,
                errorListner);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Response cachedResponse = Cache.getInstance().getLruCache().get(this.getKey());
        if (cachedResponse != null && cachedResponse.getData() != null) {
            successListener.onResponse(((Bitmap) (cachedResponse).getData()));
            this.cancel(true);
            this.finish();
        }
    }

    @Override
    protected void onPostExecute(Response<Bitmap> response) {
        super.onPostExecute(response);
        // remove the request from requestQueue
        this.finish();
        if (isShouldCache()) {
            Cache.getInstance().getLruCache().put(this.getKey(), response);
        }
        if (response.getData() != null) {
            successListener.onResponse(response.getData());
        } else if (response.getError() != null)
            errorListner.onErrorResponse(response.getError());

    }


    private static int getResizedDimension(
            int maxPrimary,
            int maxSecondary,
            int actualPrimary,
            int actualSecondary,
            ImageView.ScaleType scaleType) {

        // If no dominant value at all, just return the actual.
        if ((maxPrimary == 0) && (maxSecondary == 0)) {
            return actualPrimary;
        }

        // If ScaleType.FIT_XY fill the whole rectangle, ignore ratio.
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            if (maxPrimary == 0) {
                return actualPrimary;
            }
            return maxPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;

        // If ScaleType.CENTER_CROP fill the whole rectangle, preserve aspect ratio.
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            if ((resized * ratio) < maxSecondary) {
                resized = (int) (maxSecondary / ratio);
            }
            return resized;
        }

        if ((resized * ratio) > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }

        return (int) n;
    }


    @Override
    public Response<Bitmap> parseData(byte[] data) {

        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap = null;
        if (mMaxWidth == 0 && mMaxHeight == 0) {
            decodeOptions.inPreferredConfig = mDecodeConfig;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        } else {
            // If we have to resize this image, first get the natural bounds.
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;

            // Then compute the dimensions we would ideally like to decode to.
            int desiredWidth =
                    getResizedDimension(
                            mMaxWidth, mMaxHeight, actualWidth, actualHeight, mScaleType);
            int desiredHeight =
                    getResizedDimension(
                            mMaxHeight, mMaxWidth, actualHeight, actualWidth, mScaleType);

            // Decode to the nearest power of two scaling factor.
            decodeOptions.inJustDecodeBounds = false;
            decodeOptions.inSampleSize =
                    findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);

            // If necessary, scale down to the maximal acceptable size.
            if (tempBitmap != null
                    && (tempBitmap.getWidth() > desiredWidth
                    || tempBitmap.getHeight() > desiredHeight)) {
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            } else {
                bitmap = tempBitmap;
            }
        }

        if (bitmap == null)
            return new Response<>(new ParseResponseException("Error Parsing Data"));
        else
            return new Response<>(bitmap);
    }
}
