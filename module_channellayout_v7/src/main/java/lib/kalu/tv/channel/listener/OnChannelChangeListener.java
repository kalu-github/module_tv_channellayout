package lib.kalu.tv.channel.listener;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;

@Keep
public interface OnChannelChangeListener {

    void onFocus(@NonNull int column, @NonNull int position);

    void onMove(@NonNull int column);
}
