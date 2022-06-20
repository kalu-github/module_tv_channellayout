package lib.kalu.tv.channel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import lib.kalu.tv.channel.blur.BlurView;
import lib.kalu.tv.channel.listener.OnChannelChangeListener;
import lib.kalu.tv.channel.model.ChannelModel;

@Keep
public class ChannelLayout extends LinearLayout implements Handler.Callback {

    private final Handler mHandler = new Handler(this);

    public ChannelLayout(Context context) {
        super(context);
        init(null);
    }

    public ChannelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ChannelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public int getOrientation() {
        return LinearLayout.HORIZONTAL;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            resetTime();
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            resetTime();
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            resetTime();
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            resetTime();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearTime();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {

        int timeout;
        try {
            timeout = (int) getTag(R.id.module_channel_timeout);
        } catch (Exception e) {
            timeout = 10;
        }
        if (timeout < 0) {
            timeout = 10;
        }

        ChannelUtil.logE("handleMessage => what = " + msg.what + ", timeout = " + timeout);

        if (msg.what >= timeout) {
            clearTime();
            setVisibility(View.GONE);
        } else {
            nextTime(msg.what);
        }
        return false;
    }

    @Override
    public void setVisibility(int visibility) {

//        if (getVisibility() == visibility)
//            return;


        ChannelUtil.logE("handleMessage[setVisibility] => visibility = " + visibility);

        // show
        if (visibility == View.VISIBLE) {
            show();
        }
        // gone
        else {
            gone();
        }
    }

    private final void show() {
        autoTime(View.VISIBLE);
//        bringToFront();
        super.setVisibility(View.VISIBLE);
    }

    private final void gone() {
        autoTime(View.GONE);
        super.setVisibility(View.GONE);
    }

    private final void autoTime(int visibility) {
        if (visibility == View.VISIBLE) {
            resetTime();
        } else {
            clearTime();
        }
    }

    private final void resetTime() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (null != mHandler) {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    private final void clearTime() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private final void nextTime(int what) {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(what + 1, 1000);
        }
    }

    private final void init(@Nullable AttributeSet attrs) {
        setClickable(false);
        setLongClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
//        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(LinearLayout.HORIZONTAL);

        int maxIndex = Integer.MIN_VALUE;
        int maxEms = Integer.MIN_VALUE;
        int maxWidth = Integer.MIN_VALUE;

        int timeout = 10;
        int column = 0;
        int itemGravity = 3;
        int itemCount = 10;
        int itemTextSize = 0;
        int itemTextPaddingLeft = 0;
        int itemTextPaddingRight = 0;
        int itemDrawablePadding = 0;
        @LayoutRes
        int footRes = -1;

        TypedArray attributes = null;
        try {
            attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ChannelLayout);
            maxIndex = attributes.getInt(R.styleable.ChannelLayout_cl_column_max_index, Integer.MIN_VALUE);
            maxEms = attributes.getInt(R.styleable.ChannelLayout_cl_column_max_ems, Integer.MIN_VALUE);
            maxWidth = attributes.getDimensionPixelOffset(R.styleable.ChannelLayout_cl_column_max_width, Integer.MIN_VALUE);
            timeout = attributes.getInt(R.styleable.ChannelLayout_cl_timeout, 10);
            column = attributes.getInt(R.styleable.ChannelLayout_cl_column_count, 0);
            itemGravity = attributes.getInt(R.styleable.ChannelLayout_cl_column_gravity, 3);
            itemCount = attributes.getInt(R.styleable.ChannelLayout_cl_column_item_count, 10);
            itemTextSize = attributes.getDimensionPixelOffset(R.styleable.ChannelLayout_cl_column_item_text_size, 0);
            itemTextPaddingLeft = attributes.getDimensionPixelOffset(R.styleable.ChannelLayout_cl_column_item_padding_left, 0);
            itemTextPaddingRight = attributes.getDimensionPixelOffset(R.styleable.ChannelLayout_cl_column_item_padding_right, 0);
            itemDrawablePadding = attributes.getDimensionPixelOffset(R.styleable.ChannelLayout_cl_column_item_drawable_padding, 0);
            footRes = attributes.getResourceId(R.styleable.ChannelLayout_cl_column_foot, -1);
        } catch (Exception e) {
        }
        if (null != attributes) {
            attributes.recycle();
        }

        // timeout
        setTag(R.id.module_channel_timeout, timeout);

        int size = column + 1;
        for (int i = 0; i < size; i++) {
            // image
            if (i + 1 == size) {

                try {
                    LayoutInflater.from(getContext()).inflate(footRes, this);
//                    ChannelImageView child = new ChannelImageView(getContext());
//                    int width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_arrow_width);
//                    child.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT));
//                    child.setFocusable(false);
//                    child.setFocusableInTouchMode(false);
//                    child.setClickable(false);
//                    child.setLongClickable(false);
//                    child.setImageResource(R.drawable.module_channellayout_ic_arrow);
//                    child.setBackgroundColor(Color.parseColor("#000000"));
//                    child.setVisibility(View.GONE);
//                    addView(child);
                } catch (Exception e) {
                }
            }
            //
            else if (maxIndex >= 0 && i == maxIndex) {
                if (maxEms < 0) {
                    maxEms = Integer.MIN_VALUE;
                }
                if (maxWidth < 0) {
                    maxWidth = Integer.MIN_VALUE;
                }
                ChannelScrollView child = new ChannelScrollView(getContext(), maxEms, maxWidth, itemGravity, itemCount, itemTextSize, itemTextPaddingLeft, itemTextPaddingRight, itemDrawablePadding);
                child.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                child.setBackgroundColor(Color.parseColor("#000000"));
                child.setVisibility(View.GONE);
                addView(child);
            }
            // item
            else {
                ChannelScrollView child = new ChannelScrollView(getContext(), Integer.MIN_VALUE, Integer.MIN_VALUE, itemGravity, itemCount, itemTextSize, itemTextPaddingLeft, itemTextPaddingRight, itemDrawablePadding);
                child.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                child.setBackgroundColor(Color.parseColor("#000000"));
                child.setVisibility(View.GONE);
                addView(child);
            }
        }
    }

