package cn.imageviewer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.R;
import cn.imageviewer.helper.ImageLoader;
import cn.imageviewer.helper.OnImageLongClick;
import cn.imageviewer.helper.OnImageSingleClick;
import cn.imageviewer.helper.OnLoadListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by cloudist on 2017/3/21.
 */

public class ViewpagerAdapter extends PagerAdapter {

    private List<String> paths = new ArrayList<>();
    private SparseArray<View> views;

    private Context mContext;
    private ImageLoader imageLoader;
    private OnImageSingleClick onImageSingleClick;
    private OnImageLongClick onImageLongClick;

    public ViewpagerAdapter(Context context, ImageLoader imageLoader, List<String> paths) {
        this.mContext = context;
        this.imageLoader = imageLoader;
        this.paths = paths;

        views = new SparseArray<>(paths.size());
    }

    /**
     * @return 返回页面的个数
     */
    @Override
    public int getCount() {
        return paths.size();
    }

    /**
     * 判断对象是否生成界面
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化position位置的界面
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //new ImageView并设置全屏和图片资源
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_switch_photo, container, false);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.dialog_image);

        views.put(position, view);
        imageLoader.showImage(position, paths.get(position), photoView, onLoadListener);

        if (onImageSingleClick != null) {
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onImageSingleClick.onImageSingleClick(position, paths.get(position), photoView);
                }
            });
        }

        if (onImageLongClick != null) {
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onImageLongClick.onImageLongClick(position, paths.get(position), photoView);
                }
            });
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    OnLoadListener onLoadListener = new OnLoadListener() {
        @Override
        public void onStart(int position) {
            View view = views.get(position);
            RelativeLayout progressBarLayout = (RelativeLayout) view.findViewById(R.id.progressBar_layout);
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(int position) {
            View view = views.get(position);
            RelativeLayout progressBarLayout = (RelativeLayout) view.findViewById(R.id.progressBar_layout);
            progressBarLayout.setVisibility(View.GONE);
        }

        @Override
        public void onSuccess(int position) {
            View view = views.get(position);
            RelativeLayout progressBarLayout = (RelativeLayout) view.findViewById(R.id.progressBar_layout);
            progressBarLayout.setVisibility(View.GONE);
        }
    };

    public void setOnImageSingleClick(OnImageSingleClick onImageSingleClick) {
        this.onImageSingleClick = onImageSingleClick;
    }

    public void setOnImageLongClick(OnImageLongClick onImageLongClick) {
        this.onImageLongClick = onImageLongClick;
    }
}
