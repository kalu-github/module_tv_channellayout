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
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
                nextFocus(View.FOCUS_DOWN);
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
                nextFocus(View.FOCUS_UP);
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
                        keepSelect(View.FOCUS_RIGHT);
                        layoutNext.requestFocus();
                        layoutNext.callback(Integer.MAX_VALUE, View.FOCUS_RIGHT);
                    }
                    // 正常显示
                    else {
                        ChannelUtil.logE("dispatchKeyEvent[right move] => next");
                        keepSelect(View.FOCUS_RIGHT);
                        layoutNext.requestFocus();
                        layoutNext.nextFocus(View.FOCUS_RIGHT);
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
                        keepSelect(View.FOCUS_LEFT);
                    }
                    layoutNext.requestFocus();
                    layoutNext.nextFocus(View.FOCUS_LEFT);
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
                int position = findClick();
                if (position != -1) {
                    callback(position, Integer.MAX_VALUE);
                    ChannelUtil.logE("dispatchKeyEvent[enter click] => position = " + position);
                } else {
                    ChannelUtil.logE("dispatchKeyEvent[enter click fail] => position = " + position);
                }
                return true;
            }
        }
        // click2
        else if (showing && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int position = findClick();
                if (position != -1) {
                    callback(position, Integer.MAX_VALUE);
                    ChannelUtil.logE("dispatchKeyEvent[center click] => position = " + position);
                } else {
                    ChannelUtil.logE("dispatchKeyEvent[center click fail] => position = " + position);
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
        child.setTag(R.id.module_channel_item_tag, model);
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

    private final int findClick() {
        int num = 0;
        int position = -1;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View temp = getChildAt(i);
            if (null == temp || !(temp instanceof ChannelTextView))
                continue;

            boolean selected = temp.isSelected();
            boolean clickable = temp.isClickable();

            // click
            if (selected) {
                if (clickable) {
                    ((ChannelTextView) temp).select();
                    position = i;
                }
                num++;
                continue;
            }
            // clickable
            else if (!clickable) {
                ((ChannelTextView) temp).reset(false);
                num++;
                continue;
            }

            if (num >= 2)
                break;
        }
        return position;
    }

    private final int findColumn() {
        int column;
        try {
            column = ((ViewGroup) getParent().getParent()).indexOfChild((View) getParent());
        } catch (Exception e) {
            column = -1;
        }

        ChannelUtil.logE("findColumn => column = " + column);
        return column;
    }

    private final int findFocusPosition(@NonNull int column) {
        int position = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View temp = getChildAt(i);
            if (null == temp)
                continue;

            boolean selected = temp.isSelected();
            if (selected) {
                position = i;
                break;
            }
        }

        ChannelUtil.logE("findFocusPosition => column = " + column + ", position = " + position);
        return position;
    }

    private final int findSelectPosition(@NonNull int column) {
        int position = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View temp = getChildAt(i);
            if (null == temp)
                continue;

            boolean clickable = temp.isClickable();
            if (!clickable) {
                position = i;
                break;
            }
        }

        ChannelUtil.logE("findSelectPosition => column = " + column + ", position = " + position);
        return position;
    }

    private final void keepSelect(@NonNull int direction) {

        int column = findColumn();
        int focusPosition = findFocusPosition(column);
        int selectPosition = findSelectPosition(column);

        View viewFocus = null;
        try {
            viewFocus = getChildAt(focusPosition);
        } catch (Exception e) {
        }

        View viewSelect = null;
        try {
            viewSelect = getChildAt(selectPosition);
        } catch (Exception e) {
        }

        String tag = (direction == View.FOCUS_LEFT ? "left" : "right");
        ChannelUtil.logE("keepSelect[" + tag + "] => column = " + column + ", focusPosition = " + focusPosition + ", selectPosition = " + selectPosition + ", viewFocus = " + viewFocus + ", viewSelect = " + viewSelect);

        if (null != viewFocus) {
            if (column <= 0) {
                ((ChannelTextView) viewFocus).select(true);
            } else {
                ((ChannelTextView) viewFocus).reset(false);
            }
        }

        if (null != viewSelect) {
            if (column <= 0) {
                ((ChannelTextView) viewSelect).select(true);
            } else {
                ((ChannelTextView) viewSelect).highlight(true);
            }
        }

        clearFocus();
    }

    private final void nextFocus(@NonNull int direction) {

        if (direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT && direction != Integer.MIN_VALUE && direction != Integer.MAX_VALUE)
            return;

        int column = findColumn();
        if (column < 0)
            return;

        int focusPosition = findFocusPosition(column);
        if (focusPosition < 0)
            return;

        nextFocus(column, focusPosition, direction, false);
    }

    private final void nextFocus(@NonNull int column, @NonNull int focusPosition, @NonNull int direction, boolean select) {

        if (direction != 1111 && direction != 2222 && direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT && direction != Integer.MIN_VALUE && direction != Integer.MAX_VALUE)
            return;

        ChannelUtil.logE("nextFocus => ****************************");
        int nextPosition;

        // down
        if (direction == View.FOCUS_DOWN) {
            nextPosition = focusPosition + 1;
        }
        // nextDown
        else if (direction == 2222) {
            nextPosition = focusPosition + 1;
        }
        // up
        else if (direction == View.FOCUS_UP) {
            nextPosition = focusPosition - 1;
        }
        // nextUp
        else if (direction == 1111) {
            nextPosition = focusPosition - 1;
        }
        // init
        else {
            nextPosition = focusPosition;
        }

        int count = getChildCount();
        ChannelUtil.logE("nextFocus => column = " + column + ", focusPosition = " + focusPosition + ", nextPosition = " + nextPosition + ", count = " + count + ", direction = " + direction + ", select = " + select);
        if (nextPosition < 0 || nextPosition >= count)
            return;

        View before = null;
        try {
            before = getChildAt(focusPosition);
        } catch (Exception e) {
        }
        View next = null;
        try {
            next = getChildAt(nextPosition);
        } catch (Exception e) {
        }
        if (null != before && null != next && before == next) {
            before = null;
        }

        try {
            ChannelUtil.logE("nextFocus => before = " + ((TextView) before).getText() + ", clickable = " + before.isClickable());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus => before = null");
        }
        try {
            ChannelUtil.logE("nextFocus => next = " + ((TextView) next).getText() + ", clickable = " + next.isClickable());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus => next = null, message = " + e.getMessage(), e);
        }

        // before
        if (null != before) {

            boolean clickable = before.isClickable();
            // left
            if (!clickable && direction == View.FOCUS_LEFT) {
                ((ChannelTextView) before).highlight(true);
            }
            // right
            else if (!clickable && direction == View.FOCUS_RIGHT) {
                ((ChannelTextView) before).highlight(true);
            }
            // down
            else if (!clickable && direction == View.FOCUS_DOWN) {
                ((ChannelTextView) before).selectKeep();
            }
            // up
            else if (!clickable && direction == View.FOCUS_UP) {
                ((ChannelTextView) before).selectKeep();
            }
            // reset
            else {
                ((ChannelTextView) before).reset(false);
            }
        }

        // next
        if (null != next) {

            // scroll => init
            if (direction == Integer.MIN_VALUE) {
                scrolInit(next);
            }
            // scroll => down
            else if (direction == View.FOCUS_DOWN) {
                scrolDown(next);
            }
            // scroll => up
            else if (direction == View.FOCUS_UP) {
                scrolUp(next);
            }

            // select
            if (select) {
                ((ChannelTextView) next).select();
            }
            // init
            else if (direction == Integer.MIN_VALUE) {
                ((ChannelTextView) next).highlight(true);
            }
            // default
            else {
                ((ChannelTextView) next).focus();
            }

            // callback
            callback(nextPosition, direction);
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

    protected final void select(@NonNull int column, @NonNull int position) {
        select(column, position, false);
    }

    protected final void select(@NonNull int column, @NonNull int position, @NonNull boolean select) {
        int count = getChildCount();
        if (position + 1 > count)
            return;

        if (select) {
            requestFocus();
        }
        nextFocus(column, position, Integer.MIN_VALUE, select);
    }

    protected final void nextUp(@NonNull int column, @NonNull int position) {
        int count = getChildCount();
        if (position + 1 > count)
            return;

        nextFocus(column, position, 1111, true);
    }

    protected final void nextDown(@NonNull int column, @NonNull int position) {
        int count = getChildCount();
        if (position + 1 > count)
            return;

        nextFocus(column, position, 2222, true);
    }

    /*************/

    protected final void update(@NonNull List<ChannelModel> list) {

        ChannelUtil.logE("**********************");

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

    protected final void callback(int position, int direction) {

        ChannelUtil.logE("callback33 => position = " + position);
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
            value = (ChannelModel) child.getTag(R.id.module_channel_item_tag);
        } catch (Exception e) {
            value = null;
        }

        if (null == getParent() || !(getParent() instanceof ChannelScrollView))
            return;
        ((ChannelScrollView) getParent()).callback(position, direction, value);
    }
}
