package com.raafat.raafatalhmidimindvalley.views.adapters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.raafat.filedownloader.core.ImageRequest;
import com.raafat.filedownloader.core.RequestQueue;
import com.raafat.filedownloader.core.Response;
import com.raafat.raafatalhmidimindvalley.R;


/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @6:10 PM.
 */

public class CustomBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView,RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);

    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        view.setImageResource(R.mipmap.ic_launcher);
        RequestQueue.getInstance().add(new ImageRequest(
                imageUrl,
                view::setImageBitmap, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                error -> view.post(() -> view.setImageResource(R.drawable.image_not_found))
        ));

    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(SubsamplingScaleImageView view, String imageUrl) {

        RequestQueue.getInstance().add(new ImageRequest(
                imageUrl,
                response -> view.setImage(ImageSource.bitmap(response)), 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                error -> view.post(() -> view.setImage(ImageSource.resource(R.drawable.image_not_found)))
        ));



    }
}