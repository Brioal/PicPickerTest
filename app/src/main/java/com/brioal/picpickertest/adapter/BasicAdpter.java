package com.brioal.picpickertest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.brioal.picpickertest.base.ImageItem;
import com.brioal.picpickertest.util.ScreenUtil;

import java.util.List;

/**
 * Created by brioal on 16-3-12.
 */
public abstract   class BasicAdpter extends RecyclerView.Adapter{
    protected List<ImageItem> list ; // 总体的数据
    protected Context context;
    protected RelativeLayout.LayoutParams linearParmas ;
    protected RelativeLayout.LayoutParams fraeParmas ;
    protected int imagewidth ;

    public BasicAdpter(Context context) {
        this.context = context;
        imagewidth = ScreenUtil.getScreenWidh() / 2;
        linearParmas = new RelativeLayout.LayoutParams(imagewidth, imagewidth);
        fraeParmas = new RelativeLayout.LayoutParams(imagewidth/2, imagewidth/2);

    }

    public List<ImageItem> getList() {
        return list;
    }

    public void setList(List<ImageItem> list) {
        this.list = list;

    }

    public void updateUi() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
