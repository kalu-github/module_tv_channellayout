package lib.kalu.tv.channel.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public class ChannelModelNormal implements ChannelModel {

    private int id = 0;
    private String text = null;
    private String url = null;
    private @DrawableRes
    int leftImgDefault = 0;
    private @DrawableRes
    int leftImgHighlight = 0;
    private @DrawableRes
    int rightImgDefault = 0;
    private @DrawableRes
    int rightImgHighlight = 0;

    public ChannelModelNormal(@NonNull String text,
                              @DrawableRes int leftImgDefault,
                              @DrawableRes int leftImgHighlight) {
        this.id = NULL;
        this.text = text;
        this.url = null;
        this.leftImgDefault = leftImgDefault;
        this.leftImgHighlight = leftImgHighlight;
    }

    public ChannelModelNormal(@NonNull String text,
                              @NonNull String url,
                              @DrawableRes int leftImgDefault,
                              @DrawableRes int leftImgHighlight) {
        this.id = NULL;
        this.text = text;
        this.url = url;
        this.leftImgDefault = leftImgDefault;
        this.leftImgHighlight = leftImgHighlight;
    }

    public ChannelModelNormal(@NonNull int id,
                              @NonNull String text,
                              @NonNull String url,
                              @DrawableRes int leftImgDefault,
                              @DrawableRes int leftImgHighlight) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.leftImgDefault = leftImgDefault;
        this.leftImgHighlight = leftImgHighlight;
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
    public int leftImgHighlight() {
        return leftImgHighlight;
    }

    @Override
    public int leftImgDefault() {
        return leftImgDefault;
    }

    @Override
    public int rightImgHighlight() {
        return rightImgHighlight;
    }

    @Override
    public int rightImgDefault() {
        return rightImgDefault;
    }

    @Override
    public String toString() {
        return "ChannelModelNormal{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", leftImgDefault=" + leftImgDefault +
                ", leftImgHighlight=" + leftImgHighlight +
                ", rightImgDefault=" + rightImgDefault +
                ", rightImgHighlight=" + rightImgHighlight +
                '}';
    }
}
