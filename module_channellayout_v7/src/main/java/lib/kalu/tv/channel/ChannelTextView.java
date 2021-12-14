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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ChannelUtil.logE("onDraw => " + isSelected() + "-" + hasFocus());
        if (isHightlight()) {
            getPaint().setColor(getResources().getColor(R.color.module_channellayout_color_ffc700));
            int height = getHeight();
            int width = getWidth();
            Paint.FontMetrics metrics = getPaint().getFontMetrics();
            float fontHeight = (metrics.bottom - metrics.top) * 0.6f;
            String text = String.valueOf(getText());
            float fontWidth = getPaint().measureText(text);
            float equal = fontWidth / text.length();
            RectF rectF1 = new RectF();
            rectF1.left = (width / 2 - fontWidth / 2) - equal * 2;
            rectF1.top = height / 2 - fontHeight / 2 + equal / 10;
            rectF1.right = rectF1.left + equal * 2 / 4;
            rectF1.bottom = height - (height / 2 - fontHeight / 2);
            canvas.drawRect(rectF1, getPaint());
            RectF rectF2 = new RectF();
            rectF2.left = rectF1.right + rectF1.width() / 3;
            rectF2.top = rectF1.top;
            rectF2.right = rectF2.left + rectF1.width() / 4;
            rectF2.bottom = rectF1.bottom;
            canvas.drawRect(rectF2, getPaint());
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        ChannelUtil.logE("onFocusChanged => isSelected = " + isSelected() + ", focus = " + focused + ", direction = " + direction + ", start = " + -1 + ", text = " + getText());
        if (isSelected() && !focused) {
            setTextColor(mTextColorSelect);
            setBackgroundResource(mBackgroundResourceSelect);
        } else if (focused) {

            // 监听
            try {
                boolean activated = (boolean) getTag(R.id.module_channellayout_id_repeat);
                if (activated) {
                    setTag(R.id.module_channellayout_id_repeat, false);
                } else {
                    ((ChannelLinearLayoutChild) getParent()).callback(this, Integer.MIN_VALUE);
                }
            } catch (Exception e) {
            }

            setTextColor(mTextColorFocus);
            setBackgroundResource(mBackgroundResourceFocus);
        } else {
            setTextColor(mTextColorDefault);
            setBackgroundResource(mBackgroundResourceDefault);
        }
    }

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
        setTextColor(select ? mTextColorSelect : mTextColorDefault);
        if (invalidate) {
            invalidate();
        }
    }

    public final void hightlight() {
        setTag(R.id.module_channellayout_id_repeat, true);
        hightlight(true);
    }

    public final void hightlight(boolean enable) {
        setSelected(enable);
        setTextColor(enable ? mTextColorSelect : mTextColorDefault);
        setBackgroundResource(enable ? mBackgroundResourceSelect : mBackgroundResourceDefault);
    }

    protected final boolean isHightlight() {
        return hasFocus() || isSelected();
    }

    /*************************/

    private final void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setSelected(false);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setBackgroundColor(Color.TRANSPARENT);
        setMarqueeRepeatLimit(Integer.MAX_VALUE);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setSingleLine();
        setHorizontallyScrolling(true);
        setTag(R.id.module_channellayout_id_repeat, false);
    }

    /*************************/
}