    @Keep
    public final void setBackgroundResources(@DrawableRes int... resources) {
        setBackgroundResources(-1, false, resources);
    }

    @Keep
    public final void setBackgroundResources(@NonNull int value, @NonNull boolean blurScript, @DrawableRes int... resources) {

        int count = getChildCount();
        int length;
        try {
            length = resources.length;
        } catch (Exception e) {
            length = 0;
        }
        int min = Math.min(count, length);

        for (int i = 0; i < min; i++) {

            View view = getChildAt(i);
            if (null == view)
                continue;

            // ChannelScrollView
            if (view instanceof ChannelScrollView) {
                ChannelScrollView scrollView = (ChannelScrollView) view;
                scrollView.setBackground(value, blurScript, resources[i]);
                ChannelUtil.logE("setBackgroundBlur1[succ] => i = " + i);
            }
            // ChannelImageView
            else if (view instanceof ChannelImageView) {
                ChannelImageView imageView = (ChannelImageView) view;
                imageView.setBackground(value, blurScript, resources[i]);
                ChannelUtil.logE("setBackgroundBlur1[fail] => i = " + i);
            }
        }
    }

    @Keep
    public final void update(@NonNull List<List<ChannelModel>> list) {

        ChannelUtil.logE("update => list = " + list);
        if (null == list || list.size() <= 0)
            return;

        int size = list.size();
        ChannelUtil.logE("update => size = " + size);

        // child
        for (int i = 0; i < size; i++) {
            List<ChannelModel> temp = list.get(i);
            update(size, i, -1, false, temp, false);
        }
    }

    @Keep
    public final void update(@NonNull int count, @NonNull int column, @NonNull List<ChannelModel> list) {
        update(count, column, -1, false, list, false);
    }

    @Keep
    public final void update(@NonNull int count, @NonNull int column, @NonNull int position, @NonNull List<ChannelModel> list) {
        update(count, column, position, false, list, false);
    }

    @Keep
    public final void update(@NonNull int count, @NonNull int column, @NonNull int position, @NonNull boolean requestFocus, @NonNull List<ChannelModel> list) {
        update(count, column, position, requestFocus, list, false);
    }

