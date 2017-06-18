package cn.imageviewer.helper;

import android.view.View;

/**
 * Created by cloudist on 2017/6/18.
 */

public interface ImageClickHelper {

    boolean onLongClick(View view, int position);

    void onSingleClick(View view, int position);
}
