package com.brioal.picpickertest.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.brioal.picpickertest.R;
import com.brioal.picpickertest.adapter.SelectImageAdapter;
import com.brioal.picpickertest.base.ImageItem;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

/**
 * Created by brioal on 16-3-12.
 */
public class SelectPicActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_select_image)
    RecyclerView recyclerView;
    private List<ImageItem> allImageList;
    private List<ImageItem> selectedList ;
    public static int REQUEST_EXTRAL_PEMESSION = 0;
    final String selection = "((" + MIME_TYPE + "=?)or(" + MIME_TYPE + "=?))"; // 选择的类型 MediaStore内的参数
    final String[] selectionArgs = new String[]{"image/jpeg", "image/png"}; //选择的格式
    private SelectImageAdapter selectImageAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                selectImageAdapter.updateUi();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actvity_pic);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    public void initData() {
        allImageList = new ArrayList<>();
        selectedList = (List<ImageItem>) getIntent().getSerializableExtra("data");
        if (ContextCompat.checkSelfPermission(SelectPicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(SelectPicActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTRAL_PEMESSION);
        } else {
            getData();
        }
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Cursor cursor = getContentResolver().query(EXTERNAL_CONTENT_URI, null,//MediaStore内的参数
                        selection, selectionArgs, null);
                while (cursor != null && cursor.moveToNext()) {
                    String path = cursor.getString(
                            cursor.getColumnIndex(DATA)); // 获取路径
                    ImageItem item = new ImageItem().setUri(
                            Uri.fromFile(new File(path)).toString()).setId(allImageList.size()); //设置id，设置uri
                    allImageList.add(item);//添加到当前的列表项
                }
                handler.sendEmptyMessage(0x123);
                selectImageAdapter.selectList = selectedList;
                for (ImageItem imageBean : selectImageAdapter.selectList) {  //初始化，将传入的所以已经选中的item设置为选中
                    imageBean.setChecked(true);
                    allImageList.set(imageBean.getId(), imageBean);
                    handler.sendEmptyMessage(0x123);
                }

            }
        }).start();
    }

    public void initView() {
        setSupportActionBar(toolbar);
        //设置返回键并且添加监听
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置Adapter
        selectImageAdapter = new SelectImageAdapter(this);
        selectImageAdapter.setList(allImageList);
        //设置layoutManager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(selectImageAdapter);
        getSupportActionBar().setTitle("图片选择");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTRAL_PEMESSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                getData();
            } else {
                // Permission Denied
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pic, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onDone(MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra("data", (Serializable) selectImageAdapter.selectList);
        setResult(0, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data", (Serializable) selectImageAdapter.selectList);
        setResult(0, intent);
        super.onBackPressed();
    }
}
