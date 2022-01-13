package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import lib.kalu.tv.channel.R;

@SuppressLint("AppCompatCustomView")
class ChannelTextViewMarquee extends TextView {

    /**
     * 默认滚动时间
     */
    private static final int ROLLING_INTERVAL_DEFAULT = 10000;
    /**
     * 第一次滚动默认延迟
     */
    private static final int FIRST_SCROLL_DELAY_DEFAULT = 1000;
    /**
     * 滚动模式-一直滚动
     */
    public static final int SCROLL_FOREVER = 100;
    /**
     * 滚动模式-只滚动一次
     */
    public static final int SCROLL_ONCE = 101;

    /**
     * 滚动器
     */
    private Scroller mScroller;
    /**
     * 滚动一次的时间
     */
    private int mRollingInterval;
    /**
     * 滚动的初始 X 位置
     */
    private int mXPaused = 0;
    /**
     * 是否暂停
     */
    private boolean mPaused = true;
    /**
     * 是否第一次
     */
    private boolean mFirst = true;
    /**
     * 滚动模式
     */
    private int mScrollMode;
    /**
     * 初次滚动时间间隔
     */
    private int mFirstScrollDelay;

    public ChannelTextViewMarquee(Context context) {
        super(context);
        init(context, null);
    }

    public ChannelTextViewMarquee(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChannelTextViewMarquee(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChannelTextViewMarquee(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != View.VISIBLE) {
            stopScroll();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
    }

    private void init(Context context, AttributeSet attrs) {
        setSingleLine();
        setEllipsize(null);

        TypedArray attributes = null;
        try {
            attributes = context.obtainStyledAttributes(attrs, R.styleable.ChannelTextViewMarquee);
            mRollingInterval = attributes.getInt(R.styleable.ChannelTextViewMarquee_ctvm_scroll_interval, ROLLING_INTERVAL_DEFAULT);
            mScrollMode = attributes.getInt(R.styleable.ChannelTextViewMarquee_ctvm_scroll_mode, SCROLL_FOREVER);
            mFirstScrollDelay = attributes.getInt(R.styleable.ChannelTextViewMarquee_ctvm_scroll_first_delay, FIRST_SCROLL_DELAY_DEFAULT);
        } catch (Exception e) {
        }
        if (null != attributes) {
            attributes.recycle();
        }
    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        mXPaused = 0;
        mPaused = true;
        mFirst = true;
        resumeScroll();
    }

    /**
     * 继续滚动
     */
    public void resumeScroll() {
        if (!mPaused)
            return;
        // 设置水平滚动
        setHorizontallyScrolling(true);

        // 使用 LinearInterpolator 进行滚动
        if (mScroller == null) {
            mScroller = new Scroller(this.getContext(), new LinearInterpolator());
            setScroller(mScroller);
        }
        int scrollingLen = calculateScrollingLen();
        final int distance = scrollingLen - mXPaused;
        final int duration = (Double.valueOf(mRollingInterval * distance * 1.00000
                / scrollingLen)).intValue();
        if (mFirst) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScroller.startScroll(mXPaused, 0, distance, 0, duration);
                    invalidate();
                    mPaused = false;
                }
            }, mFirstScrollDelay);
        } else {
            mScroller.startScroll(mXPaused, 0, distance, 0, duration);
            invalidate();
            mPaused = false;
        }
    }

    /**
     * 暂停滚动
     */
    public void pauseScroll() {
        if (null == mScroller)
            return;

        if (mPaused)
            return;

        mPaused = true;

        mXPaused = mScroller.getCurrX();

        mScroller.abortAnimation();
    }

    /**
     * 停止滚动，并回到初始位置
     */
    public void stopScroll() {
        if (null == mScroller) {
            return;
        }
        mPaused = true;
        mScroller.startScroll(0, 0, 0, 0, 0);
    }

    /**
     * 计算滚动的距离
     *
     * @return 滚动的距离
     */
    private int calculateScrollingLen() {
        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = getText().toString();
        int start;
        try {
            start = (int) getTag(R.id.module_channel_item_marquee_start);
        } catch (Exception e) {
            start = 0;
        }
        tp.getTextBounds(strTxt, start, strTxt.length(), rect);
        return rect.width();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == mScroller) return;
        if (mScroller.isFinished() && (!mPaused)) {
            if (mScrollMode == SCROLL_ONCE) {
                stopScroll();
                return;
            }
            mPaused = true;
            mXPaused = -1 * getWidth();
            mFirst = false;
            this.resumeScroll();
        }
    }

    /**
     * 获取滚动一次的时间
     */
    public int getRndDuration() {
        return mRollingInterval;
    }

    /**
     * 设置滚动一次的时间
     */
    public void setRndDuration(int duration) {
        this.mRollingInterval = duration;
    }

    /**
     * 设置滚动模式
     */
    public void setScrollMode(int mode) {
        this.mScrollMode = mode;
    }

    /**
     * 获取滚动模式
     */
    public int getScrollMode() {
        return this.mScrollMode;
    }

    /**
     * 设置第一次滚动延迟
     */
    public void setScrollFirstDelay(int delay) {
        this.mFirstScrollDelay = delay;
    }

    /**
     * 获取第一次滚动延迟
     */
    public int getScrollFirstDelay() {
        return mFirstScrollDelay;
    }

    public boolean isPaused() {
        return mPaused;
    }
}
