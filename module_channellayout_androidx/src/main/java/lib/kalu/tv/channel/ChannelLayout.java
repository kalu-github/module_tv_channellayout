package lib.kalu.tv.channel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        if (msg.what >= 5) {
            clearTime();
            setVisibility(View.INVISIBLE);
        } else {
            nextTime(msg.what);
        }
        return false;
    }

    @Override
    public void setVisibility(int visibility) {
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
        super.setVisibility(View.VISIBLE);
        autoTime(View.VISIBLE);
    }

    private final void gone() {
        super.setVisibility(View.INVISIBLE);
        autoTime(View.INVISIBLE);
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

        int column = 0;
        TypedArray attributes = null;
        try {
            attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ChannelLayout);
            column = attributes.getInt(R.styleable.ChannelLayout_cl_column_count, 0);
        } catch (Exception e) {
        }
        if (null != attributes) {
            attributes.recycle();
        }

        int size = column + 1;
        for (int i = 0; i < size; i++) {
            // image
            if (i + 1 == size) {
                ImageView child = new ImageView(getContext());
                int width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_arrow_width);
                child.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT));
                child.setFocusable(false);
                child.setFocusableInTouchMode(false);
                child.setClickable(false);
                child.setLongClickable(false);
                child.setImageResource(R.drawable.module_channellayout_ic_arrow);
                child.setBackgroundColor(Color.parseColor("#000000"));
                addView(child);
            }
            // item
            else {
                ChannelScrollView child = new ChannelScrollView(getContext());
                child.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                child.setBackgroundColor(Color.parseColor("#000000"));
                addView(child);
            }
        }
    }

    @Keep
    public final void setBackgroundResources(@DrawableRes int... resources) {

        int count = getChildCount();
        int length;
        try {
            length = resources.length;
        } catch (Exception e) {
            length = 0;
        }
        int min = Math.min(count, length);

        for (int i = 0; i < min; i++) {
            try {
                View view = getChildAt(i);
                view.setBackgroundResource(resources[i]);
            } catch (Exception e) {
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
        ChannelUtil.logE("select => count = " + count);
        if (count == 0)
            return;

        if (column + 1 > count)
            return;

        View child = getChildAt(column);
        if (null == child)
            return;

        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (null == layoutParams)
            return;
        layoutParams.width = visibility == View.VISIBLE ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
        child.setLayoutParams(layoutParams);
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

//    @Keep
//    public final int getSelectPosition(@NonNull int column) {
//
//        int position = -1;
//        try {
//            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
//            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
//            int count = layoutChild.getChildCount();
//            for (int i = 0; i < count; i++) {
//                View child = layoutChild.getChildAt(i);
//                ChannelUtil.logE("getClickablePosition => view = " + child + ", count = " + count);
//                if (null == child)
//                    continue;
//                boolean clickable = child.isClickable();
//                ChannelUtil.logE("getClickablePosition => column = " + column + ", i = " + i + ", clickable = " + clickable + ", text = " + ((TextView) child).getText());
//                if (!clickable) {
//                    position = i;
//                    break;
//                }
//            }
//        } catch (Exception e) {
//        }
//
//        if (position == -1) {
//            position = 0;
//        }
//        return position;
//    }

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
    public final void setEmpty(@NonNull int column) {
        int count = getChildCount();
        if (column + 1 >= count)
            return;
        View child = getChildAt(column);
        if (null == child || !(child instanceof ChannelScrollView))
            return;
        ChannelScrollView scrollView = (ChannelScrollView) child;
        int childCount = scrollView.getChildCount();
        if (childCount != 1)
            return;
        ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
        layoutChild.addEmpty();
    }

    @Keep
    public final void clear(@NonNull int column) {
        clear(column, false);
    }

    @Keep
    public final void clear(@NonNull int column, boolean focusMove) {

        // remove
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column);
            scrollView.clear();
        } catch (Exception e) {
        }

        if (!focusMove)
            return;

        // update
        focusAuto(Channeldirection.LEFT, column);
    }

    @Keep
    public final void focusAuto(@Channeldirection.Value int direction, @NonNull int column) {

        if (direction != Channeldirection.LEFT)
            return;

        //  focus move left
        if (column <= 0)
            return;
        try {
            ChannelScrollView scrollView = (ChannelScrollView) getChildAt(column - 1);
            scrollView.focusAuto();
        } catch (Exception e) {
        }
    }
//
//    @Keep
//    public final void clearFocusLinearLayout(@NonNull int column) {
//        ChannelLinearLayoutChild layoutChild = getChannelLinearLayoutChild(column);
//        if (null == layoutChild)
//            return;
//        layoutChild.clearFocus();
//    }
//
//    @Keep
//    public final void requestFocusLinearLayout(@NonNull int column) {
//        try {
//            ChannelLinearLayoutChild layoutChild = getChannelLinearLayoutChild(column);
//            if (null == layoutChild)
//                return;
//            layoutChild.requestFocus();
//        } catch (Exception e) {
//        }
//    }

//    @Keep
//    public final ChannelLinearLayoutChild getChannelLinearLayoutChild(@NonNull int column) {
//        int count = getChildCount();
//        if (column + 1 >= count)
//            return null;
//        View child = getChildAt(column);
//        if (null == child || !(child instanceof ChannelScrollView))
//            return null;
//        ChannelScrollView scrollView = (ChannelScrollView) child;
//        int childCount = scrollView.getChildCount();
//        if (childCount != 1)
//            return null;
//        ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) scrollView.getChildAt(0);
//        return layoutChild;
//    }
//
//    @Keep
//    public final ChannelTextView getChannelTextView(@NonNull int column, int position) {
//        try {
//            ChannelLinearLayoutChild channelLinearLayoutChild = getChannelLinearLayoutChild(column);
//            ChannelTextView channelTextView = (ChannelTextView) channelLinearLayoutChild.getChildAt(position);
//            return channelTextView;
//        } catch (Exception e) {
//            return null;
//        }
//    }

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
                mOnChannelChangeListener.onMove(column, nextPosition, count, value);
            }
            // left1
            else if (direction == Channeldirection.LEFT && beforePosition == nextPosition) {
                mOnChannelChangeListener.onRepeat(column, nextPosition, count, value);
            }
            // left2
            else if (direction == Channeldirection.LEFT && beforePosition == -1) {
                mOnChannelChangeListener.onRepeat(column, nextPosition, count, value);
            }
            // left3
            else if (direction == Channeldirection.LEFT) {
                mOnChannelChangeListener.onMove(column, nextPosition, count, value);
            }
            // select
            else if (direction == Channeldirection.INIT) {
                mOnChannelChangeListener.onInit(column, nextPosition, count, value);
            }
            // click
            else if (direction == Channeldirection.CLICK) {
                mOnChannelChangeListener.onClick(column, nextPosition, count, value);
            }
            // nextUp
            else if (direction == Channeldirection.NEXT_UP) {
                mOnChannelChangeListener.onClick(column, nextPosition, count, value);
            }
            // nextDown
            else if (direction == Channeldirection.NEXT_DOWN) {
                mOnChannelChangeListener.onClick(column, nextPosition, count, value);
            }
            // highlight
            else {
                mOnChannelChangeListener.onHighlight(column, nextPosition, count, value);
            }
        }
    }
}
