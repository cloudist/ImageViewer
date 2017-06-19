package cn.imageviewer.helper;

import cn.imageviewer.photoviewer.PhotoView;

/**
 * Created by cloudist on 2017/6/19.
 */

public interface OnImageLongClick {
    boolean onImageLongClick(int position, String path, PhotoView photoView);
}
