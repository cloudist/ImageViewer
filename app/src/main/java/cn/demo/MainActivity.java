package cn.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.adapter.ViewpagerCommonAdapter;
import cn.imageviewer.helper.OnImageLongClickListener;
import cn.imageviewer.helper.OnImageSingleClickListener;
import cn.imageviewer.helper.OnLoadListener;
import cn.imageviewer.helper.ImageLoader;
import cn.imageviewer.view.ImageViewer;
import uk.co.senab.photoview.PhotoView;

public class MainActivity extends AppCompatActivity {

    List<String> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        paths = new ArrayList<>();
        paths.add("http://i2.sinaimg.cn/travel/2015/0715/U10172P704DT20150715110013.png");
        paths.add("http://img02.tooopen.com/images/20150628/tooopen_sy_132149827682.jpg");
        paths.add("http://img02.tooopen.com/images/20160330/tooopen_sy_157749743148.jpg");
        paths.add("http://pic.qiantucdn.com/58pic/17/99/58/34R58PICpKy_1024.jpg");
        paths.add("http://tupian.enterdesk.com/2013/mxy/12/07/3/4.jpg");
        paths.add("http://pic.58pic.com/58pic/13/40/15/62958PICTq7_1024.jpg");
        paths.add("http://pic.qiantucdn.com/58pic/11/69/82/58PIC2Q58PICsY9.jpg");
        paths.add("http://img05.tooopen.com/images/20150630/tooopen_sy_132344141259.jpg");
        paths.add("http://img3.3lian.com/2013/v11/41/d/81.jpg");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewer.newInstance()
                        .setIndex(0)
                        .setPaths(paths)
                        .setAdapter(new CustomViewpagerAdapter(MainActivity.this))
                        .setImageLoader(new ImageLoader() {
                            @Override
                            public void showImage(int position, String path, ImageView imageView) {
                                Glide.with(OCApplication.getContext())
                                        .load(path)
                                        .into(imageView);
                            }
                        })
                        .show(getSupportFragmentManager(), "ImageViewer");
            }
        });

        final ViewpagerCommonAdapter viewpagerCommonAdapter = new ViewpagerCommonAdapter(MainActivity.this);

        viewpagerCommonAdapter.setOnImageSingleClickListener(new OnImageSingleClickListener() {
            @Override
            public void onImageSingleClick(int position, String path, PhotoView photoView) {
                Toast.makeText(MainActivity.this, "onImageSingleClick" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewpagerCommonAdapter.setOnImageLongClickListener(new OnImageLongClickListener() {
            @Override
            public boolean onImageLongClick(int position, String path, PhotoView photoView) {
                Toast.makeText(MainActivity.this, "onImageLongClick" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewer.newInstance()
                        .setIndex(0)
                        .setPaths(paths)
                        .setAdapter(viewpagerCommonAdapter)
                        .setImageLoader(new ImageLoader() {
                            @Override
                            public void showImage(final int position, String path, ImageView imageView) {
                                final OnLoadListener loadListener = this.getOnLoadListener();
                                final View view = this.getView();

                                loadListener.onStart(position);
                                Glide.with(OCApplication.getContext())
                                        .load(path)
                                        .listener(new RequestListener<String, GlideDrawable>() {
                                            @Override
                                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                loadListener.onError(position);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                loadListener.onSuccess(position);
                                                return false;
                                            }
                                        })
                                        .into(imageView);
                            }
                        })
                        .show(getSupportFragmentManager(), "ImageViewer");
            }
        });
    }
}
