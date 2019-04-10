package com.raafat.raafatalhmidimindvalley.views.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.raafat.raafatalhmidimindvalley.R;
import com.raafat.raafatalhmidimindvalley.databinding.ItemPinboardLayoutBinding;
import com.raafat.raafatalhmidimindvalley.models.Photo;
import com.raafat.raafatalhmidimindvalley.views.callbacks.OnImageClickListner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @6:05 PM.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ItemViewHolder> {

    private List<Photo> data = new ArrayList<>();
    private OnImageClickListner onImageClickListner;

    public PhotosAdapter(OnImageClickListner onImageClickListner) {
        this.onImageClickListner = onImageClickListner;
    }

    public void setData(final List<Photo> photoList) {
        if (this.data.size() == 0) {
            this.data.addAll(photoList);
            notifyItemRangeInserted(0, photoList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PhotosAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return photoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PhotosAdapter.this.data.get(oldItemPosition).getId().equals(
                            photoList.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Photo user = photoList.get(newItemPosition);
                    Photo old = photoList.get(oldItemPosition);
                    return user.getId().equals(old.getId());
                }
            });
            this.data = photoList;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public PhotosAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPinboardLayoutBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_pinboard_layout, viewGroup, false);
        itemBinding.setOnImageClickListner(onImageClickListner);
        return new ItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.ItemViewHolder viewHolder, int position) {
        viewHolder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemPinboardLayoutBinding itemBinding;

        public ItemViewHolder(ItemPinboardLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Photo photo) {
            itemBinding.setPhoto(photo);
            itemBinding.executePendingBindings();
        }
    }
}
