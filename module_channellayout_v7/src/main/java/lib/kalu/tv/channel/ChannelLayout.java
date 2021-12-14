package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.tv.channel.listener.OnChannelChangeListener;
import lib.kalu.tv.channel.model.ChannelModel;

@Keep
public class ChannelLayout extends LinearLayout {

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

    /*************/

    @Override
    public int getOrientation() {
        return LinearLayout.HORIZONTAL;
    }

    private final void init(@Nullable AttributeSet attrs) {
        setFocusable(false);
        setFocusableInTouchMode(false);
        setClickable(false);
        setLongClickable(false);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.TRANSPARENT);
    }

    private final void refresh(int column, int visibility) {

        if (column < 0)
            return;

        int count = getChildCount();
        if (column + 1 > count)
            return;

        // arrow
        if (column == 3) {
            try {
                View child = getChildAt(column);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                int width;
                if (visibility == View.VISIBLE) {
                    width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_arrow_width);
                } else {
                    width = 0;
                }
                layoutParams.width = width;
                child.setLayoutParams(layoutParams);
            } catch (Exception e) {
            }
        }
        // item
        else {
            try {
                ChannelScrollView child = (ChannelScrollView) getChildAt(column);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                int width;
                if (visibility == View.VISIBLE) {
                    width = LayoutParams.WRAP_CONTENT;
                } else {
                    width = 0;
                }
                layoutParams.width = width;
                child.setLayoutParams(layoutParams);
                child.refresh(visibility);
            } catch (Exception e) {
            }
        }
    }

    @SuppressLint("Range")
    private final void add(@NonNull int visibility, @IntRange(from = 0, to = 3) int column, @NonNull List<ChannelModel> list) {

        ChannelUtil.logE("ChannelLayout[add] => 11");
        if (column > 3 || column < 0)
            return;
        ChannelUtil.logE("ChannelLayout[add] => 22");

        // arrow
        if (column == 3) {
            // add
            if (null == getChildAt(3)) {
                ChannelUtil.logE("ChannelLayout[add] => add");
                View child = new View(getContext());
                int width;
                if (visibility == View.VISIBLE) {
                    width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_arrow_width);
                } else {
                    width = 0;
                }
                child.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT));
