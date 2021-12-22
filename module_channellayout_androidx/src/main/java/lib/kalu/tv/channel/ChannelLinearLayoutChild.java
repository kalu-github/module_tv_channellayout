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

//        if (getVisibility() != View.VISIBLE)
//            return super.dispatchKeyEvent(event);

        // repeat
        if (event.getRepeatCount() > 0) {
            return true;
        }
        // down move
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelUtil.logE("dispatchKeyEvent[down move] => focus = " + focus);
                nextFocus(View.FOCUS_DOWN);
                return true;
            }
        }
        // up move
        else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                ChannelUtil.logE("dispatchKeyEvent[up move] => focus = " + focus);
                nextFocus(View.FOCUS_UP);
                return true;
            }
        }
        // right move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int count = ((ChannelLayout) getParent().getParent()).getChildCount();
                int column = ((ChannelLayout) getParent().getParent()).indexOfChild((View) getParent());
                ChannelUtil.logE("dispatchKeyEvent[right move] => count = " + count + ", column = " + column);
                if (column + 1 < count) {
                    ChannelUtil.logE("dispatchKeyEvent[right move] => focus = " + focus);
                    View child = ((ChannelLayout) getParent().getParent()).getChildAt(column + 1);
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
                return true;
            }
        }
        // left move
        else if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            View focus = findFocus();
            if (null != focus && focus instanceof ChannelLinearLayoutChild) {
                int column = ((ChannelLayout) getParent().getParent()).indexOfChild((View) getParent());
                ChannelUtil.logE("dispatchKeyEvent[left move] => column = " + column);
                if (column >= 1) {
                    ChannelUtil.logE("dispatchKeyEvent[left move] => focus = " + focus);
                    View child = ((ChannelLayout) getParent().getParent()).getChildAt(column - 1);
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
                return true;
            }
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
                return true;
            }
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
            column = ((ViewGroup) getParent().getParent()).indexOfChild((View) getParent());
        } catch (Exception e) {
            column = -1;
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
//                boolean selected = view.isSelected();
//                boolean clickable = view.isClickable();
//                ChannelUtil.logE("keepLight => selected = " + selected + ", clickable = " + clickable);
//                if (!clickable && selected) {
                    ((ChannelTextView) view).light(true);
//                } else {
//                    ((ChannelTextView) view).keep(true);
//                }
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

        int position = findPosition(column);
        if (position < 0)
            return;

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

//            // scroll => down
//            if (direction == View.FOCUS_DOWN) {
//                int bottom = viewNext.getBottom();
//                int scrollY = ((ViewGroup) getParent()).getScrollY();
//                int measuredHeight = ((ViewGroup) getParent()).getHeight() + scrollY;
//                if (bottom > measuredHeight) {
//                    ((ChannelScrollView) getParent()).smoothScrollBy(0, Math.abs(bottom - measuredHeight));
//                }
//                ChannelUtil.logE("nextFocus[viewNext][down] => focus");
//            }
//            // scroll => up
//            else if (direction == View.FOCUS_UP) {
//                int top = viewNext.getTop();
//                int scrollY = ((ViewGroup) getParent()).getScrollY();
//                if (top < scrollY) {
//                    ((ChannelScrollView) getParent()).smoothScrollBy(0, -Math.abs(scrollY - top));
//                }
//                ChannelUtil.logE("nextFocus[viewNext][up] => focus");
//            }
//            // scroll => null
//            else {
//                ChannelUtil.logE("nextFocus[viewNext][null] => focus");
//            }

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
            ChannelTextView child = new ChannelTextView(getContext());
            child.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            int offset = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_size);
            child.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset);
            int left = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_left);
            int right = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_right);
            int top = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_top);
            int bottom = getResources().getDimensionPixelOffset(R.dimen.module_channellayout_item_padding_bottom);
            child.setPadding(left, top, right, bottom);
            child.setText(initText);
            child.setTag(R.id.module_channel_item_tag, temp);
            // add
            addView(child);
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

        View child = getChildAt(position);

        ChannelModel value;
        try {
            value = (ChannelModel) child.getTag(R.id.module_channel_item_tag);
        } catch (Exception e) {
            value = null;
        }

        if (null == getParent() || !(getParent() instanceof ChannelScrollView))
            return;
        ((ChannelScrollView) getParent()).callback(position, direction, value);
    }
}
