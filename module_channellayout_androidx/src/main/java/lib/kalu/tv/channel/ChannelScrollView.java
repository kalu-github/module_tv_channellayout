package lib.kalu.tv.channel;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
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
        setBackgroundColor(Color.TRANSPARENT);
    }

    /********************/

    protected final void update(@NonNull List<ChannelModel> list) {

        // add
        if (null == getChildAt(0)) {
            ChannelLinearLayoutChild child = new ChannelLinearLayoutChild(getContext());
            int height = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_height);
            ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, height);
            child.setLayoutParams(params);
            child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column0);
            child.update(list);
            // add
            addView(child, 0);
        }
        // reset
        else {
            View child = getChildAt(0);
            child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column0);
            ((ChannelLinearLayoutChild) child).update(list);
        }
    }

    protected final void nextUp(@NonNull int column, @NonNull int position) {
        if (position <= 0)
            return;
        int count1 = getChildCount();
        if (count1 != 1)
            return;
        View childAt = getChildAt(0);
        if (null == childAt || !(childAt instanceof ChannelLinearLayoutChild))
            return;
        ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) childAt;
        layoutChild.nextUp(column, position);
    }

    protected final void nextDown(@NonNull int column, @NonNull int position) {
        int count1 = getChildCount();
        if (count1 != 1)
            return;
        View childAt = getChildAt(0);
        if (null == childAt || !(childAt instanceof ChannelLinearLayoutChild))
            return;
        ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) childAt;
        int count2 = layoutChild.getChildCount();
        if (position + 1 > count2)
            return;
        layoutChild.nextDown(column, position);
    }

    /*************/

    protected final void callback(@NonNull int position, int direction) {

        ChannelUtil.logE("callback22 => position = " + position);
        if (position < 0)
            return;

        ViewParent parent = getParent();
        if (null != parent && parent instanceof ChannelLayout) {
            int column = ((ChannelLayout) parent).indexOfChild(this);
            ((ChannelLayout) parent).callback(column, position, direction);
        }
    }
}
