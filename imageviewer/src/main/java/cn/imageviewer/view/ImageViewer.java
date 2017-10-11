package cn.imageviewer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.R;
import cn.imageviewer.adapter.ViewpagerAdapter;
import cn.imageviewer.dragable.SwipeDismissTouchListener;
import cn.imageviewer.dragable.SwipeableFrameLayout;
import cn.imageviewer.helper.ImageLoader;
import cn.imageviewer.helper.OnDismissCallback;
import cn.imageviewer.tranformer.CubeOutTransformer;
import cn.imageviewer.tranformer.DefaultTransformer;
import cn.imageviewer.tranformer.DepthPageTransformer;
import cn.imageviewer.tranformer.ZoomOutTranformer;

/**
 * Created by cloudist on 2017/5/31.
 */

public class ImageViewer extends DialogFragment {

    public static final int TYPE_DEFAULT_TRANSFORMER = 1011;
    public static final int TYPE_CUBEOUT_TRANSFORMER = 1012;
    public static final int TYPE_DEPTHPAGE_TRANSFORMER = 1013;
    public static final int TYPE_ZOOMOUT_TRANSFORMER = 1014;

    Window window;

    SwipeableFrameLayout layout;
    FixedViewPager viewpager;

    int index = 0;
    int transformerType = TYPE_DEFAULT_TRANSFORMER;
    List<String> paths = new ArrayList<>();
    ImageLoader imageLoader;
    ViewpagerAdapter adapter;
    OnDismissCallback onDismissCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeFixDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置入出场动画
        View rootView = inflater.inflate(R.layout.fixed_viewpager, container);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewpager = (FixedViewPager) view.findViewById(R.id.viewpager);
        layout = (SwipeableFrameLayout) view.findViewById(R.id.layout);

        layout.setSwipeDismissTouchListener(new SwipeDismissTouchListener(new SwipeDismissTouchListener.DismissCallbacks() {

            @Override
            public void onDismiss(View view) {
                dismiss();
            }

            @Override
            public void onSwiping(float degree) {
                WindowManager.LayoutParams windowParams = window.getAttributes();
                windowParams.dimAmount = 0.8f * degree;
                window.setAttributes(windowParams);
            }
        }));

        setupViewPager(viewpager);
    }

    public void onResume() {
        window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.image_viewer_transparent);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.8f;
        window.setAttributes(windowParams);
        super.onResume();
        //如果在onViewCreated 设置会出现设置无效的状况
        viewpager.setCurrentItem(index);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (onDismissCallback != null) {
            onDismissCallback.onDismiss(viewpager.getCurrentItem());
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter.setImageLoader(imageLoader);
        adapter.setPaths(paths);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.imgeviewer_margin));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        switch (transformerType) {
            case TYPE_CUBEOUT_TRANSFORMER:
                viewPager.setPageTransformer(true, new CubeOutTransformer());
                break;
            case TYPE_DEFAULT_TRANSFORMER:
                viewPager.setPageTransformer(true, new DefaultTransformer());
                break;
            case TYPE_DEPTHPAGE_TRANSFORMER:
                viewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case TYPE_ZOOMOUT_TRANSFORMER:
                viewPager.setPageTransformer(true, new ZoomOutTranformer());
                break;
        }
    }

    public static class Builder {
        int index = 0;
        int transformerType = TYPE_DEFAULT_TRANSFORMER;
        List<String> paths = new ArrayList<>();
        ImageLoader imageLoader;
        OnDismissCallback onDismissCallback;
        ViewpagerAdapter adapter;

        public Builder(ImageLoader imageLoader, ViewpagerAdapter adapter) {
            this.adapter = adapter;
            this.imageLoader = imageLoader;
        }

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder setTransformerType(int transformerType) {
            this.transformerType = transformerType;
            return this;
        }

        public Builder setPaths(List<String> paths) {
            this.paths = paths;
            return this;
        }

        public Builder setOnDismissCallback(OnDismissCallback onDismissCallback) {
            this.onDismissCallback = onDismissCallback;
            return this;
        }

        public ImageViewer build() {
            ImageViewer imageViewer = new ImageViewer();
            imageViewer.adapter = adapter;
            imageViewer.imageLoader = imageLoader;
            imageViewer.index = index;
            imageViewer.transformerType = transformerType;
            imageViewer.paths = paths;
            imageViewer.onDismissCallback = onDismissCallback;
            return imageViewer;
        }
    }
}
