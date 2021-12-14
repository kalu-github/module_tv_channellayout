package lib.kalu.tv.channel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

class ChannelUtil {

    private static final String TAG = "module_channelLayout";

    public static final void logE(@NonNull String message) {
        logE(message, null);
    }

    public static final void logE(@NonNull String message, @Nullable Throwable tr) {

        if (null == message || message.length() == 0)
            return;

        if (null == tr) {
            Log.e(TAG, message);
        } else {
            Log.e(TAG, message, tr);
        }
    }
}
