package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

    @Override
    public void setLongClickable(boolean longClickable) {
        super.setLongClickable(false);
    }

    @Override
    public boolean isLongClickable() {
        return false;
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        reset(false);
    }

    protected final void select(boolean select) {
        setSelected(select);
        setTextColor(mTextColorSelect);
        setBackgroundResource(mBackgroundResourceSelect);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_select, 0, 0, 0);
    }

    protected final void keep(boolean select) {
        setSelected(select);
        setTextColor(mTextColorDefault);
        setBackgroundResource(mBackgroundResourceDefault);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_normal, 0, 0, 0);
    }

    protected final void light(boolean select) {
        setSelected(select);
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

    protected final void reset(boolean select) {
        setSelected(select);
        setClickable(true);
        setTextColor(mTextColorDefault);
        setBackgroundResource(mBackgroundResourceDefault);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_normal, 0, 0, 0);
    }

    protected final void click() {
        setSelected(true);
        setClickable(false);
        setTextColor(mTextColorFocus);
        setBackgroundResource(mBackgroundResourceFocus);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_normal, 0, 0, 0);
    }

    //
//    protected final void select() {
//        setSelected(true);
//        setTextColor(mTextColorSelect);
//        setBackgroundResource(mBackgroundResourceSelect);
//        setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_shape_hightlight_select, 0, 0, 0);
//    }
//

    /*************************/

    private final void init() {
        setFocusable(false);
        setFocusableInTouchMode(false);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setBackgroundColor(Color.TRANSPARENT);
        setMarqueeRepeatLimit(Integer.MAX_VALUE);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setSingleLine();
        setHorizontallyScrolling(true);
        reset(false);
    }

    /*************************/
}