    @Keep
    public final void update(@NonNull int count, @NonNull int column, @NonNull int position, @NonNull boolean requestFocus, @NonNull List<ChannelModel> list, boolean callback) {

        ChannelUtil.logE("update => count = " + count + ", column = " + column + ", position = " + position + ", requestFocus = " + requestFocus + ", list = " + list);
        if (null == list || column < 0)
            return;

        View view = getChildAt(column);
        ChannelUtil.logE("update => column = " + column + ", view = " + view);
        if (null == view || !(view instanceof ChannelScrollView))
            return;

        ChannelUtil.logE("update => count = " + count + ", column = " + column + ", list = " + list);
        view.setVisibility((null != list && list.size() > 0) ? View.VISIBLE : View.GONE);
        ((ChannelScrollView) view).update(list);
        if (position < 0)
            return;
        select(Channeldirection.INIT, column, position, requestFocus, callback);
    }

    @Keep
    public final void select(@NonNull int column, @NonNull int position) {
        select(Channeldirection.INIT, column, position, false, true);
    }

    @Keep
    public final void select(@NonNull int column, @NonNull int position, boolean requestFocus) {
        select(Channeldirection.INIT, column, position, requestFocus, true);
    }

    @Keep
    public final void select(@NonNull int column, @NonNull int position, @NonNull boolean requestFocus, @NonNull boolean callback) {
        select(Channeldirection.INIT, column, position, requestFocus, callback);
    }

