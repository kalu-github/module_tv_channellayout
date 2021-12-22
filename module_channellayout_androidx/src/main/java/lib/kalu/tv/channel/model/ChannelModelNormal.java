package lib.kalu.tv.channel.model;

import androidx.annotation.Keep;

@Keep
public class ChannelModelNormal implements ChannelModel {

    private int id = 0;
    private String url = null;
    private String text = null;

    public ChannelModelNormal(int id, String url, String text) {
        this.id = id;
        this.url = url;
        this.text = text;
    }

    @Override
    public int initId() {
        return id;
    }

    @Override
    public CharSequence initUrl() {
        return url;
    }

    @Override
    public CharSequence initText() {
        return text;
    }

    @Override
    public int drawableLeft() {
        return 0;
    }
}
