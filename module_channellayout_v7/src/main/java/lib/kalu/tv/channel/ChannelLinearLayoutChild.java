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
//        // up come
//        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
//                if (null != focus.getParent() && focus.getParent() instanceof ChannelScrollView) {
//                    if (null != focus.getParent().getParent() && focus.getParent().getParent() instanceof ChannelLayout) {
//                        int column = ((ChannelLayout) focus.getParent().getParent()).indexOfChild((View) focus.getParent());
//                        ChannelUtil.logE("dispatchKeyEvent[up come item" + column + "] => column = " + column + ", focus = " + focus);
//                    }
//                }
//                searchFocus();
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[up come intercept] => focus = " + focus);
//            }
//            return true;
//        }
//        // down come
//        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
//                if (null != focus.getParent() && focus.getParent() instanceof ChannelScrollView) {
//                    if (null != focus.getParent().getParent() && focus.getParent().getParent() instanceof ChannelLayout) {
//                        int column = ((ChannelLayout) focus.getParent().getParent()).indexOfChild((View) focus.getParent());
//                        ChannelUtil.logE("dispatchKeyEvent[up come item" + column + "] => column = " + column + ", focus = " + focus);
//                    }
//                }
//                searchFocus();
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[down come intercept] => focus = " + focus);
//            }
//            return true;
//        }
//        // right come
//        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
//                if (null != focus.getParent() && focus.getParent() instanceof ChannelScrollView) {
//                    if (null != focus.getParent().getParent() && focus.getParent().getParent() instanceof ChannelLayout) {
//                        int column = ((ChannelLayout) focus.getParent().getParent()).indexOfChild((View) focus.getParent());
//                        ChannelUtil.logE("dispatchKeyEvent[right come item" + column + "] => column = " + column + ", focus = " + focus);
//                    }
//                }
//                searchFocus();
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[right come intercept] => focus = " + focus);
//            }
//            return true;
//        }
//        // left come
//        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
//                if (null != focus.getParent() && focus.getParent() instanceof ChannelScrollView) {
//                    if (null != focus.getParent().getParent() && focus.getParent().getParent() instanceof ChannelLayout) {
//                        int column = ((ChannelLayout) focus.getParent().getParent()).indexOfChild((View) focus.getParent());
//                        ChannelUtil.logE("dispatchKeyEvent[left come item" + column + "] => column = " + column + ", focus = " + focus);
//                    }
//                }
//                searchFocus();
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[left come intercept] => focus = " + focus);
//            }
//            return true;
//        }
//        // up move
//        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelTextView) {
//                int index = indexOfChild(focus);
//                int count = getChildCount();
//                if (index + 1 >= count) {
//                    ChannelUtil.logE("dispatchKeyEvent[up move intercept] => focus = " + focus);
//                } else {
//                    ChannelUtil.logE("dispatchKeyEvent[up move] => focus = " + focus);
//                    nextFocus(index, View.FOCUS_UP);
//                }
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[up move intercept] => focus = " + focus);
//            }
//            return true;
//        }
//        // right leave
//        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelTextView) {
//                ((ChannelTextView) focus).select();
//                int index = indexOfChild(focus);
//                callback(index, View.FOCUS_RIGHT);
//                ChannelUtil.logE("dispatchKeyEvent[right leave child" + index + "] => text = " + ((ChannelTextView) focus).getText());
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[right leave intercept] => focus = " + focus);
//            }
//            return true;
//        }
//        // left leave
//        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
//            View focus = findFocus();
//            if (null != focus && focus instanceof ChannelTextView) {
//                ((ChannelTextView) focus).select();
//                int index = indexOfChild(focus);
//                callback(index, View.FOCUS_LEFT);
//                ChannelUtil.logE("dispatchKeyEvent[left leave child" + index + "] => focus = " + focus);
//            }
//            // exception
//            else {
//                ChannelUtil.logE("dispatchKeyEvent[left leave intercept] => focus = " + focus);
//            }
//            return true;
//        }
        // down move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelUtil.logE("dispatchKeyEvent[down move] => focus = " + focus);
                nextFocus(View.FOCUS_DOWN);
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[down move intercept] => focus = " + focus);
            }
            return true;
        }
        // up move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelUtil.logE("dispatchKeyEvent[up move] => focus = " + focus);
                nextFocus(View.FOCUS_UP);
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[up move intercept] => focus = " + focus);
            }
            return true;
        }
        // right move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelScrollView channelScrollView = (ChannelScrollView) getParent();
                ChannelLayout channelLayout = (ChannelLayout) getParent().getParent();
                int count = channelLayout.getChildCount();
                int index = channelLayout.indexOfChild(channelScrollView);
                ChannelUtil.logE("dispatchKeyEvent[right move] => count = " + count + ", index = " + index);
                if (index + 1 < count) {
                    ChannelUtil.logE("dispatchKeyEvent[right move] => focus = " + focus);
                    View child = channelLayout.getChildAt(index + 1);
                    if (null != child && child instanceof ChannelScrollView) {
                        hightlightFocus();
                        ((ChannelLinearLayoutChild) ((ChannelScrollView) child).getChildAt(0)).requestFocus();
                        ((ChannelLinearLayoutChild) ((ChannelScrollView) child).getChildAt(0)).nextFocus(View.FOCUS_RIGHT);
                    } else {
                        ChannelUtil.logE("dispatchKeyEvent[right move error] => focus = " + focus);
                    }
                } else {
                    ChannelUtil.logE("dispatchKeyEvent[right move outside] => focus = " + focus);
                }
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[right move intercept] => focus = " + focus);
            }
            return true;
        }
        // left move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelScrollView channelScrollView = (ChannelScrollView) getParent();
                ChannelLayout channelLayout = (ChannelLayout) getParent().getParent();
                int index = channelLayout.indexOfChild(channelScrollView);
                ChannelUtil.logE("dispatchKeyEvent[left move] => index = " + index);
                if (index >= 1) {
                    ChannelUtil.logE("dispatchKeyEvent[left move] => focus = " + focus);
                    View child = channelLayout.getChildAt(index - 1);
                    if (null != child && child instanceof ChannelScrollView) {
                        hightlightFocus();
                        ((ChannelLinearLayoutChild) ((ChannelScrollView) child).getChildAt(0)).requestFocus();
                        ((ChannelLinearLayoutChild) ((ChannelScrollView) child).getChildAt(0)).nextFocus(View.FOCUS_LEFT);
                    } else {
                        ChannelUtil.logE("dispatchKeyEvent[left move error] => focus = " + focus);
                    }
                } else {
                    ChannelUtil.logE("dispatchKeyEvent[left move outside] => focus = " + focus);
                }
            }
            // exception
            else {
                ChannelUtil.logE("dispatchKeyEvent[left move intercept] => focus = " + focus);
            }
            return true;
        }
        // else
        else {
            View focus = findFocus();
            ChannelUtil.logE("dispatchKeyEvent[null] => focus = " + focus + ", action = " + event.getAction() + ", code = " + event.getKeyCode());
            return super.dispatchKeyEvent(event);
        }
    }


    private final void init() {
        setClickable(true);
        setLongClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOrientation(LinearLayout.VERTICAL);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    private final void hightlightFocus() {

        int before = 0;
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

        View viewBefore = null;
        try {
            viewBefore = getChildAt(before);
            ChannelUtil.logE("hightlightFocus[viewBefore] => text = " + ((TextView) viewBefore).getText());
        } catch (Exception e) {
            ChannelUtil.logE("hightlightFocus[viewBefore] => " + e.getMessage());
        }

        if (null != viewBefore) {
            ChannelUtil.logE("hightlightFocus[viewBefore] => select");
            ((ChannelTextView) viewBefore).select();
        }

        clearFocus();
    }

    private final void nextFocus(@NonNull int direction) {

        if (direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT && direction != Integer.MIN_VALUE)
            return;

        int before = 0;
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
        nextFocus(before, direction, true);
    }

    private final void nextFocus(@NonNull int before, @NonNull int direction, boolean requestFocus) {

        if (direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT && direction != Integer.MIN_VALUE)
            return;

        ChannelUtil.logE("nextFocus => ****************************");
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
            viewBefore = getChildAt(before);
            ChannelUtil.logE("nextFocus[viewBefore] => text = " + ((TextView) viewBefore).getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus[viewBefore] => " + e.getMessage());
        }
        View viewNext = null;
        try {
            viewNext = getChildAt(next);
            ChannelUtil.logE("nextFocus[viewNext] => text = " + ((TextView) viewNext).getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus[viewNext] => " + e.getMessage());
        }

        if (null != viewBefore) {
            ChannelUtil.logE("nextFocus[viewBefore] => reset");
            ((ChannelTextView) viewBefore).reset();
        }
        if (null != viewNext) {
            if (requestFocus) {
                // down
                if (direction == View.FOCUS_DOWN) {
                    int bottom = viewNext.getBottom();
                    int scrollY = ((ViewGroup) getParent()).getScrollY();
                    int measuredHeight = ((ViewGroup) getParent()).getMeasuredHeight() + scrollY;
                    if (bottom > measuredHeight) {
                        ((ChannelScrollView) getParent()).smoothScrollBy(0, Math.abs(bottom - measuredHeight));
                    }
                    ChannelUtil.logE("nextFocus[viewNext][down] => focus");
                }
                // up
                else if (direction == View.FOCUS_UP) {
                    int top = viewNext.getTop();
                    int scrollY = ((ViewGroup) getParent()).getScrollY();
                    if (top < scrollY) {
                        ((ChannelScrollView) getParent()).smoothScrollBy(0, -Math.abs(scrollY - top));
                    }
                    ChannelUtil.logE("nextFocus[viewNext][up] => focus");
                }
                // null
                else {
                    ChannelUtil.logE("nextFocus[viewNext][null] => focus");
                }

                callback(next, direction);
                ((ChannelTextView) viewNext).focus();

            } else {
                ChannelUtil.logE("nextFocus[viewBefore] => select");
                ((ChannelTextView) viewNext).select();
            }
        }
        ChannelUtil.logE("nextFocus => ****************************");
    }

    /*************/

    protected final void select(@NonNull int position) {
        select(position, false);
    }

    protected final void select(@NonNull int position, @NonNull boolean requestFocus) {
        int count = getChildCount();
        if (position + 1 > count)
            return;

        if (requestFocus) {
            requestFocus();
        }
        nextFocus(position, Integer.MIN_VALUE, requestFocus);
    }

    /*************/

    protected final void update(@NonNull List<ChannelModel> list) {

        if (null == list || list.size() == 0) {
            removeAllViews();
            return;
        }

        ChannelUtil.logE("**********************");

        int count = getChildCount();
        int size = list.size();
        int max = Math.max(size, count);
        ChannelUtil.logE("update => count = " + count + ", size = " + size + ", max = " + max);

        // 更新
        for (int i = 0; i < size; i++) {

            ChannelModel temp = list.get(i);
            CharSequence initText = temp.initText();
            if (null == initText || initText.length() == 0)
                continue;

            // add
            if (null == getChildAt(i)) {
                ChannelUtil.logE("update[新增] => i = " + i + " initText = " + initText);
                ChannelTextView child = new ChannelTextView(getContext());
                int width = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_height);
                child.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width));
                int offset = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_size);
                child.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset);
                int left = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_left);
                int right = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_right);
                child.setPadding(left, 0, right, 0);
                child.setText(initText, false);
                // add
                addView(child, i);
            }
            // reset
            else {
                ChannelUtil.logE("update[刷新] => i = " + i + " initText = " + initText);
                View child = getChildAt(i);
                // setText
                ((ChannelTextView) child).setText(initText, ((ChannelTextView) child).isHightlight(), true);
            }
        }

        // 移除
        for (int i = size; i < count; i++) {

            View child = getChildAt(i);
            ChannelUtil.logE("update[删除] => i = " + i + ", child = " + child);
            if (null == child)
                continue;
            if (null != child && child instanceof ChannelTextView) {
                boolean hightlight = ((ChannelTextView) child).isHightlight();
                if (hightlight) {
                    View select = getChildAt(size - 1);
                    if (null != select && select instanceof ChannelTextView) {
                        ((ChannelTextView) select).select();
                        break;
                    }
                }
            }
            ChannelUtil.logE("update[删除] => i = " + i + " initText = " + ((ChannelTextView) child).getText());
        }
        try {
            removeViews(size, count - size);
        } catch (Exception e) {
        }

        if (count == 0) {
            View select = getChildAt(0);
            if (null != select && select instanceof ChannelTextView) {
                ((ChannelTextView) select).select();
            }
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

        if (null == getParent() || !(getParent() instanceof ChannelScrollView))
            return;
        ((ChannelScrollView) getParent()).callback(position, direction);
    }
}
