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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
class ChannelTextView extends TextView {

    /**
     * 默认
     */
    @ColorInt
    private int mTextColorDefault = getResources().getColor(R.color.module_channellayout_color_ababab);
    /**
     * 高亮
     */
    @ColorInt
    private int mTextColorHighlight = getResources().getColor(R.color.module_channellayout_color_333333);
    /**
     * 选中
     */
    private int mTextColorSelect = getResources().getColor(R.color.module_channellayout_color_ffc700);
    /**
     * 在播
     */
    @ColorInt
    private int mTextColorPlaying = getResources().getColor(R.color.module_channellayout_color_ffffff);

    @DrawableRes
    private int mBackgroundDefault = R.drawable.module_channellayout_ic_background_default;
    @DrawableRes
    private int mBackgroundHighlight = R.drawable.module_channellayout_ic_background_hightlight;

    @DrawableRes
    private int mDrawableDefault = R.drawable.module_channellayout_ic_img_default;
    @DrawableRes
    private int mDrawableHighlight = R.drawable.module_channellayout_ic_img_highlight;

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

    /**************/

    protected final void setBackgroundDefault() {
        setBackgroundResource(R.drawable.module_channellayout_ic_background_default);
    }

    protected final void setBackgroundHighlight() {
        setBackgroundResource(R.drawable.module_channellayout_ic_background_hightlight);
    }

    protected final void setLeftDrawable(@NonNull boolean show) {

        if (show) {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_img_highlight, 0, 0, 0);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_channellayout_ic_img_default, 0, 0, 0);
        }
    }

    protected final void setTextColorDefault() {
        setTextColor(mTextColorDefault);
    }

    protected final void setTextColorHighlight() {
        setTextColor(mTextColorHighlight);
    }

    protected final void setTextColorSelect() {
        setTextColor(mTextColorSelect);
    }

    protected final void setTextColorPlaying() {
        setTextColor(mTextColorPlaying);
    }

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

        setTextColorDefault();
        setBackgroundDefault();
        setLeftDrawable(false);
    }

    /*************************/
}
