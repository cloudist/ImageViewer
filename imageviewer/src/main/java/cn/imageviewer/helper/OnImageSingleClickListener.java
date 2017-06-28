package cn.imageviewer.helper;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by cloudist on 2017/6/19.
 */

public interface OnImageSingleClickListener {
    void onImageSingleClick(int position, String path, PhotoView photoView);
}
