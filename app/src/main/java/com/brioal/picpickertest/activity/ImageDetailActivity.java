package com.brioal.picpickertest.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.brioal.picpickertest.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brioal on 16-3-12.
 */
public class ImageDetailActivity extends Activity {

    @Bind(R.id.detail_image)
    SimpleDraweeView detailImage;

    private String uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        uri = getIntent().getStringExtra("uri");
        if (uri != null) {
            detailImage.setImageURI(Uri.parse(uri));
        }

    }
}