//                child.setVisibility(visibility);
                child.setFocusable(false);
                child.setFocusableInTouchMode(false);
                child.setBackgroundResource(R.drawable.module_channellayout_ic_shape_background_column4);
                addView(child, 3);
            }
            // reset
            else {
                ChannelUtil.logE("ChannelLayout[add] => reset");
                View child = getChildAt(3);
                LinearLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                int width;
                if (visibility == View.VISIBLE) {
                    width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_arrow_width);
                } else {
                    width = 0;
                }
                layoutParams.width = width;
                child.setLayoutParams(layoutParams);
            }
        }
        // item
        else {

            @IdRes int id;
            if (column == 0) {
                id = R.id.module_channellayout_id_item0;
            } else if (column == 1) {
                id = R.id.module_channellayout_id_item1;
            } else {
                id = R.id.module_channellayout_id_item2;
            }

            // add
            if (null == getChildAt(column)) {
                ChannelUtil.logE("ChannelLayout[add] => add");
                ChannelScrollView child = new ChannelScrollView(getContext());
                int width;
                if (visibility == View.VISIBLE) {
                    width = LayoutParams.WRAP_CONTENT;
                } else {
                    width = 0;
                }
                child.setLayoutParams(new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT));
//                child.setVisibility(visibility);
                child.setId(id);
                child.update(visibility, column, list);
                addView(child, column);
            }
            // reset
            else {
                ChannelUtil.logE("ChannelLayout[add] => reset");
                View child = getChildAt(column);
                LinearLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                int width;
                if (visibility == View.VISIBLE) {
                    width = LayoutParams.WRAP_CONTENT;
                } else {
                    width = 0;
                }
                layoutParams.width = width;
                child.setLayoutParams(layoutParams);
                ((ChannelScrollView) child).update(visibility, column, list);
            }
        }
    }

    @Keep
    public final void refresh(@NonNull int column, @NonNull List<ChannelModel> list) {

        // column0
        if (column == 0 && null != list && list.size() > 0) {
            ChannelUtil.logE("ChannelLayout[refresh] => column0");
            add(View.VISIBLE, 0, list);
        }
        // column1
        else if (column == 1 && null != list && list.size() > 0) {
            ChannelUtil.logE("ChannelLayout[refresh] => column1");
            add(View.VISIBLE, 1, list);
        }
        // column2
        else if (column == 2 && null != list && list.size() > 0) {
            ChannelUtil.logE("ChannelLayout[refresh] => column2");
            add(View.VISIBLE, 2, list);
        }
    }

    @Keep
    public final void update(@NonNull List<ChannelModel> list0, @NonNull List<ChannelModel> list1, @NonNull List<ChannelModel> list2) {

        Object tag = getTag(R.id.module_channellayout_id_update);
        if (null != tag)
            throw new IllegalArgumentException("update must use once num");

        // tag
        setTag(R.id.module_channellayout_id_update, true);

        // column0
        if (null != list0 && list0.size() > 0) {
            ChannelUtil.logE("ChannelLayout[update] => column0");
            add(View.VISIBLE, 0, list0);
        } else {
            add(View.GONE, 0, null);
        }

        // column1
        if (null != list1 && list1.size() > 0) {
            ChannelUtil.logE("ChannelLayout[update] => column1");
            add(View.VISIBLE, 1, list1);
        } else {
            add(View.GONE, 1, null);
        }

        // column2
        if (null != list2 && list2.size() > 0) {
            ChannelUtil.logE("ChannelLayout[update] => column2");
            add(View.GONE, 2, list2);
        } else {
            add(View.GONE, 2, null);
        }

        // arrow
        if (null != list0 && list0.size() > 0 && null != list1 && list1.size() > 0) {
            add(View.VISIBLE, 3, null);
        } else {
            add(View.GONE, 3, null);
        }
    }

    @Keep
    public final void select() {
        select(-1, -1, -1);
    }

    @Keep
    public final void select(@NonNull int index0, @NonNull int index1) {
        select(index0, index1, -1);
    }

    @Keep
    public final void select(@NonNull int index0, @NonNull int index1, @NonNull int index2) {
        // item0
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) ((ChannelScrollView) getChildAt(0)).getChildAt(0);
            layoutChild.startFocus(index0, true);
        } catch (Exception e) {
        }
        // item1
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) ((ChannelScrollView) getChildAt(1)).getChildAt(0);
            layoutChild.startFocus(index1);
        } catch (Exception e) {
        }
        // item2
        try {
            ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) ((ChannelScrollView) getChildAt(2)).getChildAt(0);
            layoutChild.startFocus(index2);
        } catch (Exception e) {
        }
    }

    /*************************/

    private OnChannelChangeListener onChannelChangeListener;

    public final void setOnChannelChangeListener(@NonNull OnChannelChangeListener listener) {
        this.onChannelChangeListener = listener;
    }

    /*************************/

    protected final void callback(@NonNull ChannelScrollView view, @NonNull int position, int direction) {

        ChannelUtil.logE("callback22 => position = " + position);

        if (position < 0)
            return;

        int column = indexOfChild(view);
        ChannelUtil.logE("callback11 => column = " + column + ", direction = " + direction);
        if (column < 0)
            return;

        int count = getChildCount();
        ChannelUtil.logE("callback11 => count = " + count);
        if (column + 1 > count)
            return;

        if (null != onChannelChangeListener) {
            // right
            if (direction == View.FOCUS_RIGHT) {
                // column3
                refresh(column + 1, View.VISIBLE);
                // arraw
                refresh(3, column >= 1 ? View.GONE : View.VISIBLE);
                onChannelChangeListener.onMove(column + 1);
            }
            // left
            else if (direction == View.FOCUS_LEFT) {
                // column3
                refresh(column, View.GONE);
                // coloun2
                refresh(column - 1, View.VISIBLE);
                // arraw
                refresh(3, View.VISIBLE);
                onChannelChangeListener.onMove(column - 1);
            }
            // focus
            else {
                onChannelChangeListener.onFocus(column, position);
            }
        }
    }
}
