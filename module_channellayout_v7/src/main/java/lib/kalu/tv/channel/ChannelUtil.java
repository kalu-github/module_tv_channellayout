package lib.kalu.tv.channel;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

@Keep
public final class ChannelUtil {

    private static final String TAG = "module_channelLayout";
    private static boolean DEBUG = BuildConfig.DEBUG;

    @Keep
    public static final void logE(@NonNull String message) {
        logE(message, null);
    }

    @Keep
    public static final void logE(@NonNull String message, @Nullable Throwable tr) {

        if (!DEBUG)
            return;

        if (null == message || message.length() == 0)
            return;

        if (null == tr) {
            Log.e(TAG, message);
        } else {
            Log.e(TAG, message, tr);
        }
    }

    @Keep
    public static final void setDebug(boolean debug) {
        DEBUG = debug;
    }
}
