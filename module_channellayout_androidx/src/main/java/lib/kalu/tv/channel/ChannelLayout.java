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

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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
        super.setVisibility(visibility);
        // 5s隐藏
        autoTime(visibility);
        // 选中上一次
        requestFocusLastTime();
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

    private final void requestFocusLastTime() {

        if (getVisibility() != View.VISIBLE)
            return;

        int count = getChildCount();
        ChannelUtil.logE("requestFocusLastTime => count = " + count);

        for (int i = count - 1; i >= 0; i--) {
            View scroll = getChildAt(i);
            if (null == scroll || !(scroll instanceof ChannelScrollView))
                continue;
            ChannelLinearLayoutChild layoutChild = ((ChannelLinearLayoutChild) ((ChannelScrollView) scroll).getChildAt(0));
            int childCount = layoutChild.getChildCount();
            for (int m = 0; m <= childCount; m++) {
                View text = layoutChild.getChildAt(m);
                if (null == text || !(text instanceof ChannelTextView))
                    continue;
                // empty
                if (text.getId() == R.id.module_channel_item_empty) {
                    break;
//                    ((ChannelLinearLayoutChild) ((ChannelScrollView) view).getChildAt(0)).removeAllViews();
                }
                // select
                else if (!text.isClickable() && text.getId() == R.id.module_channel_item_standard) {
                    layoutChild.requestFocus();
                    return;
                }
            }
        }
    }

    protected boolean removeLastEmpty() {
        boolean hasEmpty = false;
        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (null != child && child instanceof ChannelScrollView) {
                ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) ((ChannelScrollView) child).getChildAt(0);
                View childAt = layoutChild.getChildAt(0);
                if (null != childAt && childAt.getId() == R.id.module_channel_item_empty) {
                    hasEmpty = true;
                    layoutChild.removeAllViews();
                }
                break;
            }
        }
        return hasEmpty;
    }

    private final void init(@Nullable AttributeSet attrs) {
        setClickable(false);
        setLongClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
//        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.parseColor("#00000000"));

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
                child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column4);
                addView(child);
            }
            // item
            else {
                ChannelScrollView child = new ChannelScrollView(getContext());
                child.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                addView(child);
            }
        }
    }

    private final void nextUp(@NonNull int column, @NonNull int position) {
        if (position < 0 || column < 0)
            return;
        int count = getChildCount();
        if (count == 0)
            return;
        if (column + 1 > count)
            return;
        for (int i = 0; i < count; i++) {
            if (i != column)
                continue;
            View child = getChildAt(i);
            if (null == child || !(child instanceof ChannelScrollView))
                continue;
            ((ChannelScrollView) child).nextUp(column, position);
            break;
        }
    }

    private final void nextDown(@NonNull int column, @NonNull int position) {
        if (position < 0 || column < 0)
            return;
        int count = getChildCount();
        if (count == 0)
            return;
        if (column + 1 > count)
            return;
        for (int i = 0; i < count; i++) {
            if (i != column)
                continue;
            View child = getChildAt(i);
            if (null == child || !(child instanceof ChannelScrollView))
                continue;
            ((ChannelScrollView) child).nextDown(column, position);
            break;
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
            update(size, i, 0, temp);
        }
    }

    @Keep
    public final void update(@NonNull int count, @NonNull int column, @NonNull List<ChannelModel> list) {
        update(count, column, 0, list);
    }

    @Keep
    public final void update(@NonNull int count, @NonNull int column, @NonNull int defaultPosition, @NonNull List<ChannelModel> list) {

        ChannelUtil.logE("update => count = " + count + ", column = " + column + ", defaultPosition = " + defaultPosition + ", list = " + list);
        if (null == list || column < 0)
            return;

        View view = getChildAt(column);
        ChannelUtil.logE("update => column = " + column + ", view = " + view);
        if (null == view || !(view instanceof ChannelScrollView))
            return;

        ChannelUtil.logE("addItem[reset0] => count = " + count + ", column = " + column + ", list = " + list);
        ((ChannelScrollView) view).update(list);
        select(column, defaultPosition, defaultPosition > 0);
    }

    @Keep
    public final void select(@NonNull int column, @NonNull int position) {
        select(column, position, false);
    }

    @Keep
    public final void select(@NonNull int column, @NonNull int position, boolean select) {
        ChannelUtil.logE("select => ****************************");
        ChannelUtil.logE("select => column = " + column + ", position = " + position + ", select = " + select);

        if (position < 0 || column < 0)
            return;

        int count = getChildCount();
        ChannelUtil.logE("select => count = " + count);
        if (count == 0)
            return;

        if (column + 1 > count)
            return;

        for (int i = 0; i < count; i++) {
            if (i != column)
                continue;
            View child = getChildAt(i);
            ChannelUtil.logE("select => child = " + child);
            if (null == child || !(child instanceof ChannelScrollView))
                continue;
            ChannelScrollView scrollView = (ChannelScrollView) child;
            int childCount = scrollView.getChildCount();
            ChannelUtil.logE("select => childCount = " + childCount);
            if (childCount != 1)
                continue;
            View childAt = scrollView.getChildAt(0);
            ChannelUtil.logE("select => childAt = " + childAt);
            if (null == childAt || !(childAt instanceof ChannelLinearLayoutChild))
                continue;
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) childAt;
            int layoutChildChildCount = layoutChild.getChildCount();
            ChannelUtil.logE("select => layoutChildChildCount = " + layoutChildChildCount);
            if (position + 1 > layoutChildChildCount)
                continue;
            layoutChild.select(column, position, select);
            break;
        }
        ChannelUtil.logE("select => ****************************");
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

    @Keep
    public final int getSelectPosition(@NonNull int column) {

        if (column < 0)
            return -1;

        int count = getChildCount();
        ChannelUtil.logE("getClickablePosition => count = " + count);
        if (count <= 0)
            return -1;

        if (column + 1 > count)
            return -1;

        View child1 = getChildAt(column);
        if (null == child1 || !(child1 instanceof ChannelScrollView))
            return -1;

        int count1 = ((ChannelScrollView) child1).getChildCount();
        if (count1 != 1)
            return -1;

        View child2 = ((ChannelScrollView) child1).getChildAt(0);
        if (null == child2 || !(child2 instanceof ChannelLinearLayoutChild))
            return -1;

        int count2 = ((ChannelLinearLayoutChild) child2).getChildCount();
        ChannelUtil.logE("getClickablePosition => count2 = " + count2);
        if (count2 < 0)
            return -1;

        int position = -1;
        for (int i = 0; i < count2; i++) {
            View child3 = ((ChannelLinearLayoutChild) child2).getChildAt(i);
            ChannelUtil.logE("getClickablePosition => view = " + child3 + ", count2 = " + count2);
            if (null == child3)
                continue;
            boolean clickable = child3.isClickable();
            ChannelUtil.logE("getClickablePosition => column = " + column + ", i = " + i + ", clickable = " + clickable + ", text = " + ((TextView) child3).getText());
            if (!clickable) {
                position = i;
                break;
            }
        }
        return position;
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
    public final void selectNextDown(@NonNull int column) {
        int position = getSelectPosition(column);
        if (position < 0)
            return;

        int count = getChildCount(column);
        if (position + 1 >= count)
            return;
        nextDown(column, position);
    }

    @Keep
    public final void selectNextUp(@NonNull int column) {
        int position = getSelectPosition(column);
        if (position <= 0)
            return;
        nextUp(column, position);
    }

    @Keep
    public final void addEmpty(@NonNull int column) {
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
    public final void removeAllViews(@NonNull int column) {
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
        layoutChild.removeAllViews();
    }

    /*************************/

    private OnChannelChangeListener onChannelChangeListener;

    public final void setOnChannelChangeListener(@NonNull OnChannelChangeListener listener) {
        this.onChannelChangeListener = listener;
    }

    /*************************/

    protected final void callback(@NonNull int column, @NonNull int position, @NonNull int direction, @NonNull ChannelModel value) {

        ChannelUtil.logE("callback11 => column = " + column + ", position = " + position + ", direction = " + direction + ", value = " + value);
        if (position < 0 || column < 0)
            return;

        int count = getChildCount();
        ChannelUtil.logE("callback11 => count = " + count);
        if (column + 1 > count)
            return;

        if (null != onChannelChangeListener) {
            // right
            if (direction == View.FOCUS_RIGHT) {
                if (position == Integer.MAX_VALUE) {
                    position = -1;
                }
                onChannelChangeListener.onMove(column, position, value);
            }
            // left
            else if (direction == View.FOCUS_LEFT) {
                onChannelChangeListener.onMove(column, position, value);
            }
            // init
            else if (direction == Integer.MIN_VALUE) {
                onChannelChangeListener.onSelect(column, position, value);
            }
            // click
            else if (direction == Integer.MAX_VALUE) {
                onChannelChangeListener.onSelect(column, position, value);
            }
            // nextUp
            else if (direction == 1111) {
                onChannelChangeListener.onSelect(column, position, value);
            }
            // nextDown
            else if (direction == 2222) {
                onChannelChangeListener.onSelect(column, position, value);
            }
            // highlight
            else {
                onChannelChangeListener.onHighlight(column, position, value);
            }
        }
    }
}
