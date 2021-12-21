package lib.kalu.tv.channel.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;

@Keep
public interface ChannelModel {

    int initId();

    CharSequence initUrl();

    CharSequence initText();

    @DrawableRes
    int drawableLeft();
}
