package com.brioal.picpickertest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.brioal.picpickertest.R;
import com.brioal.picpickertest.activity.ImageDetailActivity;
import com.brioal.picpickertest.base.ImageItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by brioal on 16-3-12.
 */
public class SelectImageAdapter extends BasicAdpter {
    public  List<ImageItem> selectList;


    public SelectImageAdapter(Context context) {
        super(context);

    }


    @Override
    public SelectImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_select, parent, false);
        return new SelectImageViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ImageItem imageItem = list.get(position);
        ((SelectImageViewHolder) holder).mImage.setImageURI(Uri.parse(imageItem.getUri()));
        ((SelectImageViewHolder) holder).mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageItem.setChecked(true);
                if (selectList.contains(imageItem)) {
                    if (imageItem.isChecked() == false) {
                        selectList.remove(imageItem);
                    }
                } else if (imageItem.isChecked()) {
                    selectList.add(imageItem);
                }
            }
        });

        ((SelectImageViewHolder) holder).mCheck.setChecked(imageItem.isChecked());
        ((SelectImageViewHolder) holder).mImage.setLayoutParams(linearParmas);
        ((SelectImageViewHolder) holder).mImage.setImageURI(Uri.parse(imageItem.getUri()));
        ((SelectImageViewHolder) holder).mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("uri", imageItem.getUri());
                context.startActivity(intent);
            }
        });

    }


    class SelectImageViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mImage;
        private CheckBox mCheck;
        private View itemView;

        public SelectImageViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.item_select_image);
            mCheck = (CheckBox) itemView.findViewById(R.id.item_select_check);

        }
    }
}
