package cn.imageviewer.dragable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class SwipeableFrameLayout extends FrameLayout {

    private SwipeDismissTouchListener mSwipeDismissTouchListener;
    private ElasticDismissListener mElasticDismissListener;

    public SwipeableFrameLayout(@NonNull Context context) {
        super(context);
    }

    public SwipeableFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeDismissTouchListener(SwipeDismissTouchListener swipeDismissTouchListener) {
        mSwipeDismissTouchListener = swipeDismissTouchListener;
        mSwipeDismissTouchListener.setView(this);
        setOnTouchListener(swipeDismissTouchListener);
        setClickable(true);
    }

    public void setmElasticDismissListener(ElasticDismissListener elasticDismissListener) {
        mElasticDismissListener = elasticDismissListener;
        mElasticDismissListener.setView(this);
        setOnTouchListener(mElasticDismissListener);
        setClickable(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mSwipeDismissTouchListener != null) {
            //当有mTouchListener时 直接拦截onTouch事件
            if (mSwipeDismissTouchListener.onTouch(this, ev)) {
                return true;
            }
        }

        if (mElasticDismissListener != null) {
            //当有mTouchListener时 直接拦截onTouch事件
            if (mElasticDismissListener.onTouch(this, ev)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

}
