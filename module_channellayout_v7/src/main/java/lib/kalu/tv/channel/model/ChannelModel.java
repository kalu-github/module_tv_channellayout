package lib.kalu.tv.channel.model;

import android.support.annotation.Keep;

@Keep
public interface ChannelModel {

    int initId();

    CharSequence initUrl();

    CharSequence initText();

    boolean initSelect();
}
