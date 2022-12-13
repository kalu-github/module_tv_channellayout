package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
@SuppressLint("AppCompatCustomView")
public interface ChannelFootImpl {

    default void setBackgroundBlur(@NonNull int value, @NonNull boolean blurScript, @NonNull int resid) {
        if (value <= 0) {
            setBackgroundResId(resid);
        } else {
            setBackgroundDrawableId(value, blurScript, resid);
        }
    }

    default void setBackgroundResId(@NonNull int resid) {
        try {
            ((View) this).setBackgroundResource(resid);
        } catch (Exception e) {
            ChannelUtil.logE("setBackgroundResId => " + e.getMessage(), e);
        }
    }

    default void setBackgroundDrawableId(@NonNull int value, @NonNull boolean blurScript, @NonNull int resid) {
        try {
            Context context = ((View) this).getContext();
            Bitmap bitmap = ChannelUtil.blurResources(context, resid, value, blurScript);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            ((View) this).setBackground(drawable);
        } catch (Exception e) {
            ChannelUtil.logE("setBackgroundDrawableId => " + e.getMessage(), e);
            setBackgroundResId(resid);
        }
    }
}
