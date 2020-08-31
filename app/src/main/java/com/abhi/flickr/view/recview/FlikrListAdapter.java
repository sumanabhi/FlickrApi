package com.abhi.flickr.view.recview;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi.flickr.R;
import com.abhi.flickr.repository.model.FlikrModel;
import com.abhi.flickr.repository.model.ImageSize;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhinav.
 */

public class FlikrListAdapter extends RecyclerView.Adapter<FlikrListAdapter.FlikrViewHolder> {

    private final Application application;
    private List<FlikrModel> mItems = new ArrayList<FlikrModel>();
    private static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
    private Drawable drawable;
    private RequestOptions options;

    public FlikrListAdapter(Application application) {
        this.application = application;
        drawable = ContextCompat.getDrawable(application.getApplicationContext(), R.drawable.ic_image_24dp);
        options = new RequestOptions();
        options.centerCrop();
        options.placeholder(drawable);
    }

    @Override
    public void onBindViewHolder(@NonNull FlikrViewHolder holder, int position) {
        final FlikrModel model = mItems.get(position);
        Glide.with(application.getApplicationContext()).load(String.format(IMAGE_URL, model.farm,
                model.server, model.id, model.secret, ImageSize.MEDIUM.getValue())).
                apply(options).into(holder.ivIcon);
    }

    @Override
    @NonNull
    public FlikrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FlikrViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItems(List<FlikrModel> items) {
       // mItems.clear();
        if (items != null) {
            mItems.addAll(items);
        }else{
            mItems.clear();
        }
        notifyDataSetChanged();
    }


    class FlikrViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;

         FlikrViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.image);
        }
    }
}
