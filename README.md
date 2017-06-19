# ImageViewer
图片浏览器
## 1.代码参考
    //初始化图片集合
    List<String> paths = new ArrayList
    ImageViewer.newInstance()
     //用来设置默认位置
    .setIndex(0)
    //用来设置单按事件
    .setOnImageSingleClick(new OnImageSingleClick() {
        @Override
        public void onImageSingleClick(int position, String path, PhotoView photoView) {
            Toast.makeText(MainActivity.this, "onSingleClick" + position, Toast.LENGTH_SHORT).show();
        }
    })
    //用来设置长按事件
    .setOnImageLongClick(new OnImageLongClick() {
        @Override
        public boolean onImageLongClick(int position, String path, PhotoView photoView) {
            Toast.makeText(MainActivity.this, "onLongClick" + position, Toast.LENGTH_SHORT).show();
            return true;
        }
    })
    //传入所需的pathList 以及加载方式 ImageLoadHelper（必须）
    //paths里的对象可以是任意的，在tramsformPaths完成转换即可
    //showImage方法里可以自定义你想要的加载方式(例子中使用的是Glide)
    .setPaths(paths, new ImageLoadHelper<String>() {

        @Override
        public String tramsformPaths(String path) {
            return path;
        }

        @Override
        public void showImage(int position, String path, PhotoView photoView) {

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(MainActivity.this, "onLongClick" + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    Toast.makeText(MainActivity.this, "onSingleClick" + position, Toast.LENGTH_SHORT).show();
                }
            });

            Glide.with(OCApplication.getContext())
                    .load(path)
                    .into(photoView);
        }

    })
    .show(getSupportFragmentManager(), "ImageViewer");
        }
    });
