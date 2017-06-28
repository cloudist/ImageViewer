package cn.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import cn.imageviewer.adapter.ViewpagerAdapter;

/**
 * Created by cloudist on 2017/3/21.
 */

public class CustomViewpagerAdapter extends ViewpagerAdapter {

    public CustomViewpagerAdapter(Context context) {
        super(context);
    }

    @Override
    protected View initView(ViewGroup container, final int position) {
        //new ImageView并设置全屏和图片资源
        View view = LayoutInflater.from(mContext).inflate(R.layout.demo_photoview, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image_demo);

        //自定义adapter可以在内部直接设置点击事件
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

        imageLoader.setView(view);
        imageLoader.showImage(position, paths.get(position), imageView);
        return view;
    }

}
