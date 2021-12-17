package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("AppCompatCustomView")
class ChannelTextView extends TextView {

    @ColorInt
    private int mTextColorFocus = getResources().getColor(R.color.module_channellayout_color_333333);
    @ColorInt
    private int mTextColorSelect = getResources().getColor(R.color.module_channellayout_color_ffffff);
    @ColorInt
    private int mTextColorDefault = getResources().getColor(R.color.module_channellayout_color_ababab);

    @DrawableRes
    private int mBackgroundResourceFocus = R.drawable.module_channellayout_ic_shape_background_focus;
    @DrawableRes
    private int mBackgroundResourceSelect = R.drawable.module_channellayout_ic_shape_background_select;
    @DrawableRes
    private int mBackgroundResourceDefault = R.drawable.module_channellayout_ic_shape_background_default;

    public ChannelTextView(Context context) {
        super(context);
        init();
    }

    public ChannelTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChannelTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChannelTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        CharSequence text = getText();
//        if (null == text) {
//            text = "";
//        }
//        int width = (int) getPaint().measureText(String.valueOf(text));
//        if (width > 0) {
//            width += getPaddingLeft();
//            width += getPaddingRight();
//        }
//        setMeasuredDimension(width, height);
////        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    public void clearFocus() {
        setSelected(false);
        super.clearFocus();
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        setSelected(false);
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    protected final void setText(@NonNull CharSequence text, boolean select) {
        setText(text, select, false);
    }

    protected final void setText(@NonNull CharSequence text, boolean select, boolean invalidate) {
        super.setText(text);
        setSelected(select);
        setTextColor(select ? mTextColorSelect : mTextColorDefault);
        if (invalidate) {
            invalidate();
        }
    }

    protected final boolean isHightlight() {
        return isSelected();
    }

    protected final void select() {
        setSelected(true);
        setTextColor(mTextColorSelect);
        setBackgroundResource(mBackgroundResourceSelect);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_select, 0, 0, 0);
    }

    protected final void focus() {
        setSelected(true);
        setTextColor(mTextColorFocus);
        setBackgroundResource(mBackgroundResourceFocus);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_normal, 0, 0, 0);
    }

    protected final void reset() {
        setSelected(false);
        setTextColor(mTextColorDefault);
        setBackgroundResource(mBackgroundResourceDefault);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_normal, 0, 0, 0);
    }

    /*************************/

    private final void init() {
        setClickable(false);
        setLongClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setSelected(false);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setBackgroundColor(Color.TRANSPARENT);
        setMarqueeRepeatLimit(Integer.MAX_VALUE);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setSingleLine();
        setHorizontallyScrolling(true);
        reset();
//        setTag(R.id.module_channellayout_id_repeat, false);
    }

    /*************************/
}
