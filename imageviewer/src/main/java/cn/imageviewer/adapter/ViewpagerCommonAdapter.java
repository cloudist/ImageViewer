package cn.imageviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.imageviewer.R;
import cn.imageviewer.helper.OnLoadListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by cloudist on 2017/3/21.
 */

public class ViewpagerCommonAdapter extends ViewpagerAdapter {

    public ViewpagerCommonAdapter(Context context) {
        super(context);
    }

    @Override
    protected View initView(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.switch_photoview, container, false);

        //可以根据自己的需求重写onLoadListener 并在此处设置
        imageLoader.setOnLoadListener(onLoadListener);

        return view;
    }

    @Override
    protected void loadImage(final int position, String path, View view) {
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.dialog_image);

        if (onImageSingleClickListener != null) {
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onImageSingleClickListener.onImageSingleClick(position, paths.get(position), photoView);
                }
            });
        }

        if (onImageLongClickListener != null) {
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onImageLongClickListener.onImageLongClick(position, paths.get(position), photoView);
                }
            });
        }

        imageLoader.showImage(position, path, photoView);
    }

    private void showProgressBar(int position, int visible) {
        View view = views.get(position);
        RelativeLayout progressBarLayout = (RelativeLayout) view.findViewById(R.id.progressBar_layout);
        progressBarLayout.setVisibility(visible);
    }

    OnLoadListener onLoadListener = new OnLoadListener() {
        @Override
        public void onStart(int position) {
            showProgressBar(position, View.VISIBLE);
        }

        @Override
        public void onError(int position) {
            showProgressBar(position, View.GONE);
        }

        @Override
        public void onSuccess(int position) {
            showProgressBar(position, View.GONE);
        }
    };

}
