package com.raafat.raafatalhmidimindvalley.views.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.raafat.raafatalhmidimindvalley.R;
import com.raafat.raafatalhmidimindvalley.databinding.ActivityMainBinding;
import com.raafat.raafatalhmidimindvalley.models.Photo;
import com.raafat.raafatalhmidimindvalley.viewModels.MainActivityViewModel;
import com.raafat.raafatalhmidimindvalley.views.adapters.PhotosAdapter;
import com.raafat.raafatalhmidimindvalley.views.adapters.PhotosGridAdapter;
import com.raafat.raafatalhmidimindvalley.views.callbacks.OnImageClickListner;

public class MainActivity extends AppCompatActivity implements OnImageClickListner {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private PhotosAdapter photosAdapter;
    private PhotosGridAdapter photosGridAdapter;
    private boolean isGrid = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        photosAdapter = new PhotosAdapter(this);
        photosGridAdapter = new PhotosGridAdapter(this);
        setSupportActionBar(binding.toolbar);

        binding.dataRV.setItemAnimator(new DefaultItemAnimator());
        binding.dataRV.setHasFixedSize(true);

        if (!isGrid) {
            binding.setAdapter(photosAdapter);
            binding.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        } else {
            binding.setAdapter(photosGridAdapter);
            binding.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        }

        viewModel = new ViewModelProvider.NewInstanceFactory().create(MainActivityViewModel.class);
        observeViewModel(viewModel);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());

    }

    private void observeViewModel(MainActivityViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getData().observe(this, apiResponse -> {
            if (apiResponse == null)
                return;
            if (apiResponse.getError() == null) {
                binding.setIsLoading(false);
                photosAdapter.setData(apiResponse.getPhotos());
                photosGridAdapter.setData(apiResponse.getPhotos());
                photosGridAdapter.notifyDataSetChanged();
                photosAdapter.notifyDataSetChanged();

                binding.executePendingBindings();
            } else {
                Throwable e = apiResponse.getError();
                Toast.makeText(MainActivity.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_view_type) {
            isGrid = !isGrid;
            if (!isGrid)
                item.setIcon(R.drawable.ic_menu);
            else
                item.setIcon(R.drawable.one_column);
            changeLayoutType();
        }
        return true;
    }

    private void changeLayoutType() {
        if (!isGrid) {
            binding.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.setAdapter(photosAdapter);
        } else {
            binding.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
            binding.setAdapter(photosGridAdapter);
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onClick(View view, Photo photo) {

        Intent toPreviewActivity = new Intent(this, PreviewActivity.class);
        toPreviewActivity.putExtra(PreviewActivity.PHOTO_ID_KEY, photo.getId());
        toPreviewActivity.putExtra(PreviewActivity.PHOTO_URL_KEY, photo.getUrls().getRegular());

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, view.getTransitionName());
        startActivity(toPreviewActivity, options.toBundle());


    }
}
