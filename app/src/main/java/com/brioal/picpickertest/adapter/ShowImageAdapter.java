package com.brioal.picpickertest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.brioal.picpickertest.R;
import com.brioal.picpickertest.activity.ImageDetailActivity;
import com.brioal.picpickertest.base.ImageItem;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by brioal on 16-3-12.
 */
public class ShowImageAdapter extends BasicAdpter {


    private static final String TAG = "ShowImage";

    public ShowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.item_show, parent, false);
        return new ShowViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ImageItem imageItem = list.get(position);
        ((ShowViewHolder) holder).mImage.setLayoutParams(fraeParmas);
        Log.i(TAG, "onBindViewHolder: "+imageItem.getId());
        ((ShowViewHolder) holder).mImage.setImageURI(Uri.parse(imageItem.getUri()));
        ((ShowViewHolder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                updateUi();
            }
        });
        ((ShowViewHolder) holder).mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra("uri", imageItem.getUri());
                context.startActivity(intent);
            }
        });

    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView mImage ;
        private ImageButton btn_delete;
        private View itemView ;
        public ShowViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mImage = (SimpleDraweeView) itemView.findViewById(R.id.item_show_image);
            btn_delete = (ImageButton) itemView.findViewById(R.id.item_show_btn);
        }
    }

}
