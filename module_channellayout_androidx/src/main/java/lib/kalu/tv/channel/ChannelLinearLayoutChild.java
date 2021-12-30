package lib.kalu.tv.channel;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.tv.channel.model.ChannelModel;

@Keep
class ChannelLinearLayoutChild extends LinearLayout {

    public ChannelLinearLayoutChild(Context context) {
        super(context);
        init();
    }

    public ChannelLinearLayoutChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChannelLinearLayoutChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChannelLinearLayoutChild(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        boolean showing;
        try {
            ViewGroup viewGroup = (ViewGroup) getParent().getParent();
            showing = (viewGroup.getVisibility() == View.VISIBLE);
        } catch (Exception e) {
            showing = false;
        }

        // repeat
        if (showing && event.getRepeatCount() > 0) {
            return true;
        }
        // down move
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            try {
                ChannelScrollView scroll = (ChannelScrollView) getParent();
                ChannelLayout layout = (ChannelLayout) scroll.getParent();
                int column = layout.indexOfChild(scroll);
                ChannelUtil.logE("dispatchKeyEvent[down move] => column = " + column);
                nextFocus(Channeldirection.DOWN, true);
            } catch (Exception e) {
            }
            return true;
        }
        // up move
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            try {
                ChannelScrollView scroll = (ChannelScrollView) getParent();
                ChannelLayout layout = (ChannelLayout) scroll.getParent();
                int column = layout.indexOfChild(scroll);
                ChannelUtil.logE("dispatchKeyEvent[up move] => column = " + column);
                nextFocus(Channeldirection.UP, true);
            } catch (Exception e) {
            }
            return true;
        }
        // right move
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            try {
                ChannelScrollView scroll = (ChannelScrollView) getParent();
                ChannelLayout layout = (ChannelLayout) scroll.getParent();
                int column = layout.indexOfChild(scroll);
                int count = layout.getChildCount();
                ChannelUtil.logE("dispatchKeyEvent[right move] => column = " + column + ", count = " + count);
                if (column + 1 < count) {
                    ChannelScrollView scrollNext = (ChannelScrollView) layout.getChildAt(column + 1);
                    ChannelLinearLayoutChild layoutNext = (ChannelLinearLayoutChild) scrollNext.getChildAt(0);
                    int childCount = layoutNext.getChildCount();
                    // 暂无节目
                    if (childCount == 0) {
                        ChannelUtil.logE("dispatchKeyEvent[right move] => empty");
                        keep(Channeldirection.RIGHT);
                        layoutNext.callback(Integer.MAX_VALUE, Channeldirection.RIGHT);
                        layoutNext.requestFocus();
                    }
                    // 正常显示
                    else {
                        ChannelUtil.logE("dispatchKeyEvent[right move] => next");
                        keep(Channeldirection.RIGHT);
                        clearFocus();
                        layoutNext.nextFocus(Channeldirection.RIGHT, true);
                        layoutNext.requestFocus();
                    }
                }
            } catch (Exception e) {
                ChannelUtil.logE("dispatchKeyEvent[right move] => exception");
            }
            return true;
        }
        // left move
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            try {
                ChannelScrollView scroll = (ChannelScrollView) getParent();
                ChannelLayout layout = (ChannelLayout) scroll.getParent();

                int column = layout.indexOfChild(scroll);
                ChannelUtil.logE("dispatchKeyEvent[left move] => column = " + column);
                if (column >= 1) {
                    ChannelScrollView scrollNext = (ChannelScrollView) layout.getChildAt(column - 1);
                    ChannelLinearLayoutChild layoutNext = (ChannelLinearLayoutChild) scrollNext.getChildAt(0);
                    int childCount = layoutNext.getChildCount();
                    // 暂无节目1
                    if (childCount == 0) {
                        ChannelUtil.logE("dispatchKeyEvent[left move] => empty");
                    }
                    // 暂无节目2
                    else if (childCount == 1 && layoutNext.getChildAt(0).getId() == R.id.module_channel_item_empty) {
                        ChannelUtil.logE("dispatchKeyEvent[left move] => empty");
                        removeAllViews();
                    }
                    // 正常显示
                    else {
                        ChannelUtil.logE("dispatchKeyEvent[left move] => next");
                    }
                    // before
                    keep(Channeldirection.LEFT);
                    clearFocus();
                    // next
                    layoutNext.nextFocus(Channeldirection.LEFT, true);
                    layoutNext.requestFocus();
                }
            } catch (Exception e) {
                ChannelUtil.logE("dispatchKeyEvent[left move] => exception");
            }
            return true;
        }
        // click1
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int selectPosition = getSelectPosition();
                int highlightPosition = getHighlightPosition();
                if (selectPosition != highlightPosition) {
                    updateTags(true);
                    setSelectPosition(highlightPosition);
                    try {
                        ChannelScrollView scrollView = (ChannelScrollView) getParent();
                        scrollView.updateParentHighlight();
                    } catch (Exception e) {
                    }
                    callback(highlightPosition, Channeldirection.SELECT);
                    ChannelUtil.logE("dispatchKeyEvent[enter click] => position = " + highlightPosition);
                }
                return true;
            }
        }
        // click2
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int selectPosition = getSelectPosition();
                int highlightPosition = getHighlightPosition();
                if (selectPosition != highlightPosition) {
                    updateTags(true);
                    setSelectPosition(highlightPosition);
                    try {
                        ChannelScrollView scrollView = (ChannelScrollView) getParent();
                        scrollView.updateParentHighlight();
                    } catch (Exception e) {
                    }
                    callback(highlightPosition, Channeldirection.SELECT);
                    ChannelUtil.logE("dispatchKeyEvent[center click] => position = " + highlightPosition);
                }
                return true;
            }
        }
        // else
        else {
            View focus = findFocus();
            ChannelUtil.logE("dispatchKeyEvent[] => focus = " + focus + ", action = " + event.getAction() + ", code = " + event.getKeyCode());
        }
        return super.dispatchKeyEvent(event);
    }

    /**************/

    protected final int getHighlightPosition() {
        try {
            return (int) getTag(R.id.module_channel_position_highlight);
        } catch (Exception e) {
            return 0;
        }
    }

    private final void setHighlightPosition(@NonNull int position) {
        if (position < 0)
            return;
        int count = getChildCount();
        if (position >= count)
            return;

        try {
            int highlightPosition = getHighlightPosition();
            if (highlightPosition != position) {
                ChannelTextView textView = (ChannelTextView) getChildAt(highlightPosition);
                textView.setBackgroundDefault();
                textView.setLeftDrawable(false);
                textView.setBackgroundDefault();
            }
        } catch (Exception e) {
        }

        setTag(R.id.module_channel_position_highlight, position);
    }

    protected final int getSelectPosition() {
        try {
            return (int) getTag(R.id.module_channel_position_select);
        } catch (Exception e) {
            return 0;
        }
    }

    private final void setSelectPosition(@NonNull int position) {
        if (position < 0)
            return;
        int count = getChildCount();
        if (position >= count)
            return;

        try {
            int selectPosition = getSelectPosition();
            if (selectPosition != position) {
                ChannelTextView textView = (ChannelTextView) getChildAt(selectPosition);
                textView.setBackgroundDefault();
                textView.setLeftDrawable(false);
                textView.setBackgroundDefault();
            }
        } catch (Exception e) {
        }
        setTag(R.id.module_channel_position_select, position);
    }

    /**************/

    private final void keep(@Channeldirection.Value int direction) {

        if (direction != Channeldirection.LEFT && direction != Channeldirection.RIGHT)
            return;

        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        ChannelUtil.logE("keep => direction = " + direction + ", selectPosition = " + selectPosition + ", highlightPosition = " + highlightPosition);

        // 1
        ChannelTextView view = null;
        try {
            view = (ChannelTextView) getChildAt(highlightPosition);
        } catch (Exception e) {
        }

        // 2
        if (null != view) {
            view.setTextColorSelect();
            view.setLeftDrawable(selectPosition == highlightPosition);
            view.setBackgroundDefault();
        }
    }

    /**************/

    private final void init() {
        setClickable(true);
        setLongClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setGravity(Gravity.CENTER);
        setOrientation(LinearLayout.VERTICAL);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    private final void addItem(@NonNull ChannelModel model) {

        if (null == model)
            return;

        CharSequence initText = model.initText();
        if (null == initText || initText.length() == 0)
            return;

        ChannelTextView child = new ChannelTextView(getContext());
        child.setId(R.id.module_channel_item_standard);
        child.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        int offset = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_size);
        child.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset);
        int left = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_left);
        int right = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_right);
        int top = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_top);
        int bottom = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_bottom);
        child.setPadding(left, top, right, bottom);
        child.setText(initText);
        child.setTag(R.id.module_channel_tag_item, model);
        addView(child);
    }

    protected final void addEmpty() {
        ChannelTextView child = new ChannelTextView(getContext());
        child.setId(R.id.module_channel_item_empty);
        child.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        int offset = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_size);
        child.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset);
        int left = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_left);
        int right = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_right);
        int top = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_top);
        int bottom = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_bottom);
        child.setPadding(left, top, right, bottom);
        child.setText("暂无节目");

        addView(child);
    }

    protected final void nextFocus(@Channeldirection.Value int direction, boolean select) {

        if (direction < Channeldirection.MIN || direction > Channeldirection.MAX)
            return;

        int highlightPosition = getHighlightPosition();
        nextFocus(highlightPosition, direction, select, true);
    }

    protected final void nextFocus(@NonNull int highlightPosition, @Channeldirection.Value int direction, boolean select, @NonNull boolean callback) {

        if (direction < Channeldirection.MIN || direction > Channeldirection.MAX)
            return;

        ChannelUtil.logE("nextFocus => ****************************");

        int selectPosition = getSelectPosition();
        int count = getChildCount();
        int nextPosition;

        // down
        if (direction == Channeldirection.DOWN) {
            nextPosition = highlightPosition + 1;
        }
        // nextDown
        else if (direction == Channeldirection.NEXT_DOWN) {
            nextPosition = highlightPosition + 1;
        }
        // up
        else if (direction == Channeldirection.UP) {
            nextPosition = highlightPosition - 1;
        }
        // nextUp
        else if (direction == Channeldirection.NEXT_UP) {
            nextPosition = highlightPosition - 1;
        }
        // init
        else {
            nextPosition = highlightPosition;
        }

        ChannelUtil.logE("nextFocus => highlightPosition = " + highlightPosition + ", selectPosition = " + selectPosition + ", nextPosition = " + nextPosition + ", count = " + count);
        if (nextPosition < 0 || nextPosition >= count)
            return;

        ChannelTextView before = null;
        try {
            if (nextPosition != highlightPosition) {
                before = (ChannelTextView) getChildAt(highlightPosition);
            }
        } catch (Exception e) {
        }
        ChannelTextView next = null;
        try {
            next = (ChannelTextView) getChildAt(nextPosition);
        } catch (Exception e) {
        }

        // setHighlightPosition
        setHighlightPosition(nextPosition);
        int highlightPosition1 = getHighlightPosition();
        ChannelUtil.logE("nextFocus => highlightPosition1 = " + highlightPosition1);

        // setSelectPosition
        if (direction == Channeldirection.NEXT_UP || direction == Channeldirection.NEXT_DOWN || direction == Channeldirection.INIT || direction == Channeldirection.SELECT) {
            setSelectPosition(nextPosition);
            int selectPosition1 = getSelectPosition();
            ChannelUtil.logE("nextFocus => selectPosition1 = " + selectPosition1);
        }

        try {
            ChannelUtil.logE("nextFocus => before = " + before.getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus => before = null");
        }
        try {
            ChannelUtil.logE("nextFocus => next = " + next.getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus => next = null, message = " + e.getMessage(), e);
        }

        // before
        if (null != before && direction == Channeldirection.NEXT_DOWN) {
            before.setTextColorDefault();
            before.setLeftDrawable(false);
            before.setBackgroundDefault();
        }
        // before
        else if (null != before && direction == Channeldirection.NEXT_UP) {
            before.setTextColorDefault();
            before.setLeftDrawable(false);
            before.setBackgroundDefault();
        }
        // before
        else if (null != before) {
            before.setTextColorDefault();
            before.setLeftDrawable(highlightPosition == selectPosition);
            before.setBackgroundDefault();
        }

        // next
        if (null != next) {

            // scroll => init
            if (direction == Channeldirection.INIT) {
                scrolInit(next);
            }
            // scroll => down
            else if (direction == Channeldirection.DOWN) {
                scrolDown(next);
            }
            // scroll => up
            else if (direction == Channeldirection.UP) {
                scrolUp(next);
            }

            // up down
            if (direction == Channeldirection.UP || direction == Channeldirection.DOWN) {
                next.setLeftDrawable(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // next-up next-down
            else if (direction == Channeldirection.NEXT_UP || direction == Channeldirection.NEXT_DOWN) {
                next.setLeftDrawable(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // left right
            else if (direction == Channeldirection.LEFT || direction == Channeldirection.RIGHT) {
                next.setLeftDrawable(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // select
            else if (direction == Channeldirection.SELECT) {
                next.setLeftDrawable(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // init1
            else if (select && direction == Channeldirection.INIT) {
                next.setLeftDrawable(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // init2
            else if (direction == Channeldirection.INIT) {
                next.setLeftDrawable(true);
                next.setTextColorDefault();
                next.setBackgroundDefault();
            }

            // requestFocus
            if (select) {
                requestFocus();
            }
            // clearFocus
            else {
                clearFocus();
            }
        }

        // callback
        if (callback && null != next) {
            // callback
            if (direction == Channeldirection.SELECT && !select) {
            }
            // callback
            else {
                callback(nextPosition, direction);
            }
        }

        ChannelUtil.logE("nextFocus => ****************************");
    }

    private final void scrolUp(@NonNull View view) {

        if (null == view)
            return;

        int top = view.getTop();
        int scrollY = ((ViewGroup) getParent()).getScrollY();
        ChannelUtil.logE("scrolUp => top = " + top + ", scrollY = " + scrollY);
        if (top < scrollY) {
            ((ChannelScrollView) getParent()).smoothScrollBy(0, -Math.abs(scrollY - top));
        }
    }

    private final void scrolInit(@NonNull View view) {

        if (null == view)
            return;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                scrolDown(view);
            }
        }, 100);
    }

    private final void scrolDown(@NonNull View view) {

        if (null == view)
            return;

        int bottom = view.getBottom();
        int scrollY = ((ViewGroup) getParent()).getScrollY();
        int measuredHeight = ((ViewGroup) getParent()).getMeasuredHeight() + scrollY;
        ChannelUtil.logE("scrolDown => bottom = " + bottom + ", scrollY = " + scrollY + ", measuredHeight = " + measuredHeight);
        if (bottom > measuredHeight) {
            ((ChannelScrollView) getParent()).smoothScrollBy(0, Math.abs(bottom - measuredHeight));
        }
    }

    /*************/

//    protected final void select(@NonNull int column, @NonNull int position) {
//        select(column, position, false);
//    }
//    protected final void select(@NonNull int position, @NonNull boolean select) {
//        int count = getChildCount();
//        if (position + 1 > count)
//            return;
//
//        if (select) {
//            requestFocus();
//        }
//
//        nextFocus(position, Channeldirection.SELECT, select);
//    }

//    protected final void select(@NonNull int column, @NonNull int position , @NonNull int direction, @NonNull boolean select) {
//        int count = getChildCount();
//        if (position + 1 > count)
//            return;
//
//        if (select) {
//            requestFocus();
//        }
//        nextFocus(column, position, direction, select);
//    }

    /*************/

    protected final void forceSelectEqualsHighlight() {
        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        setSelectPosition(highlightPosition);
        if (selectPosition != highlightPosition) {
            try {
                ChannelTextView textView = (ChannelTextView) getChildAt(selectPosition);
                textView.setTextColorDefault();
                textView.setLeftDrawable(false);
                textView.setBackgroundDefault();
            } catch (Exception e) {
            }
        }
        try {
            ChannelTextView textView = (ChannelTextView) getChildAt(highlightPosition);
            if (hasFocus()) {
                textView.setTextColorHighlight();
                textView.setLeftDrawable(true);
                textView.setBackgroundHighlight();
            } else {
                textView.setTextColorSelect();
                textView.setLeftDrawable(true);
                textView.setBackgroundDefault();
            }
        } catch (Exception e) {
        }
    }

    protected final void resetHighlight() {
        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        setHighlightPosition(selectPosition);
        if (selectPosition != highlightPosition) {
            try {
                ChannelTextView textView = (ChannelTextView) getChildAt(highlightPosition);
                textView.setTextColorDefault();
                textView.setLeftDrawable(false);
                textView.setBackgroundDefault();
            } catch (Exception e) {
            }
        }
        try {
            ChannelTextView textView = (ChannelTextView) getChildAt(selectPosition);
            if (hasFocus()) {
                textView.setTextColorHighlight();
                textView.setLeftDrawable(true);
                textView.setBackgroundHighlight();
            } else {
                textView.setTextColorSelect();
                textView.setLeftDrawable(true);
                textView.setBackgroundDefault();
            }
        } catch (Exception e) {
        }
    }

    protected final void resetTags() {
        List<ChannelModel> list = getTags();
        if (null == list || list.size() == 0)
            return;
        update(list);
    }

    private final List<ChannelModel> getTags() {
        try {
            List<ChannelModel> tags = (List<ChannelModel>) getTag(R.id.module_channel_tag_all);
            return tags;
        } catch (Exception e) {
            return null;
        }
    }

    private final void updateTags(boolean clear) {

        if (clear) {
            setTag(R.id.module_channel_tag_all, null);
        }

        List<ChannelModel> tags = getTags();
        if (null != tags)
            return;

        int count = getChildCount();
        if (count <= 0) {
            setTag(R.id.module_channel_tag_all, null);
        } else {
            ArrayList<ChannelModel> cache = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                try {
                    ChannelTextView textView = (ChannelTextView) getChildAt(i);
                    ChannelModel model = (ChannelModel) textView.getTag(R.id.module_channel_tag_item);
                    cache.add(model);
                } catch (Exception e) {
                }
            }
            setTag(R.id.module_channel_tag_all, cache);
        }
    }

    protected final void update(@NonNull List<ChannelModel> list) {

        ChannelUtil.logE("**********************");

        // step0 - tag all
        updateTags(false);

        // step1
        removeAllViews();

        int size = list.size();
        ChannelUtil.logE("update => size = " + size);

        // step2
        for (int i = 0; i < size; i++) {

            ChannelModel temp = list.get(i);
            CharSequence initText = temp.initText();
            if (null == initText || initText.length() == 0)
                continue;

            ChannelUtil.logE("update => i = " + i + " initText = " + initText);
            addItem(temp);
        }

        ChannelUtil.logE("**********************");
    }

    /*************/

    protected final void callback(@NonNull int position, @Channeldirection.Value int direction) {

        ChannelUtil.logE("callback33 => position = " + position + ", direction = " + direction);
        if (position < 0)
            return;

        int count = getChildCount();
        ChannelUtil.logE("callback33 => count = " + count);
        if (position + 1 > count) {
            return;
        }

        ChannelModel value;
        try {
            View child = getChildAt(position);
            value = (ChannelModel) child.getTag(R.id.module_channel_tag_item);
        } catch (Exception e) {
            value = null;
        }

        if (null == getParent() || !(getParent() instanceof ChannelScrollView))
            return;
        ((ChannelScrollView) getParent()).callback(position, count, direction, value);
    }
}
