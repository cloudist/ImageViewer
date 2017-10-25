package cn.imageviewer.helper;

import cn.imageviewer.view.ImageViewer;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by cloudist on 2017/6/19.
 */

public interface OnImageSingleClickListener {
    void onImageSingleClick(int position, Object path, PhotoView photoView, ImageViewer imageViewer);
}
