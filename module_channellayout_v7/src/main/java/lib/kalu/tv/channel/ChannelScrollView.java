package lib.kalu.tv.channel;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

    protected final void update(@IntRange(from = 0, to = 2) int index, @NonNull List<ChannelModel> list) {

        // add
        if (null == getChildAt(0)) {
            ChannelLinearLayoutChild child = new ChannelLinearLayoutChild(getContext());
            int height = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_height);
            ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, height);
            child.setLayoutParams(params);
            child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column0);
            child.update(index, list);
            // add
            addView(child, 0);
        }
        // reset
        else {
            View child = getChildAt(0);
            child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column0);
            ((ChannelLinearLayoutChild) child).update(index, list);
        }
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
