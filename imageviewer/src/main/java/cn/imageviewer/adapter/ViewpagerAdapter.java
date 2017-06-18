package cn.imageviewer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.R;
import cn.imageviewer.helper.ImageLoadHelper;
import cn.imageviewer.photoviewer.PhotoView;

/**
 * Created by cloudist on 2017/3/21.
 */

public class ViewpagerAdapter extends PagerAdapter {

    private SparseArray<Bitmap> drawableArray;

    private List<String> paths = new ArrayList<>();

    private Context mContext;
    private ImageLoadHelper imageHelper;

    public ViewpagerAdapter(Context context, ImageLoadHelper imageHelper, List<String> paths) {
        this.mContext = context;
        this.imageHelper = imageHelper;
        this.paths = paths;
        drawableArray = new SparseArray<>(paths.size());
    }

    /**
     * @return 返回页面的个数
     */
    @Override
    public int getCount() {
        return paths.size();
    }

    /**
     * 判断对象是否生成界面
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化position位置的界面
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //new ImageView并设置全屏和图片资源
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_switch_photo, container, false);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.dialog_image);

        imageHelper.showImage(position, paths.get(position), photoView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
