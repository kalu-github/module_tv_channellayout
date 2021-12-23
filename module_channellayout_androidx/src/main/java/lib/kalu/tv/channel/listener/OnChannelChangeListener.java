package lib.kalu.tv.channel.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import lib.kalu.tv.channel.model.ChannelModel;

@Keep
public interface OnChannelChangeListener {

    void onSelect(@NonNull int column, @NonNull int position, @NonNull ChannelModel value);

    void onHighlight(@NonNull int column, @NonNull int position, @NonNull ChannelModel value);

    void onMove(@NonNull int column, @NonNull int position, @NonNull ChannelModel value);
}
