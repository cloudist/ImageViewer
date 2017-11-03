package cn.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.adapter.ViewpagerAdapter;
import cn.imageviewer.helper.OnImageLongClickListener;
import cn.imageviewer.helper.OnImageSingleClickListener;
import cn.imageviewer.helper.OnLoadListener;
import cn.imageviewer.helper.ImageLoader;
import cn.imageviewer.view.ImageViewer;
import uk.co.senab.photoview.PhotoView;

public class MainActivity extends AppCompatActivity {

    List<Object> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        paths = new ArrayList<>();
        paths.add("http://i2.sinaimg.cn/travel/2015/0715/U10172P704DT20150715110013.png");
        paths.add("http://img02.tooopen.com/images/20150628/tooopen_sy_132149827682.jpg");
        paths.add("http://pic.qiantucdn.com/58pic/17/99/58/34R58PICpKy_1024.jpg");
        paths.add("http://tupian.enterdesk.com/2013/mxy/12/07/3/4.jpg");
        paths.add("http://pic.58pic.com/58pic/13/40/15/62958PICTq7_1024.jpg");
        paths.add("http://pic.qiantucdn.com/58pic/11/69/82/58PIC2Q58PICsY9.jpg");
        paths.add("http://img3.3lian.com/2013/v11/41/d/81.jpg");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new ImageViewer.Builder(
//                        new ImageLoader() {
//                            @Override
//                            public void showImage(int position, Object path, ImageView imageView) {
//                                Glide.with(OCApplication.getContext())
//                                        .load(path)
//                                        .into(imageView);
//                            }
//                        },
//                        new CustomViewpagerAdapter(MainActivity.this))
//                        .setIndex(0)
//                        .setPaths(paths)
//                        .build()
//                        .show(getSupportFragmentManager(), "ImageViewer");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageViewer.Builder(MainActivity.this,
                        new ImageLoader() {
                            @Override
                            public void showImage(final int position, Object path, ImageView imageView) {
                                Glide.with(OCApplication.getContext())
                                        .load(path.toString())
                                        .into(imageView);
                            }
                        })
                        .setIndex(2)
                        .setPaths(paths)
                        .setTransformerType(ImageViewer.TYPE_CUBEOUT_TRANSFORMER)
                        .setOnImageLongClickListener(new OnImageLongClickListener() {
                            @Override
                            public boolean onImageLongClick(int position, Object path, PhotoView photoView, ImageViewer imageViewer) {
                                imageViewer.dismiss();
                                return false;
                            }
                        })
                        .build()
                        .show(getSupportFragmentManager(), "ImageViewer");
            }
        });
    }
}
