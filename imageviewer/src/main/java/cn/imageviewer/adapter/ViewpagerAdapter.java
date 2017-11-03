package cn.imageviewer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.R;
import cn.imageviewer.helper.ImageLoader;
import cn.imageviewer.helper.OnImageLongClickListener;
import cn.imageviewer.helper.OnImageSingleClickListener;
import cn.imageviewer.view.ImageViewer;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by cloudist on 2017/6/28.
 */

public class ViewpagerAdapter extends PagerAdapter {

    protected List<Object> paths = new ArrayList<>();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.switch_photoview, container, false);

        container.addView(view);
        views.put(position, view);
        imageLoader.setView(view);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.dialog_image);

        if (onImageSingleClickListener != null) {
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onImageSingleClickListener.onImageSingleClick(position, paths.get(position), photoView, imageViewer);
                }
            });
        }

        if (onImageLongClickListener != null) {
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onImageLongClickListener.onImageLongClick(position, paths.get(position), photoView, imageViewer);
                }
            });
        }

        imageLoader.showImage(position, paths.get(position), photoView);

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

        for (int i = 0; i < views.size(); i++) {
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

    public void setPaths(List<Object> paths) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        this.paths = paths;
        views = new SparseArray<>(paths.size());
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    private void recycleImage(View view) {
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.dialog_image);
        imageLoader.recycleImage(photoView);
    }

}
