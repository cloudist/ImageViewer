package cn.imageviewer.helper;

import cn.imageviewer.photoviewer.PhotoView;

/**
 * Created by cloudist on 2017/6/18.
 */

public interface ImageLoadHelper<T> {
    String tramsformPaths(T t);

    void showImage(int position, String path, PhotoView photoView);
}
