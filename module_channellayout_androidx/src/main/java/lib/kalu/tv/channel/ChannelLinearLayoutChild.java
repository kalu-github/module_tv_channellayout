package lib.kalu.tv.channel;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
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
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            requestFocus();
        } else {
            clearFocus();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (getVisibility() != View.VISIBLE)
            return super.dispatchKeyEvent(event);

        // repeat
        if (event.getRepeatCount() > 0) {
            return true;
        }
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
                        keepLight(View.FOCUS_RIGHT);
                        (((ChannelScrollView) child).getChildAt(0)).requestFocus();
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
                        keepLight(View.FOCUS_LEFT);
                        (((ChannelScrollView) child).getChildAt(0)).requestFocus();
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
        // click1
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int position = findClick();
                if (position != -1) {
                    callback(position, Integer.MAX_VALUE);
                    ChannelUtil.logE("dispatchKeyEvent[enter click] => position = " + position);
                } else {
                    ChannelUtil.logE("dispatchKeyEvent[enter click fail] => position = " + position);
                }
            } else {
                ChannelUtil.logE("dispatchKeyEvent[enter click intercept] => focus = " + focus);
            }
            return true;
        }
        // click2
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int position = findClick();
                if (position != -1) {
                    callback(position, Integer.MAX_VALUE);
                    ChannelUtil.logE("dispatchKeyEvent[center click] => position = " + position);
                } else {
                    ChannelUtil.logE("dispatchKeyEvent[center click fail] => position = " + position);
                }
            } else {
                ChannelUtil.logE("dispatchKeyEvent[center click intercept] => focus = " + focus);
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
                    ((ChannelTextView) temp).click();
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
            ChannelScrollView scrollView = (ChannelScrollView) getParent();
            ChannelLayout channelLayout = (ChannelLayout) scrollView.getParent();
            column = channelLayout.indexOfChild(scrollView);
        } catch (Exception e) {
            column = 0;
        }

        ChannelUtil.logE("findColumn => column = " + column);
        return column;
    }

    private final int findPosition(@NonNull int column) {
        int position = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View temp = getChildAt(i);
            if (null == temp)
                continue;

            boolean selected = temp.isSelected();
            ChannelUtil.logE("findPosition => selected = " + selected + ", text = " + ((ChannelTextView) temp).getText());
            if (selected) {
                position = i;
                break;
            }
        }

        ChannelUtil.logE("findPosition => column = " + column + ", position = " + position);
        return position;
    }

    private final void keepLight(@NonNull int direction) {

        int column = findColumn();
        int position = findPosition(column);

        // left
        if (direction == View.FOCUS_LEFT) {
            ChannelUtil.logE("keepLight[left] => column = " + column + ", position = " + position);
        }
        // right
        else {
            ChannelUtil.logE("keepLight[right] => column = " + column + ", position = " + position);
        }

        View view = null;
        try {
            view = getChildAt(position);
            ChannelUtil.logE("keepLight => text = " + ((TextView) view).getText());
        } catch (Exception e) {
            ChannelUtil.logE("keepLight => " + e.getMessage());
        }

        if (null != view) {
            if (column <= 0) {
                ((ChannelTextView) view).select(true);
            } else {
                boolean selected = view.isSelected();
                boolean clickable = view.isClickable();
                ChannelUtil.logE("keepLight => selected = " + selected + ", clickable = " + clickable);
                if (!clickable && selected) {
                    ((ChannelTextView) view).light(true);
                } else {
                    ((ChannelTextView) view).keep(true);
                }
            }
        }

        clearFocus();
    }

    private final void nextFocus(@NonNull int direction) {

        if (direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT && direction != Integer.MIN_VALUE && direction != Integer.MAX_VALUE)
            return;

        int column = findColumn();
        int position = findPosition(column);
        nextFocus(column, position, direction, true);
    }

    private final void nextFocus(@NonNull int column, @NonNull int position, @NonNull int direction, boolean focus) {

        if (direction != 1111 && direction != 2222 && direction != View.FOCUS_DOWN && direction != View.FOCUS_UP && direction != View.FOCUS_LEFT && direction != View.FOCUS_RIGHT && direction != Integer.MIN_VALUE && direction != Integer.MAX_VALUE)
            return;

        ChannelUtil.logE("nextFocus => ****************************");
        int next;

        // down
        if (direction == View.FOCUS_DOWN) {
            next = position + 1;
        }
        // nextDown
        else if (direction == 2222) {
            next = position + 1;
        }
        // up
        else if (direction == View.FOCUS_UP) {
            next = position - 1;
        }
        // nextUp
        else if (direction == 1111) {
            next = position - 1;
        }
        // init
        else {
            next = position;
        }

        int count = getChildCount();
        ChannelUtil.logE("nextFocus => column = " + column + ", position = " + position + ", next = " + next + ", count = " + count + ", direction = " + direction);
        if (next < 0 || next >= count)
            return;

        View viewBefore = null;
        if (next != position) {
            try {
                viewBefore = getChildAt(position);
            } catch (Exception e) {
            }
        }
        View viewNext = null;
        try {
            viewNext = getChildAt(next);
        } catch (Exception e) {
        }

        try {
            ChannelUtil.logE("nextFocus[viewBefore] => text = " + ((TextView) viewBefore).getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus[viewBefore] => null");
        }
        try {
            ChannelUtil.logE("nextFocus[viewNext] => text = " + ((TextView) viewNext).getText());
        } catch (Exception e) {
            ChannelUtil.logE("nextFocus[viewNext] => " + e.getMessage());
        }

        // viewBefore
        if (null != viewBefore) {

            if (column <= 0) {
                ((ChannelTextView) viewBefore).reset(false);
            } else {
                boolean clickable = viewBefore.isClickable();
                ChannelUtil.logE("nextFocus[viewBefore] => clickable = " + clickable + ", text = " + ((TextView) viewBefore).getText());

                if (direction == 1111 || direction == 2222) {
                    ((ChannelTextView) viewBefore).reset(false);
                } else if (clickable) {
                    ((ChannelTextView) viewBefore).reset(direction != View.FOCUS_DOWN && direction != View.FOCUS_UP);
                } else {
                    ((ChannelTextView) viewBefore).light(false);
                }
            }
        }

        // viewNext
        if (null != viewNext) {

            // scroll => down
            if (direction == View.FOCUS_DOWN) {
                int bottom = viewNext.getBottom();
                int scrollY = ((ViewGroup) getParent()).getScrollY();
                int measuredHeight = ((ViewGroup) getParent()).getMeasuredHeight() + scrollY;
                if (bottom > measuredHeight) {
                    ((ChannelScrollView) getParent()).smoothScrollBy(0, Math.abs(bottom - measuredHeight));
                }
                ChannelUtil.logE("nextFocus[viewNext][down] => focus");
            }
            // scroll => up
            else if (direction == View.FOCUS_UP) {
                int top = viewNext.getTop();
                int scrollY = ((ViewGroup) getParent()).getScrollY();
                if (top < scrollY) {
                    ((ChannelScrollView) getParent()).smoothScrollBy(0, -Math.abs(scrollY - top));
                }
                ChannelUtil.logE("nextFocus[viewNext][up] => focus");
            }
            // scroll => null
            else {
                ChannelUtil.logE("nextFocus[viewNext][null] => focus");
            }

            // column0
            if (column <= 0 && focus) {
                ((ChannelTextView) viewNext).focus();
            }
            // column0
            else if (column <= 0) {
                ((ChannelTextView) viewNext).light(true);
            }
            // init
            else if (direction == Integer.MIN_VALUE && focus) {
                ((ChannelTextView) viewNext).click();
            }
            // nextUp
            else if (direction == 1111 && focus) {
                ((ChannelTextView) viewNext).click();
            }
            // nextDown
            else if (direction == 2222 && focus) {
                ((ChannelTextView) viewNext).click();
            }
            // move
            else if (focus) {
                ((ChannelTextView) viewNext).focus();
            }
            // light
            else {
                ((ChannelTextView) viewNext).light(direction == View.FOCUS_DOWN || direction == View.FOCUS_UP);
            }

            // callback
            if (focus) {
                callback(next, direction);
            }
        }
        ChannelUtil.logE("nextFocus => ****************************");
    }

    /*************/

    protected final void select(@NonNull int column, @NonNull int position) {
        select(column, position, false);
    }

    protected final void select(@NonNull int column, @NonNull int position, @NonNull boolean requestFocus) {
        int count = getChildCount();
        if (position + 1 > count)
            return;

        if (requestFocus) {
            requestFocus();
        }
        nextFocus(column, position, Integer.MIN_VALUE, requestFocus);
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
                child.setText(initText);
                // add
                addView(child, i);
            }
            // reset
            else {
                ChannelUtil.logE("update[刷新] => i = " + i + " initText = " + initText);
                View child = getChildAt(i);
                // setText
                ((ChannelTextView) child).setText(initText);
            }
        }

        // 移除
//        for (int i = size; i < count; i++) {
//
//            View child = getChildAt(i);
//            ChannelUtil.logE("update[删除] => i = " + i + ", child = " + child);
//            if (null == child)
//                continue;
//            boolean selected = child.isSelected();
//            if (selected) {
//                View select = getChildAt(size - 1);
//                if (null != select && select instanceof ChannelTextView) {
//                    ((ChannelTextView) select).select();
//                    break;
//                }
//            }
//            ChannelUtil.logE("update[删除] => i = " + i + " initText = " + ((ChannelTextView) child).getText());
//        }
        try {
            removeViews(size, count - size);
        } catch (Exception e) {
        }

//        if (count == 0) {
//            View select = getChildAt(0);
//            if (null != select && select instanceof ChannelTextView) {
//                ((ChannelTextView) select).select();
//            }
//        }

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
