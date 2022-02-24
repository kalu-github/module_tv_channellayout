package lib.kalu.tv.channel.listener;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.kalu.tv.channel.model.ChannelModel;

@Keep
public interface OnChannelChangeListener {

    @Keep
    void onMove(@NonNull int column, @NonNull int position, @NonNull int before, int count, @NonNull ChannelModel value);

    @Keep
    void onInit(@NonNull int column, @NonNull int position, @NonNull int before, int count, @NonNull ChannelModel value);

    @Keep
    void onClick(@NonNull int column, @NonNull int position, @NonNull int before, int count, @NonNull ChannelModel value);

    @Keep
    void onRepeat(@NonNull int column, @NonNull int position, @NonNull int before, int count, @NonNull ChannelModel value);

    @Keep
    void onHighlight(@NonNull int column, @NonNull int position, @NonNull int before, int count, @NonNull ChannelModel value);
}
