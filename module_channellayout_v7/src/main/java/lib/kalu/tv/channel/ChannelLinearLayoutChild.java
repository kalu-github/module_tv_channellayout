package lib.kalu.tv.channel;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        if (event.getRepeatCount() > 0) {
            return true;
        }
        // up come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            View focus = findFocus();
            ChannelUtil.logE("dispatchKeyEvent[up come] => focus = " + focus);
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root) {
                searchFocus();
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root) {
                searchFocus();
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root) {
                searchFocus();
                return true;
            }
        }
        // down come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root) {
                ChannelUtil.logE("dispatchKeyEvent[down come item0] => focus = " + focus);
                searchFocus();
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root) {
                ChannelUtil.logE("dispatchKeyEvent[down come item1] => focus = " + focus);
                searchFocus();
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root) {
                ChannelUtil.logE("dispatchKeyEvent[down come item2] => focus = " + focus);
                searchFocus();
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[down exception] => focus = " + focus);
                return true;
            }
        }
        // right come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root) {
                ChannelUtil.logE("dispatchKeyEvent[right come item0] => focus = " + focus);
                searchFocus();
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root) {
                ChannelUtil.logE("dispatchKeyEvent[right come item1] => focus = " + focus);
                searchFocus();
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root) {
                ChannelUtil.logE("dispatchKeyEvent[right come item2] => focus = " + focus);
//                setVisibility(View.VISIBLE);
                searchFocus();
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[right exception] => focus = " + focus);
                return true;
            }
        }
        // left come
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root) {
                ChannelUtil.logE("dispatchKeyEvent[left come item0] => focus = " + focus);
                searchFocus();
                callback((ChannelTextView) findFocus(), View.FOCUS_LEFT);
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root) {
                ChannelUtil.logE("dispatchKeyEvent[left come item1] => focus = " + focus);
                searchFocus();
                callback((ChannelTextView) findFocus(), View.FOCUS_LEFT);
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root) {
                ChannelUtil.logE("dispatchKeyEvent[left come item2] => focus = " + focus);
                searchFocus();
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[left come exception] => focus = " + focus);
            }
        }
        // down move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[down move item0] => focus = " + focus);
                int index = indexOfChild(focus);
                nextFocus(index, View.FOCUS_DOWN);
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[down move item1] => focus = " + focus);
                int index = indexOfChild(focus);
                nextFocus(index, View.FOCUS_DOWN);
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[down move item2] => focus = " + focus);
                int index = indexOfChild(focus);
                nextFocus(index, View.FOCUS_DOWN);
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[down exception] => focus = " + focus);
                return true;
            }
        }
        // up move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[up move] => focus = " + focus);
                int index = indexOfChild(focus);
                nextFocus(index, View.FOCUS_UP);
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[up move] => focus = " + focus);
                int index = indexOfChild(focus);
                nextFocus(index, View.FOCUS_UP);
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[up move] => focus = " + focus);
                int index = indexOfChild(focus);
                nextFocus(index, View.FOCUS_UP);
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[up exception] => focus = " + focus);
                return true;
            }
        }
        // right leave
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[right leave item0] => focus = " + focus);
                ((ChannelTextView) focus).hightlight();
                callback((ChannelTextView) focus, View.FOCUS_RIGHT);
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[right leave item1] => focus = " + focus);
                ((ChannelTextView) focus).hightlight();
                callback((ChannelTextView) focus, View.FOCUS_RIGHT);
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[right leave item2] => focus = " + focus);
//                ((ChannelTextView) focus).hightlight();
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[right leave exception] => focus = " + focus);
                return true;
            }
        }
        // left leave
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            // item0
            if (null != focus && focus.getId() == R.id.module_channellayout_id_item0_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[left leave item0] => focus = " + focus);
                ((ChannelTextView) focus).hightlight();
                return true;
            }
            // item1
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item1_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[left leave item1] => focus = " + focus);
                callback((ChannelTextView) focus, View.FOCUS_LEFT);
                ((ChannelTextView) focus).hightlight();
                return true;
            }
            // item2
            else if (null != focus && focus.getId() == R.id.module_channellayout_id_item2_root_child) {
                ChannelUtil.logE("dispatchKeyEvent[left leave item2] => focus = " + focus);
                callback((ChannelTextView) focus, View.FOCUS_LEFT);
                return true;
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[left leave exception] => focus = " + focus);
                return true;
            }
        }
        // else
        else {
            View focus = findFocus();
            ChannelUtil.logE("dispatchKeyEvent[null] => focus = " + focus + ", action = " + event.getAction() + ", code = " + event.getKeyCode());
        }
        return super.dispatchKeyEvent(event);
    }

    private final void init() {
        setOrientation(LinearLayout.VERTICAL);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    private final void searchFocus() {
        searchFocus(-1, true);
    }

    private final void searchFocus(int index, boolean focus) {
        int before = 0;
        if (index < 0) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View temp = getChildAt(i);
                if (null == temp)
                    continue;
                if (((ChannelTextView) temp).isHightlight()) {
                    before = i;
                    break;
                }
            }
        } else {
            before = index;
        }
        ChannelUtil.logE("searchFocus => before = " + before);
        nextFocus(before, Integer.MIN_VALUE, focus);
    }

    private final void nextFocus(@NonNull int before, @NonNull int direction) {
        nextFocus(before, direction, true);
    }

    /**
     * FOCUS_BLOCK_DESCENDANTS: 拦截焦点, 直接自己尝试获取焦点
     * FOCUS_BEFORE_DESCENDANTS: 首先自己尝试获取焦点, 如果自己不能获取焦点, 则尝试让子控件获取焦点
     * FOCUS_AFTER_DESCENDANTS: 首先尝试把焦点给子控件, 如果所有子控件都不要, 则自己尝试获取焦点
     */
    private final void nextFocus(@NonNull int before, @NonNull int direction, boolean focus) {

        if (direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != Integer.MIN_VALUE)
            return;

        int next;

        // down
        if (direction == View.FOCUS_DOWN) {
            next = before + 1;
        }
        // up
        else if (direction == View.FOCUS_UP) {
            next = before - 1;
        }
        // init
        else {
            next = before;
        }

        int count = getChildCount();
        ChannelUtil.logE("nextFocus => before = " + before + ", next = " + next + ", count = " + count + ", direction = " + direction);
        if (next < 0 || next >= count)
            return;

        View viewBefore = null;
        try {
            if (before >= 0 && before <= count) {
                viewBefore = getChildAt(before);
                ChannelUtil.logE("nextFocus => viewBefore = " + viewBefore + ", text = " + ((TextView) viewBefore).getText());
            } else {
                ChannelUtil.logE("nextFocus => viewBefore = null");
            }
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus => viewBefore = null");
        }
        View viewNext = null;
        try {
            viewNext = getChildAt(next);
            ChannelUtil.logE("nextFocus => viewNext = " + viewNext + ", text = " + ((TextView) viewNext).getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus => viewNext = null");
        }

        setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        if (null != viewBefore) {
            ((ChannelTextView) viewNext).hightlight(false);
        }
        if (null != viewNext) {
            if (focus) {
                viewNext.requestFocus();
            } else {
                ((ChannelTextView) viewNext).hightlight();
            }
        }
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    /*************/

    protected final void startFocus(@NonNull int index) {
        searchFocus(index, false);
    }

    protected final void startFocus(@NonNull int index, boolean focus) {
        searchFocus(index, focus);
    }

    /*************/

    protected final void refresh(int visibility) {

        // step1
        setFocusable(visibility == View.VISIBLE);
        setFocusableInTouchMode(visibility == View.VISIBLE);

        // step2
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (null == child)
                continue;
            child.setFocusable(visibility == View.VISIBLE);
            child.setFocusableInTouchMode(visibility == View.VISIBLE);
        }

        // step3
        searchFocus();
    }

    protected final void update(@NonNull int visibility, @IntRange(from = 0, to = 2) int index, @NonNull List<ChannelModel> list) {

        // focus
        setFocusable(visibility == View.VISIBLE);
        setFocusableInTouchMode(visibility == View.VISIBLE);

        if (null == list || list.size() == 0)
            return;

        @IdRes int id;
        if (index == 0) {
            id = R.id.module_channellayout_id_item0_root_child;
        } else if (index == 1) {
            id = R.id.module_channellayout_id_item1_root_child;
        } else {
            id = R.id.module_channellayout_id_item2_root_child;
        }

        ChannelUtil.logE("**********************");

        int count = getChildCount();
        int size = list.size();
        ChannelUtil.logE("update => count = " + count + ", size = " + size);

        for (int i = 0; i < size; i++) {

            ChannelUtil.logE("update => i = " + i);
            ChannelModel temp = list.get(i);
            CharSequence initText = temp.initText();
            if (null == initText || initText.length() == 0)
                continue;

            // add
            if (null == getChildAt(i)) {
                ChannelUtil.logE("update[new] => initText = " + initText);
                ChannelTextView child = new ChannelTextView(getContext());
                child.setId(id);
                int width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_height);
                child.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width));
                int offset = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_size);
                child.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset);
                int left = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_left);
                int right = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_right);
                child.setPadding(left, 0, right, 0);
                child.setSelected(false);
                child.setText(initText, temp.initSelect());
                // focus
                child.setFocusable(visibility == View.VISIBLE);
                child.setFocusableInTouchMode(visibility == View.VISIBLE);
                // add
                addView(child, i);
            }
            // reset
            else {
                ChannelUtil.logE("update[old] => initText = " + initText);
                View child = getChildAt(i);
                // focus
                child.setFocusable(visibility == View.VISIBLE);
                child.setFocusableInTouchMode(visibility == View.VISIBLE);
                // setText
                ((ChannelTextView) child).setText(initText, temp.initSelect(), true);
            }
        }
        ChannelUtil.logE("**********************");
    }

    /*************/

    protected final void callback(@NonNull ChannelTextView view, int direction) {

        int position = indexOfChild(view);
        ChannelUtil.logE("callback33 => position = " + position);
        if (position < 0) {
            return;
        }

        int count = getChildCount();
        ChannelUtil.logE("callback33 => count = " + count);
        if (position + 1 > count) {
            return;
        }

        ChannelUtil.logE("callback33 => text = " + view.getText());
        try {
            ((ChannelScrollView) getParent()).callback(position, direction);
        } catch (Exception e) {
        }
    }
}
