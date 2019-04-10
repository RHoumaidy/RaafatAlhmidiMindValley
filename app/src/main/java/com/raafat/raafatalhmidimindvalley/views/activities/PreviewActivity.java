package com.raafat.raafatalhmidimindvalley.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.raafat.raafatalhmidimindvalley.R;
import com.raafat.raafatalhmidimindvalley.databinding.ActivityPhotoPreviewBinding;

/**
 * Created by Raafat Alhoumaidy on 4/11/2019 @3:00 AM.
 */
public class PreviewActivity extends AppCompatActivity {

    public static final String PHOTO_URL_KEY = "PHOTO_URL";
    public static final String PHOTO_ID_KEY = "PHOTO_ID";

    private ActivityPhotoPreviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_preview);

        binding.setPhotoFullUrl(getIntent().getStringExtra(PHOTO_URL_KEY));
        binding.setPhotoId(getIntent().getStringExtra(PHOTO_ID_KEY));
        binding.executePendingBindings();


    }


}
