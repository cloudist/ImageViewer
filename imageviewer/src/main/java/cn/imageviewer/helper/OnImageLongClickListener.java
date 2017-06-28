package cn.imageviewer.helper;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by cloudist on 2017/6/19.
 */

public interface OnImageLongClickListener {
    boolean onImageLongClick(int position, String path, PhotoView photoView);
}
