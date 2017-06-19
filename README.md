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
    ImageViewer.newInstance()
            .setIndex(0) // Default index
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
                public void showImage(int position, String path, PhotoView photoView) {
                    // Load images
                    Glide.with(OCApplication.getContext())
                            .load(path)
                            .into(photoView);
                }
            })
            .show(getSupportFragmentManager(), "ImageViewer");
```
