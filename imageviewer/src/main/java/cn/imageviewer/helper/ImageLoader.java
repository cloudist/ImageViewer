package cn.imageviewer.helper;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by cloudist on 2017/6/21.
 */

public interface ImageLoader {
    void showImage(int position, String path, PhotoView photoView, OnLoadListener onLoadListener);
}
