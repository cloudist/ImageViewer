package cn.imageviewer.helper;

import cn.imageviewer.photoviewer.PhotoView;

/**
 * Created by cloudist on 2017/6/19.
 */

public interface OnImageSingleClick {
    void onImageSingleClick(int position, String path, PhotoView photoView);
}
