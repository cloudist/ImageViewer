package cn.imageviewer.helper;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by cloudist on 2017/6/28.
 */

public abstract class ImageLoader {

    public OnLoadListener onLoadListener;
    public View view;

    public abstract void showImage(int position, String path, ImageView imageView);

    public void recycleImage(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        imageView.setImageDrawable(null);
    }

    public OnLoadListener getOnLoadListener() {
        return onLoadListener;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
