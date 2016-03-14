package com.brioal.picpickertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.brioal.picpickertest.activity.SelectPicActivity;
import com.brioal.picpickertest.adapter.ShowImageAdapter;
import com.brioal.picpickertest.base.ImageItem;
import com.brioal.picpickertest.util.ScreenUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainInfo";
    @Bind(R.id.main_show_image)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar ;
    @Bind(R.id.main_btn_show_bottomsheet)
    Button btn_start;

    public static int REQUEST_SELECT_PIC = 2 ;
    private List<ImageItem> selectedImageList;
    private ShowImageAdapter adapter ;



    //TODo 默认显示并且不会隐藏的设置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.init(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setView();
    }

    public void initData() {
        selectedImageList = new ArrayList<>();
    }

    public void setView() {
        adapter = new ShowImageAdapter(this);
        adapter.setList(selectedImageList);
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("图片选择器");
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectPicActivity.class);
                intent.putExtra("data", (Serializable) selectedImageList);
                startActivityForResult(intent, REQUEST_SELECT_PIC);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PIC) {
            selectedImageList = (List<ImageItem>) data.getSerializableExtra("data");
            adapter.setList(selectedImageList);

            Log.i(TAG, "onActivityResult: "+selectedImageList.size());
            for (int i = 0; i < selectedImageList.size(); i++) {
                Log.i(TAG, "onActivityResult: "+selectedImageList.get(i).getUri());
            }
            adapter.updateUi();
        }
    }
}
