package lib.kalu.tv.channel.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;

@Keep
public interface ChannelModel {

    int NULL = Integer.MIN_VALUE;
    int LIVE = 0;
    int VOD = 1;

    int initId();

    int initType();

    CharSequence initUrl();

    CharSequence initText();

    CharSequence initTip();

    @ColorRes
    int initTextColorPlaying();

    @ColorRes
    int initTextColorHighlight();

    @ColorRes
    int initTextColorDefault();

    @DrawableRes
    int initDrawablePlaying();

    @DrawableRes
    int initDrawablePlayingAndEqual();

    @DrawableRes
    int initDrawableHighlight();

    @DrawableRes
    int initDrawableDefault();

    @DrawableRes
    int initBackgroundResourcePlaying();

    @DrawableRes
    int initBackgroundResourceHighlight();

    @DrawableRes
    int initBackgroundResourceDefault();
}
