package lib.kalu.tv.channel.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;

@Keep
public interface ChannelModel {

    int NULL = Integer.MIN_VALUE;

    int initId();

    CharSequence initUrl();

    CharSequence initText();

    @DrawableRes
    int leftImgHighlight();

    @DrawableRes
    int leftImgDefault();

    @DrawableRes
    int rightImgHighlight();

    @DrawableRes
    int rightImgDefault();
}
