package lib.kalu.tv.channel.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.kalu.tv.channel.model.ChannelModel;

@Keep
public interface OnChannelChangeListener {

    void onMove(@NonNull int column, @NonNull int position, int count, @NonNull ChannelModel value);

    void onSelect(@NonNull int column, @NonNull int position, int count, @NonNull ChannelModel value);

    void onHighlight(@NonNull int column, @NonNull int position, int count, @NonNull ChannelModel value);
}
