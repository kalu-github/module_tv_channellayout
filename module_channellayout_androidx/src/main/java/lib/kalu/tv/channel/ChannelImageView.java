package lib.kalu.tv.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@Keep
@SuppressLint("AppCompatCustomView")
public class ChannelImageView extends ImageView {
    public ChannelImageView(Context context) {
        super(context);
    }

    public ChannelImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChannelImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setBackground(@NonNull int value, @NonNull boolean blurScript, @NonNull int resid) {
        if (value <= 0) {
            setBackgroundResource(resid);
        } else {
            try {
                Bitmap bitmap = ChannelUtil.blurResources(getContext(), resid, value, blurScript);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                super.setBackground(drawable);
                ChannelUtil.logE("setBackgroundBlur2 => blur succ");
            } catch (Exception e) {
                setBackgroundResource(resid);
                ChannelUtil.logE("setBackgroundBlur2 => " + e.getMessage(), e);
            }
        }
    }
}
