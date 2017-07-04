package cn.imageviewer.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import cn.imageviewer.helper.ImageTramsform;
import cn.imageviewer.helper.ImageLoader;

/**
 * Created by cloudist on 2017/5/31.
 */

public class ImageViewer extends DialogFragment {

    FixedViewPager viewpager;
    ViewpagerAdapter adapter;

    int index = 0;
    List<String> paths = new ArrayList<>();
    ImageLoader imageLoader;

    public static ImageViewer newInstance() {
        Bundle args = new Bundle();
        ImageViewer fragment = new ImageViewer();
        fragment.setArguments(args);
        return fragment;
    }

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

        setupViewPager(viewpager);
    }

    public void onResume() {
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.image_viewer_black_deep);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        hideStatusNavigationBar(window);
        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter.setImageLoader(imageLoader);
        adapter.setPaths(paths);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.imgeviewer_margin));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
    }

    private void hideStatusNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;//hide statusBar
            window.getDecorView().setSystemUiVisibility(uiFlags);

        }
    }

    public ImageViewer setPaths(List<String> paths) {
        this.paths = paths;
        return ImageViewer.this;
    }

    public <T> ImageViewer setPaths(List<T> objects, ImageTramsform<T> imageTramsform) {
        for (T t : objects) {
            paths.add(imageTramsform.tramsformPaths(t));
        }
        return ImageViewer.this;
    }

    public ImageViewer setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return ImageViewer.this;
    }

    public ImageViewer setIndex(int index) {
        this.index = index;
        return ImageViewer.this;
    }

    public ImageViewer setAdapter(ViewpagerAdapter adapter) {
        this.adapter = adapter;
        return ImageViewer.this;
    }

}
