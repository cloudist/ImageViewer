package cn.imageviewer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.helper.OnImageLongClickListener;
import cn.imageviewer.helper.OnImageSingleClickListener;
import cn.imageviewer.helper.ImageLoader;
import cn.imageviewer.view.ImageViewer;

/**
 * Created by cloudist on 2017/6/28.
 */

public abstract class ViewpagerAdapter extends PagerAdapter {

    protected List<String> paths = new ArrayList<>();
    protected SparseArray<View> views;

    protected Context mContext;
    protected ImageLoader imageLoader;
    protected OnImageSingleClickListener onImageSingleClickListener;
    protected OnImageLongClickListener onImageLongClickListener;

    private ImageViewer imageViewer;

    public ViewpagerAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //new ImageView并设置全屏和图片资源

        View view = initView(container, position);

        container.addView(view);
        views.put(position, view);
        imageLoader.setView(view);

        loadImage(position, paths.get(position), view, imageViewer);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        recycleImage((View) object);
        container.removeView((View) object);
    }

    public void recycleAllImage() {
        if (views == null) {
            return;
        }

        for(int i = 0; i < views.size(); i++) {
            int key = views.keyAt(i);
            recycleImage(views.get(key));
        }
    }

    public void setImageViewer(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void setOnImageSingleClickListener(OnImageSingleClickListener onImageSingleClickListener) {
        this.onImageSingleClickListener = onImageSingleClickListener;
    }

    public void setOnImageLongClickListener(OnImageLongClickListener onImageLongClickListener) {
        this.onImageLongClickListener = onImageLongClickListener;
    }

    public void setPaths(List<String> paths) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        this.paths = paths;
        views = new SparseArray<>(paths.size());
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    protected abstract View initView(ViewGroup container, final int position);

    protected abstract void loadImage(int position, String path, View view, ImageViewer imageViewer);

    protected abstract void recycleImage(View view);

}
