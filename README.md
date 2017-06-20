# ImageViewer

## Screenshot

![](gif/display.gif)

## Usage

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.sychaos:ImageViewer:1.0.2'
	}

## Sample Code

```Java
    List<String> paths = new ArrayList();
    final ImageViewer imageViewer = ImageViewer.newInstance();
    imageViewer.setIndex(0)
            .setOnImageSingleClick(new OnImageSingleClick() {
                @Override
                public void onImageSingleClick(int position, String path, PhotoView photoView) {
                    Toast.makeText(MainActivity.this, "onSingleClick" + position, Toast.LENGTH_SHORT).show();
                }
            })
            .setOnImageLongClick(new OnImageLongClick() {
                @Override
                public boolean onImageLongClick(int position, String path, PhotoView photoView) {
                    Toast.makeText(MainActivity.this, "onLongClick" + position, Toast.LENGTH_SHORT).show();
                    return true;
                }
            })
            .setPaths(paths, new ImageLoadHelper<String>() {
                @Override
                public String tramsformPaths(String path) {
                    return path;
                }

                @Override
                public void showImage(final int position, String path, PhotoView photoView) {
                    imageViewer.showProgress(position);
                    Glide.with(OCApplication.getContext())
                            .load(path)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    imageViewer.hideProgress(position);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    imageViewer.hideProgress(position);
                                    return false;
                                }
                            })
                            .into(photoView);
                }
            })
            .show(getSupportFragmentManager(), "ImageViewer");
```
