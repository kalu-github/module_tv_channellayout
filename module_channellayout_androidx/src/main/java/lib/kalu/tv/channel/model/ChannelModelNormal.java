package lib.kalu.tv.channel.model;

import android.annotation.SuppressLint;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import lib.kalu.tv.channel.R;

@Keep
public class ChannelModelNormal implements ChannelModel {

//    private int id = ChannelModel.NULL;
//    private int type = ChannelModel.VOD;
    private String tip = null;
    private String text = null;
    private String url = null;
    private String bundle = null;

    @DrawableRes
    private int mDrawablePlaying = 0;
    @DrawableRes
    private int mDrawablePlayingAndEqual = 0;
    @DrawableRes
    private int mDrawablePlayingAndHighlight = 0;
    @DrawableRes
    private int mDrawableHighlight = 0;
    @DrawableRes
    private int mDrawableDefault = 0;

    @ColorRes
    private int mTextColorPlaying = R.color.module_channellayout_color_ffffff;
    @ColorRes
    private int mTextColorHighlight = R.color.module_channellayout_color_ffc700;
    @ColorRes
    private int mTextColorDefault = R.color.module_channellayout_color_ababab;

    @DrawableRes
    private int mBackgroundResourcePlaying = -1;
    @SuppressLint("ResourceType")
    @DrawableRes
    private int mBackgroundResourceHighlight = -2;
    @SuppressLint("ResourceType")
    @DrawableRes
    private int mBackgroundResourceDefault = -3;

    @Override
    public CharSequence initUrl() {
        return url;
    }

    @Override
    public CharSequence initText() {
        return text;
    }

    @Override
    public CharSequence initTip() {
        return tip;
    }

    @Nullable
    @Override
    public String initBundle() {
        return bundle;
    }

    @Override
    public int initTextColorPlaying() {
        return mTextColorPlaying;
    }

    @Override
    public int initTextColorHighlight() {
        return mTextColorHighlight;
    }

    @Override
    public int initTextColorDefault() {
        return mTextColorDefault;
    }


    @Override
    public int initDrawablePlaying() {
        return mDrawablePlaying;
    }

    @Override
    public int initDrawablePlayingAndEqual() {
        return mDrawablePlayingAndEqual;
    }

    @Override
    public int initDrawablePlayingAndHightlght() {
        return mDrawablePlayingAndHighlight;
    }

    @Override
    public int initDrawableHighlight() {
        return mDrawableHighlight;
    }

    @Override
    public int initDrawableDefault() {
        return mDrawableDefault;
    }

    @Override
    public int initBackgroundResourcePlaying() {
        return mBackgroundResourcePlaying;
    }

    @Override
    public int initBackgroundResourceHighlight() {
        return mBackgroundResourceHighlight;
    }

    @Override
    public int initBackgroundResourceDefault() {
        return mBackgroundResourceDefault;
    }

    /************/

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDrawablePlaying(@DrawableRes int mDrawablePlaying) {
        this.mDrawablePlaying = mDrawablePlaying;
    }

    public void setDrawablePlayingAndEqual(@DrawableRes int mDrawablePlayingAndEqual) {
        this.mDrawablePlayingAndEqual = mDrawablePlayingAndEqual;
    }

    public void setDrawableHighlight(@DrawableRes int mDrawableHighlight) {
        this.mDrawableHighlight = mDrawableHighlight;
    }

    public void setDrawableDefault(@DrawableRes int mDrawableDefault) {
        this.mDrawableDefault = mDrawableDefault;
    }

    public void setTextColorPlaying(@ColorRes int mTextColorPlaying) {
        this.mTextColorPlaying = mTextColorPlaying;
    }

    public void setTextColorHighlight(@ColorRes int mTextColorHighlight) {
        this.mTextColorHighlight = mTextColorHighlight;
    }

    public void setTextColorDefault(@ColorRes int mTextColorDefault) {
        this.mTextColorDefault = mTextColorDefault;
    }

    public void setBackgroundResourcePlaying(@DrawableRes int mBackgroundResourcePlaying) {
        this.mBackgroundResourcePlaying = mBackgroundResourcePlaying;
    }

    public void setBackgroundResourceHighlight(@DrawableRes int mBackgroundResourceHighlight) {
        this.mBackgroundResourceHighlight = mBackgroundResourceHighlight;
    }

    public void setBackgroundResourceDefault(@DrawableRes int mBackgroundResourceDefault) {
        this.mBackgroundResourceDefault = mBackgroundResourceDefault;
    }

    public void setDrawablePlayingAndLight(@DrawableRes int mDrawablePlayingAndHighlight) {
        this.mDrawablePlayingAndHighlight = mDrawablePlayingAndHighlight;
    }
}
