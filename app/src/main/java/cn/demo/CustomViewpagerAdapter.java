package cn.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import cn.imageviewer.adapter.ViewpagerAdapter;
import cn.imageviewer.view.ImageViewer;

/**
 * Created by cloudist on 2017/3/21.
 */

public class CustomViewpagerAdapter extends ViewpagerAdapter {

    public CustomViewpagerAdapter(Context context) {
        super(context);
    }

    @Override
    protected View initView(ViewGroup container, int position) {
        return LayoutInflater.from(mContext).inflate(R.layout.demo_photoview, container, false);
    }

    @Override
    protected void loadImage(final int position, Object path, View view, ImageViewer imageViewer) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.image_demo);
        //自定义adapter也可以直接在内部设置点击事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "demoOnPhotoTap" + position, Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "demoOnLongClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        imageLoader.showImage(position, path, imageView);
    }

    @Override
    protected void recycleImage(View view) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.image_demo);
        imageLoader.recycleImage(imageView);
    }

}
