package cn.imageviewer.dragable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by cloudist on 2017/9/21.
 */

public class ElasticDismissListener implements View.OnTouchListener {

    // Cached ViewConfiguration and system-wide constant values
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;

    // Fixed properties
    private View mView;
    private ElasticDismissListener.DismissCallbacks mCallbacks;
    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero
    private int mViewHeight = 1;

    // Transient properties
    private float mDownY;
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;
    private float mTranslationX;
    private float mTranslationY;
    private int mWindowHeight;
    private int mWindowWidth;

    private boolean mTiltEnabled = true;

    public interface DismissCallbacks {

        boolean canSwipe();

        void onDismiss(View view, boolean toTop);

        void onSwiping(float degree);
    }

    public ElasticDismissListener( ElasticDismissListener.DismissCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    public void setView(View mView) {
        this.mView = mView;
        ViewConfiguration vc = ViewConfiguration.get(mView.getContext());

        //getScaledTouchSlop是一个距离，表示滑动的时候，
        // 手的移动要大于这个距离才开始移动控件。如果小于这个距离就不触发移动控件，如viewpager就是用这个距离来判断用户是否翻页
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = mView.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        WindowManager wm = (WindowManager) mView.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        mWindowHeight = size.y;
        mWindowWidth = size.x;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // offset because the view is translated during swipe
        motionEvent.offsetLocation(mTranslationX, mTranslationY);

        if (mViewWidth < 2) {
            mViewWidth = mView.getWidth();
        }

        if (mViewHeight < 2) {
            mViewHeight = mView.getHeight();
        }

        switch (motionEvent.getActionMasked()) {
            // 一次ACTION_DOWN和ACTION_UP消费一次motionEvent
            case MotionEvent.ACTION_DOWN: {
                // 每一次手指按下，记为实例化一个 VelocityTracker
                // VelocityTracker 是一个跟踪触摸事件滑动速度的帮助类，通过addMovement分析MotionEvent在单位时间类发生的位移来计算速度。
                mDownY = motionEvent.getRawY();
                // 这个token并木有用到
                if (mCallbacks.canSwipe()) {
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                return false;
            }

            case MotionEvent.ACTION_UP: {
                if (mVelocityTracker == null) {
                    mView.performClick();
                    break;
                }

                //mDownX为起点，motionEvent.getRawX()为终点 deltaX为以前按压事件移动的距离
                float deltaY = motionEvent.getRawY() - mDownY;
                mVelocityTracker.addMovement(motionEvent);
                //  VelocityTracker先调用computeCurrentVelocity(int)来初始化速率的单位 。
                mVelocityTracker.computeCurrentVelocity(1000);
                //  横向速度
                float velocityX = mVelocityTracker.getXVelocity();
                float velocityY = mVelocityTracker.getYVelocity();
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
                boolean dismiss = false;
                final boolean dismissTop;
                // 当移动距离大于40 并且mSwiping时 进行dimiss操作
                if (Math.abs(deltaY) > 40) {
                    dismiss = true;
                    // 如果deltaX > 0 向右dimiss
                    dismissTop = deltaY > 0;
                } else if (mMinFlingVelocity <= absVelocityY && absVelocityY <= mMaxFlingVelocity
                        && absVelocityY > absVelocityX && mSwiping) {
                    // 当速率方向与移动方向一致时 进行dimiss
                    dismiss = (velocityY < 0) == (deltaY < 0);
                    // 当速率>0时 向右dimiss
                    dismissTop = mVelocityTracker.getYVelocity() > 0;
                } else {
                    dismissTop = false;
                }

                if (dismiss) {
                    // dismiss
                    mView.animate()
                            .translationY(dismissTop ? -mWindowHeight : mWindowHeight)
                            .setDuration(mAnimationTime)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    performDismiss(dismissTop);
                                }
                            });
                    mCallbacks.onSwiping(0);
                    // mSwiping指翻滚中 翻滚状态下如果不满足dimiss的操作 会停止动画 还原
                } else if (mSwiping) {
                    // cancel
                    mView.animate()
                            .translationY(0)
                            .setDuration(mAnimationTime)
                            .setListener(null);
                    mCallbacks.onSwiping(1);
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mTranslationX = 0;
                mTranslationY = 0;
                mDownY = 0;
                mSwiping = false;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                //如果没走ACTION_DOWN 无视他
                if (mVelocityTracker == null) {
                    mView.performClick();
                    break;
                }

                //停止动画 还原
                mView.animate()
                        .translationY(0)
                        .setDuration(mAnimationTime)
                        .setListener(null);
                mCallbacks.onSwiping(1);
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mTranslationY = 0;
                mDownY = 0;
                mSwiping = false;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                //如果没走ACTION_DOWN 无视他
                if (mVelocityTracker == null) {
                    mView.performClick();
                    break;
                }

                mVelocityTracker.addMovement(motionEvent);
                float deltaY = motionEvent.getRawY() - mDownY;
                //如果x轴移动距离大于 离开控件的距离 并且Y轴移动小于x轴二分之一
                if (Math.abs(deltaY) > mSlop) {
                    mSwiping = true;
                    mView.getParent().requestDisallowInterceptTouchEvent(true);

                    // Cancel listview's touch
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (motionEvent.getActionIndex() <<
                                    MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mView.onTouchEvent(cancelEvent);
                    cancelEvent.recycle();
                }
                // 设置拖拽中的动画
                if (mSwiping) {
                    mTranslationY = deltaY;
                    float moveY = Math.min(Math.abs(deltaY), 100) * (deltaY > 0 ? 1 : -1);
                    //设置左右偏移量
                    mView.setTranslationY(moveY / 3);
                    //设置透明度
                    float minAlpha = 1f - 2f * Math.abs(deltaY) / mViewHeight;
                    mCallbacks.onSwiping(Math.max(0f, Math.min(1f, minAlpha)));
                    return true;
                }
                break;
            }
        }
        return false;
    }

    // 执行dimiss操作
    private void performDismiss(final boolean toTop) {
        final ViewGroup.LayoutParams lp = mView.getLayoutParams();
        final int originalHeight = mView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //回调
                mCallbacks.onDismiss(mView, toTop);
                //重置View 如果你在onDismiss中dimiss了dialog是看不到效果的
                mView.setTranslationX(0);
                mView.setTranslationY(0);
                lp.height = originalHeight;
                mView.setLayoutParams(lp);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = (Integer) valueAnimator.getAnimatedValue();
                mView.setLayoutParams(lp);
            }
        });

        animator.start();
    }
}
