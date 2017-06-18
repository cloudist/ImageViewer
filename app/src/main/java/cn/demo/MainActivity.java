package cn.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.imageviewer.helper.ImageClickHelper;
import cn.imageviewer.helper.ImageLoadHelper;
import cn.imageviewer.view.ImageViewer;

public class MainActivity extends AppCompatActivity {

    List<String> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        paths = new ArrayList<>();
        paths.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2847494274,442712643&fm=26&gp=0.jpg");
        paths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497737727892&di=49a8bb543d3c4c620214b5cc37af2cbb&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F285%2F24%2F586K2UOWHG9D.jpg");
        paths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497737727892&di=7647dd2161a5e3b0bb2146eb45179d0b&imgtype=0&src=http%3A%2F%2Fm2.biz.itc.cn%2Fpic%2Fnew%2Fn%2F85%2F13%2FImg6941385_n.jpg");
        paths.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497737727631&di=eea1e1284255cdccceb09a600368cd26&imgtype=0&src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F512%2F0R112123213%2F120R1123213-10.jpg");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageViewer.newInstance()
                        .setIndex(0)
                        .setPaths(paths, new ImageLoadHelper<String>() {
                            @Override
                            public String tramsformPaths(String path) {
                                return path;
                            }

                            @Override
                            public void showImage(int position, String path, ImageView imageView) {
                                Glide.with(OCApplication.getContext())
                                        .load(path)
                                        .into(imageView);
                            }
                        })
                        .setClickHelper(new ImageClickHelper() {
                            @Override
                            public boolean onLongClick(View view, int position) {
                                Toast.makeText(MainActivity.this, "onLongClick", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public void onSingleClick(View view, int position) {
                                Toast.makeText(MainActivity.this, "onSingleClick", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show(getSupportFragmentManager(), "ImageViewer");
            }
        });
    }
}
