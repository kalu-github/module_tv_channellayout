package lib.kalu.tv.channel;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ScrollView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

import lib.kalu.tv.channel.model.ChannelModel;

@Keep
class ChannelScrollView extends ScrollView {
    public ChannelScrollView(Context context) {
        super(context);
        init();
    }

    public ChannelScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChannelScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChannelScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
//        super.setOnLongClickListener(l);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
//        super.setOnClickListener(l);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return false;
    }

    private final void init() {
        setClickable(false);
        setLongClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setFillViewport(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setBackgroundColor(Color.parseColor("#00000000"));

        // step1
        removeAllViews();

        // step2
        ChannelLinearLayoutChild child = new ChannelLinearLayoutChild(getContext());
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column0);
        addView(child);
    }

    /********************/

    protected final void update(@NonNull List<ChannelModel> list) {

        View child = getChildAt(0);
        if (null == child)
            return;

        // update
        ((ChannelLinearLayoutChild) child).update(list);
    }

    protected final boolean nextUp() {
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            int selectPosition = layoutChild.getSelectPosition();
            if (selectPosition == 0) {
                return false;
            } else {
                layoutChild.nextFocus(selectPosition, Channeldirection.NEXT_UP, true, true);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    protected final boolean nextDown() {
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            int count = layoutChild.getChildCount();
            int selectPosition = layoutChild.getSelectPosition();
            if (selectPosition + 1 >= count) {
                return false;
            } else {
                layoutChild.nextFocus(selectPosition, Channeldirection.NEXT_DOWN, true, true);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    protected final void select(@Channeldirection.Value int direction, @NonNull int position, @NonNull boolean select, @NonNull boolean callback) {

        if (direction != Channeldirection.INIT && direction != Channeldirection.SELECT)
            return;

        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            layoutChild.nextFocus(position, direction, select, callback);
        } catch (Exception e) {
        }

    }

    protected final void resetTags() {
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            layoutChild.resetTags();
        } catch (Exception e) {
        }
    }

    protected final void resethighLight() {
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            layoutChild.resetHighlight();
        } catch (Exception e) {
        }
    }

    protected final void updateParentHighlight() {
        try {
            ChannelLayout channelLayout = (ChannelLayout) getParent();
            int index = channelLayout.indexOfChild(this);
            if (index > 0) {
                ChannelScrollView scrollView = (ChannelScrollView) channelLayout.getChildAt(index - 1);
                scrollView.refreshParentHighlight();
            }
        } catch (Exception e) {
        }
    }

    private final void refreshParentHighlight() {
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            layoutChild.forceSelectEqualsHighlight();
        } catch (Exception e) {
        }
    }

    protected final void focus() {
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) getChildAt(0);
            layoutChild.resetHighlight();
            layoutChild.requestFocus();
        } catch (Exception e) {
        }
    }

    /*************/

    protected final void callback(@NonNull int position, @NonNull int count, @Channeldirection.Value int direction, @NonNull ChannelModel value) {

        ChannelUtil.logE("callback22 => position = " + position + ", count = " + count + ", direction = " + direction + ", value = " + value);
        if (position < 0)
            return;

        ViewParent parent = getParent();
        if (null != parent && parent instanceof ChannelLayout) {
            int column = ((ChannelLayout) parent).indexOfChild(this);
            ((ChannelLayout) parent).callback(column, position, count, direction, value);
        }
    }
}