    @Keep
    public final void select(@Channeldirection.Value int direction, @NonNull int column, @NonNull int position, @NonNull boolean requestFocus, @NonNull boolean callback) {

        ChannelUtil.logE("select => direction = " + direction + ", column = " + column + ", position = " + position + ", requestFocus = " + requestFocus + ", callback = " + callback);
        if (direction != Channeldirection.INIT)
            return;

        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            scrollView.select(direction, position, requestFocus, callback);
        } catch (Exception e) {
            ChannelUtil.logE("select => " + e.getMessage(), e);
        }
    }

    @Keep
    public final void setVisibility(@NonNull int column, @NonNull int visibility) {

        if (column < 0)
            return;

        int count = getChildCount();
        if (count == 0)
            return;

        if (column + 1 > count)
            return;

        View child = getChildAt(column);
        if (null == child)
            return;

        child.setVisibility(visibility == View.VISIBLE ? View.VISIBLE : View.GONE);
//        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
//        if (null == layoutParams)
//            return;
//        layoutParams.width = visibility == View.VISIBLE ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
//        child.setLayoutParams(layoutParams);
    }

    @Keep
    public final int getSelectColumn() {
        int column = -1;
        if (getVisibility() == View.VISIBLE) {
            try {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    ViewGroup viewGroup = (ViewGroup) getChildAt(i);
                    View child = viewGroup.getChildAt(0);
                    if (child.hasFocus()) {
                        column = i;
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
        return column;
    }

    @Keep
    public final int getChildCount(@NonNull int column) {

        if (column < 0)
            return -1;

        int count = getChildCount();
        ChannelUtil.logE("getChildCount => count = " + count);
        if (count == 0)
            return -1;

        if (column + 1 > count)
            return -1;

        View child = getChildAt(column);
        if (null == child || !(child instanceof ChannelScrollView))
            return -1;

        ChannelScrollView scrollView = (ChannelScrollView) child;
        int childCount = scrollView.getChildCount();
        if (childCount != 1)
            return -1;

        View childAt = scrollView.getChildAt(0);
        if (null == childAt || !(childAt instanceof ChannelLinearLayoutChild))
            return -1;

        ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) childAt;
        int layoutChildChildCount = layoutChild.getChildCount();
        return layoutChildChildCount;
    }

    @Keep
    public final boolean selectNextDown(@NonNull int column) {
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            return scrollView.nextDown();
        } catch (Exception e) {
            return false;
        }
    }

    @Keep
    public final boolean selectNextUp(@NonNull int column) {
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            return scrollView.nextUp();
        } catch (Exception e) {
            return false;
        }
    }

    @Keep
    public final void clear(@NonNull int column, @NonNull int visable, boolean clearFocus) {

        // clean data
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            scrollView.clear();
        } catch (Exception e) {
        }

        // clear focus
        if (clearFocus) {
            clearFocuser(column);
        }

        // visable
        setVisibility(column, visable);
    }

    @Keep
    public final void clearFocuser(@NonNull int column) {

        // clearFocus
        try {
            ChannelScrollView scrollView = (ChannelScrollView) this.getChildAt(column);
            ChannelLinearLayoutChild linearLayoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
            linearLayoutChild.clearFocus();
        } catch (Exception var3) {
        }
    }

    @Keep
    public final void requestFocuser(@NonNull int column) {

        // clearFocus
        try {
            ChannelScrollView scrollView = (ChannelScrollView) this.getChildAt(column);
            ChannelLinearLayoutChild linearLayoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
            linearLayoutChild.requestFocus();
        } catch (Exception var3) {
        }
    }

    @Keep
    public final boolean isVisible(@NonNull int column) {

        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            return scrollView.getVisibility() == View.VISIBLE;
        } catch (Exception e) {
            return false;
        }
    }

    @Keep
    public final boolean isEqual(@NonNull int column) {

        if (column < 0)
            return false;

        int count = getChildCount();
        if (column + 1 >= count)
            return false;

        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
            int selectPosition = layoutChild.getSelectPosition();
            int highlightPosition = layoutChild.getHighlightPosition();
            return selectPosition == highlightPosition;
        } catch (Exception e) {
            return false;
        }
    }

    @Keep
    public final ChannelModel getTag(@NonNull int column, @NonNull boolean isHighlight) {

        if (column < 0)
            return null;

        int count = getChildCount();
        if (column + 1 >= count)
            return null;

        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
            int position;
            if (isHighlight) {
                position = layoutChild.getHighlightPosition();
            } else {
                position = layoutChild.getSelectPosition();
            }
            View child = layoutChild.getChildAt(position);
            return (ChannelModel) child.getTag(R.id.module_channel_tag_item);
        } catch (Exception e) {
            return null;
        }
    }

    @Keep
    public final boolean backupIndex(@NonNull int column, @NonNull boolean backupStyle) {
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            scrollView.backupIndex(backupStyle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Keep
    public final boolean backupStyle(@NonNull int column) {
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            scrollView.backupStyle();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*************************/

    private OnChannelChangeListener mOnChannelChangeListener;

    public final void setOnChannelChangeListener(@NonNull OnChannelChangeListener listener) {
        this.mOnChannelChangeListener = listener;
    }

    /*************************/

    protected final void callback(@NonNull int column, @NonNull int beforePosition, @NonNull int nextPosition, @NonNull int count, @Channeldirection.Value int direction, @NonNull ChannelModel value) {

        ChannelUtil.logE("callback11 => column = " + column + ", beforePosition = " + beforePosition + ", nextPosition = " + nextPosition + ", count = " + count + ", direction = " + direction + ", value = " + value);
        if (nextPosition < 0 || column < 0)
            return;

        int size = getChildCount();
        ChannelUtil.logE("callback11 => count = " + size);
        if (column + 1 > size)
            return;

        if (null != mOnChannelChangeListener) {

            // right
            if (direction == Channeldirection.RIGHT) {
                if (nextPosition == Integer.MAX_VALUE) {
                    nextPosition = -1;
                }
                mOnChannelChangeListener.onMove(column, nextPosition, beforePosition, count, value);
            }
            // left1
            else if (direction == Channeldirection.LEFT && beforePosition == nextPosition) {
                mOnChannelChangeListener.onRepeat(column, nextPosition, beforePosition, count, value);
            }
            // left2
            else if (direction == Channeldirection.LEFT && beforePosition == -1) {
                mOnChannelChangeListener.onRepeat(column, nextPosition, beforePosition, count, value);
            }
            // left3
            else if (direction == Channeldirection.LEFT) {
                mOnChannelChangeListener.onMove(column, nextPosition, beforePosition, count, value);
            }
            // select
            else if (direction == Channeldirection.INIT) {
                mOnChannelChangeListener.onInit(column, nextPosition, beforePosition, count, value);
            }
            // click
            else if (direction == Channeldirection.CLICK) {
                mOnChannelChangeListener.onClick(column, nextPosition, beforePosition, count, value);
            }
            // nextUp
            else if (direction == Channeldirection.NEXT_UP) {
                mOnChannelChangeListener.onClick(column, nextPosition, beforePosition, count, value);
            }
            // nextDown
            else if (direction == Channeldirection.NEXT_DOWN) {
                mOnChannelChangeListener.onClick(column, nextPosition, beforePosition, count, value);
            }
            // highlight
            else {
                setVisibility(View.VISIBLE);
                mOnChannelChangeListener.onHighlight(column, nextPosition, beforePosition, count, value);
            }
        }
    }
}
