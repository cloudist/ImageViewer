package cn.imageviewer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.adapter.ViewpagerAdapter;
import cn.imageviewer.R;
import cn.imageviewer.helper.ImageClickHelper;
import cn.imageviewer.helper.ImageLoadHelper;

/**
 * Created by cloudist on 2017/5/31.
 */

public class ImageViewer extends DialogFragment {

    FixedViewPager viewpager;
    ViewpagerAdapter adapter;

    int index = 0;
    List<String> paths = new ArrayList<>();
    ImageLoadHelper imageLoadHelper;
    ImageClickHelper imageClickHelper;

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
        View rootView = inflater.inflate(R.layout.dialog_switch_image, container);
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
        super.onResume();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewpagerAdapter(getActivity(), imageLoadHelper, paths);
        adapter.setClickListener(imageClickHelper);
        viewPager.setOffscreenPageLimit(paths.size());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
    }

    public <T> ImageViewer setPaths(List<T> objects, ImageLoadHelper imageLoadHelper) {
        this.imageLoadHelper = imageLoadHelper;
        if (objects != null) {
            for (T t : objects) {
                paths.add(imageLoadHelper.tramsformPaths(t));
            }
        }
        return ImageViewer.this;
    }

    public ImageViewer setIndex(int index) {
        this.index = index;
        return ImageViewer.this;
    }

    public ImageViewer setClickHelper(ImageClickHelper imageClickHelper) {
        this.imageClickHelper = imageClickHelper;
        return ImageViewer.this;
    }
}
