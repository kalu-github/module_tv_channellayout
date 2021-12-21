package lib.kalu.tv.channel.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public interface OnChannelChangeListener {

    void onInit(@NonNull int column, @NonNull int position);

    void onFocus(@NonNull int column, @NonNull int position);

    void onClick(@NonNull int column, @NonNull int position);

    void onMove(@NonNull int column);
}
