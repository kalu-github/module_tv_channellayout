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
        setFocusable(false);
        setFocusableInTouchMode(false);
        setFillViewport(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setBackgroundColor(Color.TRANSPARENT);
    }

    /********************/

    protected final void refresh(int visibility) {

        View child = getChildAt(0);
        if (null == child)
            return;

        ((ChannelLinearLayoutChild) child).refresh(visibility);
    }

    protected final void update(@NonNull int visibility, @IntRange(from = 0, to = 2) int index, @NonNull List<ChannelModel> list) {

        @IdRes int id;
        @DrawableRes int res;
        if (index == 0) {
            res = R.drawable.module_channellayout_ic_shape_background_column0;
            id = R.id.module_channellayout_id_item0_root;
        } else if (index == 1) {
            res = R.drawable.module_channellayout_ic_shape_background_column1;
            id = R.id.module_channellayout_id_item1_root;
        } else {
            res = R.drawable.module_channellayout_ic_shape_background_column2;
            id = R.id.module_channellayout_id_item2_root;
        }


        // add
        if (null == getChildAt(0)) {
            ChannelLinearLayoutChild child = new ChannelLinearLayoutChild(getContext());
            child.setId(id);
            child.setBackgroundResource(res);
            int height = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_height);
            ScrollView.LayoutParams params = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, height);
            child.setLayoutParams(params);
//            child.setVisibility(visibility);
            child.update(visibility, index, list);
            // add
            addView(child, 0);
        }
        // reset
        else {
            View child = getChildAt(0);
            child.setBackgroundResource(res);
//            child.setVisibility(visibility);
            ((ChannelLinearLayoutChild) child).update(visibility, index, list);
        }
    }

    /*************/

    protected final void callback(@NonNull int position, int direction) {

        ChannelUtil.logE("callback22 => position = " + position);
        if (position < 0)
            return;

        try {
            ((ChannelLayout) getParent()).callback(this, position, direction);
        } catch (Exception e) {
        }
    }
}
