package lib.kalu.tv.channel;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
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

        // repeat
        if (hasFocus() && event.getRepeatCount() > 0) {
            return true;
        }
        // down move
        else if (hasFocus() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
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
        else if (hasFocus() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
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
        else if (hasFocus() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            try {

                ChannelScrollView scroll = (ChannelScrollView) getParent();
                ChannelLayout layout = (ChannelLayout) scroll.getParent();
                int column = layout.indexOfChild(scroll);
                int count = layout.getChildCount();
                ChannelUtil.logE("dispatchKeyEvent[right move] => column = " + column + ", count = " + count);

                ChannelScrollView scrollNext = (ChannelScrollView) layout.getChildAt(column + 1);
                ChannelLinearLayoutChild layoutNext = (ChannelLinearLayoutChild) scrollNext.getChildAt(0);
                int countNext = layoutNext.getChildCount();

                // error
                if (column + 1 >= count) {
                    ChannelUtil.logE("dispatchKeyEvent[right move] => error");
                }
                // empty
                else if (countNext <= 0) {
                    ChannelUtil.logE("dispatchKeyEvent[right move] => empty");
                    keep(Channeldirection.RIGHT);
                    layoutNext.callback(-1, Integer.MAX_VALUE, Channeldirection.RIGHT);
                    layoutNext.requestFocus();
                }
                // next
                else {
                    ChannelUtil.logE("dispatchKeyEvent[right move] => next");
                    clearFocus();
                    keep(Channeldirection.RIGHT);
                    layoutNext.requestFocus();
                    layoutNext.nextFocus(Channeldirection.RIGHT, true);
                }
            } catch (Exception e) {
                ChannelUtil.logE("dispatchKeyEvent[right move] => exception = " + e.getMessage(), e);
            }
            return true;
        }
        // left move
        else if (hasFocus() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
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
                    clearFocus();
                    keep(Channeldirection.LEFT);
                    // next
                    layoutNext.requestFocus();
                    layoutNext.nextFocus(Channeldirection.LEFT, true);
                }
            } catch (Exception e) {
                ChannelUtil.logE("dispatchKeyEvent[left move] => exception");
            }
            return true;
        }
        // click1
        else if (hasFocus() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelScrollView scrollView = (ChannelScrollView) getParent();
                ChannelLayout channelLayout = (ChannelLayout) scrollView.getParent();
                int column = channelLayout.indexOfChild(scrollView);
                int selectPosition = getSelectPosition();
                int highlightPosition = getHighlightPosition();
                ChannelUtil.logE("dispatchKeyEvent[enter click] => column = " + column + ", selectPosition = " + selectPosition + ", highlightPosition = " + highlightPosition);

                boolean pass = false;
                if (column > 0 && selectPosition >= 0 && highlightPosition >= 0 && selectPosition != highlightPosition) {
                    pass = true;
                } else if (column > 0 && highlightPosition < 0) {
                    highlightPosition = 0;
                    pass = true;
                } else if (column > 0 && selectPosition < 0) {
                    pass = true;
                }
                if (pass) {

                    // step1
                    scrollView.updateLeft();

                    // step2
                    List<ChannelModel> tags = getTags(true);
                    updateTags(tags);

                    // step3
                    setBeforePosition(highlightPosition);
                    setSelectPosition(highlightPosition, true);
                    setHighlightPosition(highlightPosition, true);

                    // step4
                    callback(selectPosition, highlightPosition, Channeldirection.CLICK);
                    ChannelUtil.logE("dispatchKeyEvent[enter click] => position = " + highlightPosition);
                }
            }
            return true;
        }
        // click2
        else if (hasFocus() && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelScrollView scrollView = (ChannelScrollView) getParent();
                ChannelLayout channelLayout = (ChannelLayout) scrollView.getParent();
                int column = channelLayout.indexOfChild(scrollView);
                int selectPosition = getSelectPosition();
                int highlightPosition = getHighlightPosition();
                ChannelUtil.logE("dispatchKeyEvent[enter click] => column = " + column + ", selectPosition = " + selectPosition + ", highlightPosition = " + highlightPosition);

                boolean pass = false;
                if (column > 0 && selectPosition >= 0 && highlightPosition >= 0 && selectPosition != highlightPosition) {
                    pass = true;
                } else if (column > 0 && highlightPosition < 0) {
                    highlightPosition = 0;
                    pass = true;
                } else if (column > 0 && selectPosition < 0) {
                    pass = true;
                }
                if (pass) {

                    // step1
                    scrollView.updateLeft();

                    // step2
                    List<ChannelModel> tags = getTags(true);
                    updateTags(tags);

                    // step3
                    setBeforePosition(highlightPosition);
                    setSelectPosition(highlightPosition, true);
                    setHighlightPosition(highlightPosition, true);

                    // step4
                    callback(selectPosition, highlightPosition, Channeldirection.CLICK);
                    ChannelUtil.logE("dispatchKeyEvent[center click] => position = " + highlightPosition);
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /***********************/

    private final void keep(@Channeldirection.Value int direction) {

        if (direction != Channeldirection.LEFT && direction != Channeldirection.RIGHT)
            return;

        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        int beforePosition = getBeforePosition();
        ChannelUtil.logE("keep => direction = " + direction + ", selectPosition = " + selectPosition + ", highlightPosition = " + highlightPosition + ", beforePosition = " + beforePosition);

        // step1
        ChannelTextView view = null;
        try {
            int index = highlightPosition < 0 ? 0 : highlightPosition;
            view = (ChannelTextView) getChildAt(index);
        } catch (Exception e) {
        }

        // step2
        if (null != view) {
            if (highlightPosition == selectPosition) {
                view.setTextColorPlaying();
                view.setCompoundDrawables(true);
            } else if (direction == Channeldirection.LEFT && selectPosition != beforePosition) {
                view.setTextColorDefault();
                view.setCompoundDrawables(false);
            } else {
                view.setTextColorSelect();
                view.setCompoundDrawables(false);
            }
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

    private final void addItem(@NonNull int index, @NonNull ChannelModel model) {

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

        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        int beforePosition = getBeforePosition();
        ChannelUtil.logE("addItem => index = " + index + ", selectPosition = " + selectPosition + ", highlightPosition = " + highlightPosition + ", beforePosition = " + beforePosition);
        if (selectPosition >= 0 && selectPosition == index) {
            child.setTextColorPlaying();
            child.setCompoundDrawables(true);
        } else {
            child.setTextColorDefault();
            child.setCompoundDrawables(false);
        }
        child.setBackgroundDefault();
        addView(child, index);
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

    protected final void nextFocus(@Channeldirection.Value int direction, boolean requestFocus) {

        if (direction < Channeldirection.MIN || direction > Channeldirection.MAX)
            return;

        int highlightPosition = getHighlightPosition();
        nextFocus(highlightPosition, direction, requestFocus, true);
    }

    protected final void nextFocus(@NonNull int highlightPosition, @Channeldirection.Value int direction, boolean requestFocus, @NonNull boolean callback) {

        if (direction < Channeldirection.MIN || direction > Channeldirection.MAX)
            return;

        ChannelUtil.logE("nextFocus => ****************************");

        if (direction == Channeldirection.INIT && highlightPosition == 0) {
            setBeforePosition(highlightPosition);
            setSelectPosition(highlightPosition, true);
            setHighlightPosition(highlightPosition, true);
        }

        int nextPosition;
        // down
        if (direction == Channeldirection.DOWN) {
            nextPosition = (highlightPosition < 0 ? 0 : highlightPosition) + 1;
        }
        // nextDown
        else if (direction == Channeldirection.NEXT_DOWN) {
            nextPosition = (highlightPosition < 0 ? 0 : highlightPosition) + 1;
        }
        // up
        else if (direction == Channeldirection.UP) {
            nextPosition = (highlightPosition < 0 ? 0 : highlightPosition) - 1;
        }
        // nextUp
        else if (direction == Channeldirection.NEXT_UP) {
            nextPosition = (highlightPosition < 0 ? 0 : highlightPosition) - 1;
        }
        // init
        else {
            nextPosition = highlightPosition < 0 ? 0 : highlightPosition;
        }

        int count = getChildCount();
        int selectPosition = getSelectPosition();
        ChannelUtil.logE("nextFocusTT => highlightPosition = " + highlightPosition + ", selectPosition = " + selectPosition + ", nextPosition = " + nextPosition + ", count = " + count);
        if (nextPosition < 0 || nextPosition >= count)
            return;

        ChannelTextView before = null;
        try {
            if (highlightPosition >= 0 && nextPosition != highlightPosition) {
                before = (ChannelTextView) getChildAt(highlightPosition);
            }
        } catch (Exception e) {
        }
        ChannelTextView next = null;
        try {
            next = (ChannelTextView) getChildAt(nextPosition);
        } catch (Exception e) {
        }

        // setHighlightPosition1
        if (direction == Channeldirection.UP || direction == Channeldirection.DOWN) {
            setHighlightPosition(nextPosition, false);
        }
        // setHighlightPosition2
        else if (nextPosition > 0 && direction == Channeldirection.INIT) {
            setHighlightPosition(nextPosition, false);
        }
        // setHighlightPosition3
        else if (highlightPosition < 0 && direction == Channeldirection.LEFT) {
            setHighlightPosition(nextPosition, false);
        }
        // setHighlightPosition4
        else if (highlightPosition < 0 && direction == Channeldirection.RIGHT) {
            setHighlightPosition(nextPosition, false);
        }

        // setSelectPosition1
        if (direction == Channeldirection.NEXT_UP || direction == Channeldirection.NEXT_DOWN) {
            setBeforePosition(nextPosition);
            setSelectPosition(nextPosition, false);
        }
        // setSelectPosition2
        else if (nextPosition > 0 && direction == Channeldirection.INIT) {
            setBeforePosition(nextPosition);
            setSelectPosition(nextPosition, false);
        }

        try {
            ChannelUtil.logE("nextFocusTT => before = " + before.getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocusTT => before = null");
        }
        try {
            ChannelUtil.logE("nextFocusTT => next = " + next.getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocusTT => next = null, message = " + e.getMessage(), e);
        }

        // before
        if (null != before && direction == Channeldirection.NEXT_DOWN) {
            before.setTextColorDefault();
            before.setCompoundDrawables(false);
            before.setBackgroundDefault();
        }
        // before
        else if (null != before && direction == Channeldirection.NEXT_UP) {
            before.setTextColorDefault();
            before.setCompoundDrawables(false);
            before.setBackgroundDefault();
        }
        // before
        else if (null != before) {
            if (highlightPosition >= 0 && selectPosition >= 0 && highlightPosition == selectPosition) {
                before.setTextColorPlaying();
                before.setCompoundDrawables(true);
            } else {
                before.setTextColorDefault();
                before.setCompoundDrawables(false);
            }
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
                next.setCompoundDrawables(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // next-up next-down
            else if (direction == Channeldirection.NEXT_UP || direction == Channeldirection.NEXT_DOWN) {
                next.setCompoundDrawables(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // left right
            else if (direction == Channeldirection.LEFT || direction == Channeldirection.RIGHT) {
                next.setCompoundDrawables(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // select
            else if (direction == Channeldirection.CLICK) {
                next.setCompoundDrawables(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // init1
            else if (requestFocus && direction == Channeldirection.INIT) {
                next.setCompoundDrawables(false);
                next.setTextColorHighlight();
                next.setBackgroundHighlight();
            }
            // init2
            else if (direction == Channeldirection.INIT) {
                next.setCompoundDrawables(true);
                next.setTextColorPlaying();
                next.setBackgroundDefault();
            }

            // requestFocus
            if (requestFocus) {
                requestFocus();
            }
            // clearFocus
            else {
                clearFocus();
            }
        }

        // callback
        if (callback && null != next) {
//            // callback
//            if (direction == Channeldirection.INIT && !requestFocus) {
//            }
//            // callback
//            else {
                callback(selectPosition, nextPosition, direction);
//            }
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

    protected final void refresh() {
        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        if (selectPosition == highlightPosition)
            return;

        setBeforePosition(highlightPosition);
        setSelectPosition(highlightPosition, true);

        if (selectPosition != highlightPosition) {
            try {
                ChannelTextView textView = (ChannelTextView) getChildAt(selectPosition);
                textView.setTextColorDefault();
                textView.setCompoundDrawables(false);
                textView.setBackgroundDefault();
            } catch (Exception e) {
            }
        }
        try {
            ChannelTextView textView = (ChannelTextView) getChildAt(highlightPosition);
            if (hasFocus()) {
                textView.setTextColorHighlight();
                textView.setCompoundDrawables(true);
                textView.setBackgroundHighlight();
            } else {
                textView.setTextColorPlaying();
                textView.setCompoundDrawables(true);
                textView.setBackgroundDefault();
            }
        } catch (Exception e) {
        }

        callback(selectPosition, highlightPosition, Channeldirection.INIT);
    }

//    protected final void focusAuto() {
//        int selectPosition = getSelectPosition();
//        int highlightPosition = getHighlightPosition();
//        setHighlightPosition(selectPosition, false);

//        updateHighlightPosition();

//        if (selectPosition != highlightPosition) {
//            try {
//                ChannelTextView textView = (ChannelTextView) getChildAt(highlightPosition);
//                textView.setTextColorDefault();
//                textView.setCompoundDrawables(false);
//                textView.setBackgroundDefault();
//            } catch (Exception e) {
//            }
//        }
//        try {
//            ChannelTextView textView = (ChannelTextView) getChildAt(selectPosition);
//            if (hasFocus()) {
//                textView.setTextColorHighlight();
//                textView.setCompoundDrawables(true);
//                textView.setBackgroundHighlight();
//            } else {
//                textView.setTextColorSelect();
//                textView.setCompoundDrawables(true);
//                textView.setBackgroundDefault();
//            }
//        } catch (Exception e) {
//        }
//    }

    private final List<ChannelModel> getTags(boolean click) {

        // 新
        if (click) {
            ArrayList<ChannelModel> list = new ArrayList<>();
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                try {
                    ChannelTextView textView = (ChannelTextView) getChildAt(i);
                    ChannelModel model = (ChannelModel) textView.getTag(R.id.module_channel_tag_item);
                    list.add(model);
                } catch (Exception e) {
                }
            }
            setTag(R.id.module_channel_tag_all, list);
            return list;
        }

        try {
            List<ChannelModel> tags = (List<ChannelModel>) getTag(R.id.module_channel_tag_all);
            return tags;
        } catch (Exception e) {
            return null;
        }
    }

    protected final void updateTags(@NonNull List<ChannelModel> list) {
        ChannelUtil.logE("updateTags => list = " + list);
        setTag(R.id.module_channel_tag_all, list);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        List<ChannelModel> list = getTags(false);
        ChannelUtil.logE("onVisibilityChanged => visibility = " + visibility + ", list = " + list);
        if (null == list || list.size() <= 0)
            return;

        // show
        if (hasFocus() && visibility == View.VISIBLE) {
            try {
                int selectPosition = getSelectPosition();
                ChannelTextView child = (ChannelTextView) getChildAt(selectPosition);
                child.setTextColorHighlight();
                child.setCompoundDrawables(false);
                child.setBackgroundHighlight();
            } catch (Exception e) {
            }
        }
        // gone
        else if (visibility != View.VISIBLE) {
            int beforePosition = getBeforePosition();
            ChannelUtil.logE("onVisibilityChanged => beforePosition = " + beforePosition);
            setHighlightPosition(beforePosition, true);
            setSelectPosition(beforePosition, true);
            update(list, false);
        }
    }

    protected final void update(@NonNull List<ChannelModel> list, boolean refresh) {

        if (null == list || list.size() == 0)
            return;

        ChannelUtil.logE("updateTT => **********************");
        int count = getChildCount();
        ChannelUtil.logE("updateTT => refresh = " + refresh + ", count = " + count);

        // step0 - tag all
        List<ChannelModel> tags = getTags(false);
        updateTags(null != tags ? tags : list);

        // step1
        if (refresh) {
            resetSelectPosition();
            resetHighlightPosition();
        }

        // step3
        if (refresh) {
            ChannelScrollView scrollView = (ChannelScrollView) getParent();
            ChannelLayout channelLayout = (ChannelLayout) scrollView.getParent();
            int column = channelLayout.indexOfChild(scrollView);
            ChannelUtil.logE("updateTT => column = " + column);
            if (column > 0) {
                ChannelScrollView layoutScroll = (ChannelScrollView) channelLayout.getChildAt(column - 1);
                ChannelLinearLayoutChild layoutChild = (ChannelLinearLayoutChild) layoutScroll.getChildAt(0);
                int selectPosition = layoutChild.getSelectPosition();
                int highlightPosition = layoutChild.getHighlightPosition();
                int beforePosition = getBeforePosition();
                ChannelUtil.logE("updateTT => selectPosition = " + selectPosition + ", highlightPosition = " + highlightPosition + ", beforePosition = " + beforePosition);
                if (beforePosition >= 0 && selectPosition >= 0 && highlightPosition >= 0 && selectPosition == highlightPosition) {
                    ChannelUtil.logE("updateTT => selectPosition1 = " + selectPosition + ", highlightPosition1 = " + highlightPosition + ", beforePosition1 = " + beforePosition);
                    int size = list.size();
                    setSelectPosition(size, beforePosition, false);
                    setHighlightPosition(size, beforePosition, false);
                }
            }
        }

        int selectPosition = getSelectPosition();
        int highlightPosition = getHighlightPosition();
        int beforePosition = getBeforePosition();
        ChannelUtil.logE("updateTT => selectPosition2 = " + selectPosition + ", highlightPosition2 = " + highlightPosition + ", beforePosition2 = " + beforePosition);
        ChannelUtil.logE("updateTT => **********************");

        // step4
        removeAllViews();
        int size = list.size();
        for (int i = 0; i < size; i++) {

            ChannelModel temp = list.get(i);
            CharSequence initText = temp.initText();
            if (null == initText || initText.length() == 0)
                continue;

//            ChannelUtil.logE("update => i = " + i + " initText = " + initText);
            addItem(i, temp);
        }
    }

    /*************/

    protected final void callback(@NonNull int beforePosition, @NonNull int nextPosition, @Channeldirection.Value int direction) {

        ChannelUtil.logE("callback33 => beforePosition = " + beforePosition + ", nextPosition = " + nextPosition + ", direction = " + direction);
        if (nextPosition < 0)
            return;

        int count = getChildCount();
        ChannelUtil.logE("callback33 => count = " + count);
        if (nextPosition + 1 > count) {
            return;
        }

        ChannelModel value;
        try {
            View child = getChildAt(nextPosition);
            value = (ChannelModel) child.getTag(R.id.module_channel_tag_item);
        } catch (Exception e) {
            value = null;
        }

        if (null == getParent() || !(getParent() instanceof ChannelScrollView))
            return;
        ((ChannelScrollView) getParent()).callback(beforePosition, nextPosition, count, direction, value);
    }

    /**************/

    private final int getBeforePosition() {
        try {
            return (int) getTag(R.id.module_channel_position_before);
        } catch (Exception e) {
            return -1;
        }
    }

    private final void setBeforePosition(@NonNull int index) {
        if (index < 0)
            return;
        int count = getChildCount();
        if (index >= count)
            return;
        ChannelUtil.logE("setBeforePosition => index = " + index + ", count = " + getChildCount());
        setTag(R.id.module_channel_position_before, index);
    }

    protected final int getHighlightPosition() {
        try {
            return (int) getTag(R.id.module_channel_position_highlight);
        } catch (Exception e) {
            return -1;
        }
    }

    private final void resetHighlightPosition() {
        setTag(R.id.module_channel_position_highlight, -1);
    }

    protected final void setHighlightPosition(@NonNull int index, @NonNull boolean update) {
        setHighlightPosition(-1, index, update);
    }

    private final void setHighlightPosition(@NonNull int size, @NonNull int index, @NonNull boolean update) {
        if (index < 0)
            return;
        int count = getChildCount();
        if (size > 0) {
            count = Math.max(count, size);
        }
        if (index >= count)
            return;

        try {
            if (update) {
                int highlightPosition = getHighlightPosition();
                if (highlightPosition >= 0 && highlightPosition != index) {
                    ChannelTextView textView = (ChannelTextView) getChildAt(highlightPosition);
                    textView.setBackgroundDefault();
                    textView.setCompoundDrawables(false);
                    textView.setBackgroundDefault();
                }
            }
        } catch (Exception e) {
        }

        ChannelUtil.logE("setHighlightPosition => index = " + index + ", update = " + update + ", count = " + getChildCount());
        setTag(R.id.module_channel_position_highlight, index);
    }

    protected final int getSelectPosition() {
        try {
            return (int) getTag(R.id.module_channel_position_select);
        } catch (Exception e) {
            return -1;
        }
    }

    private final void resetSelectPosition() {
        setTag(R.id.module_channel_position_select, -1);
    }

    private final void setSelectPosition(@NonNull int index, @NonNull boolean update) {
        setSelectPosition(-1, index, update);
    }

    private final void setSelectPosition(@NonNull int size, @NonNull int index, @NonNull boolean update) {
        if (index < 0)
            return;
        int count = getChildCount();
        if (size > 0) {
            count = Math.max(count, size);
        }
        if (index >= count)
            return;

        try {
            if (update) {
                int selectPosition = getSelectPosition();
                if (selectPosition >= 0 && selectPosition != index) {
                    ChannelTextView textView = (ChannelTextView) getChildAt(selectPosition);
                    textView.setTextColorDefault();
                    textView.setCompoundDrawables(false);
                    textView.setBackgroundDefault();
                }
            }
        } catch (Exception e) {
        }

        ChannelUtil.logE("setSelectPosition => index = " + index + ", update = " + update + ", count = " + getChildCount());
        setTag(R.id.module_channel_position_select, index);
    }

    /**************/
}
