package cn.imageviewer.helper;

import android.widget.ImageView;

/**
 * Created by cloudist on 2017/6/18.
 */

public interface ImageLoadHelper {
    <T> String tramsformPaths(T t);

    void showImage(int position, String path, ImageView imageView);
}
