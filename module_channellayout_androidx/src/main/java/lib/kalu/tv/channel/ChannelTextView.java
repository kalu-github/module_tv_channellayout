package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import lib.kalu.tv.channel.model.ChannelModel;

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
    private int mLeftImgDefault = 0;
    @DrawableRes
    private int mLeftImgHighlight = 0;
    @DrawableRes
    private int mRightImgDefault = 0;
    @DrawableRes
    private int mRightImgHighlight = 0;

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

    /*************************/

    protected final void setCompoundDrawables(@NonNull boolean left) {
        setCompoundDrawables(left, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected final void setCompoundDrawables(@NonNull boolean left, @NonNull boolean right) {

        @DrawableRes
        int imgLeft;
        @DrawableRes
        int imgRight;

        try {
            ChannelModel temp = (ChannelModel) getTag(R.id.module_channel_tag_item);
            imgLeft = left ? temp.leftImgHighlight() : temp.leftImgDefault();
            imgRight = right ? temp.rightImgHighlight() : temp.rightImgDefault();
        } catch (Exception e) {
            imgLeft = left ? mLeftImgHighlight : mLeftImgDefault;
            imgRight = right ? mRightImgHighlight : mRightImgDefault;
        }

        try {
            setCompoundDrawablesWithIntrinsicBounds(imgLeft, 0, imgRight, 0);
        } catch (Exception e) {
        }
    }

    /*************************/

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
        setCompoundDrawables(false, false);
    }

    /*************************/
}
