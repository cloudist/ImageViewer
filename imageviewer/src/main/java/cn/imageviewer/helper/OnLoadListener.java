package cn.imageviewer.helper;

/**
 * Created by cloudist on 2017/6/21.
 */

public interface OnLoadListener {

    void onStart(int position);

    void onError(int position);

    void onSuccess(int position);
}
