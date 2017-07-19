package cn.imageviewer.tranformer;

import android.view.View;

/**
 * Created by cloudist on 2017/7/19.
 */

public class DefaultTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
