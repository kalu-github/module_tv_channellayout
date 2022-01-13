package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Gravity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lib.kalu.tv.channel.model.ChannelModel;

@SuppressLint("AppCompatCustomView")
class ChannelTextView extends ChannelTextViewMarquee {

    public ChannelTextView(Context context, @NonNull int maxEms) {
        super(context);
        setTag(R.id.module_channel_item_max_ems, maxEms);
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
    public boolean isFocused() {
        return false;
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
    }

    /**************/

    protected final void setCompoundDrawables1(@NonNull boolean isHighlight, @NonNull boolean isPlaying) {

        @DrawableRes
        int img;

        try {
            ChannelModel temp = (ChannelModel) getTag(R.id.module_channel_tag_item);
            if (isPlaying) {
                img = temp.initDrawablePlaying();
            } else if (isHighlight) {
                img = temp.initDrawableHighlight();
            } else {
                img = temp.initDrawableDefault();
            }
        } catch (Exception e) {
            if (isPlaying) {
                img = 0;
            } else if (isHighlight) {
                img = 0;
            } else {
                img = 0;
            }
        }

        try {
            setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
        } catch (Exception e) {
        }
    }

    protected final void setTextColor(@NonNull boolean isHighlight, @NonNull boolean isPlaying) {

        // step1: color
        @ColorRes
        int res;
        try {
            ChannelModel temp = (ChannelModel) getTag(R.id.module_channel_tag_item);
            if (isPlaying) {
                res = temp.initTextColorPlaying();
            } else if (isHighlight) {
                res = temp.initTextColorHighlight();
            } else {
                res = temp.initTextColorDefault();
            }
        } catch (Exception e) {
            if (isPlaying) {
                res = R.color.module_channellayout_color_ffffff;
            } else if (isHighlight) {
                res = R.color.module_channellayout_color_ffc700;
            } else {
                res = R.color.module_channellayout_color_ababab;
            }
        }
        try {
            int color = getResources().getColor(res);
            setTextColor(color);
        } catch (Exception e) {
        }

        // step2: scroll
        CharSequence text = getText();
        int maxEms;
        try {
            maxEms = (int) getTag(R.id.module_channel_item_max_ems);
        } catch (Exception e) {
            maxEms = -1;
        }
        if (maxEms > 0 && null != text && text.length() > maxEms) {
            if (isHighlight) {
                startScroll();
            } else {
                stopScroll();
            }
        }
    }

    protected final void setTextColor(@NonNull boolean isHighlight, @ColorRes int res) {

        // step1: color
        try {
            int color = getResources().getColor(res);
            setTextColor(color);
        } catch (Exception e) {
        }

        // step2: scroll
        CharSequence text = getText();
        int maxEms;
        try {
            maxEms = (int) getTag(R.id.module_channel_item_max_ems);
        } catch (Exception e) {
            maxEms = -1;
        }
        if (maxEms > 0 && null != text && text.length() > maxEms) {
            if (isHighlight) {
                startScroll();
            } else {
                stopScroll();
            }
        }
    }

    protected final void setBackgroundResource(@NonNull boolean isHighlight, @NonNull boolean isPlaying) {

        // step1: background
        @DrawableRes
        int res;
        try {
            ChannelModel temp = (ChannelModel) getTag(R.id.module_channel_tag_item);
            if (isPlaying) {
                res = temp.initBackgroundResourcePlaying();
            } else if (isHighlight) {
                res = temp.initBackgroundResourceHighlight();
            } else {
                res = temp.initBackgroundResourceDefault();
            }
        } catch (Exception e) {
            if (isPlaying) {
                res = -1;
            } else if (isHighlight) {
                res = -2;
            } else {
                res = -3;
            }
        }
        try {
            setBackgroundResource(res);
        } catch (Exception e) {
            @ColorInt
            int color;
            if (isPlaying) {
                color = getResources().getColor(R.color.module_channellayout_color_00000000);
            } else if (isHighlight) {
                color = getResources().getColor(R.color.module_channellayout_color_ffc700);
            } else {
                color = getResources().getColor(R.color.module_channellayout_color_00000000);
            }
            setBackgroundColor(color);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // tip
        try {
            ChannelModel temp = (ChannelModel) getTag(R.id.module_channel_tag_item);
            CharSequence tip = temp.initTip();
            if (null == tip || tip.length() <= 0)
                return;
//            int padding = getCompoundDrawablePadding();
            int paddingLeft = getPaddingLeft();
            canvas.drawText(String.valueOf(tip), paddingLeft, getHeight() / 2, getPaint());
        } catch (Exception e) {
        }
    }

    /*************************/

    private final void init() {
        try {
            ChannelModel temp = (ChannelModel) getTag(R.id.module_channel_tag_item);
            CharSequence tip = temp.initTip();
            if (null != tip && tip.length() > 0) {
                int measureText = (int) getPaint().measureText(String.valueOf(tip));
                int drawablePadding = getCompoundDrawablePadding();
                setCompoundDrawablePadding(drawablePadding + measureText);
            }
        } catch (Exception e) {
        }

        setSingleLine(true);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setBackgroundColor(Color.TRANSPARENT);

        setTextColor(false, false);
        setCompoundDrawables1(false, false);
        setBackgroundResource(false, false);
    }

    /*************************/
}
