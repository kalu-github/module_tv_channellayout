package com.kalu.channellayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lib.kalu.tv.channel.ChannelLayout;
import lib.kalu.tv.channel.listener.OnChannelChangeListener;
import lib.kalu.tv.channel.model.ChannelModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<List<ChannelModel>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            int column = i;
            ArrayList<ChannelModel> item = new ArrayList<>();

            for (int j = 0; j < 20; j++) {

                int num = j;
                ChannelModel model = new ChannelModel() {
                    @Override
                    public int initId() {
                        return 0;
                    }

                    @Override
                    public String initUrl() {
                        return null;
                    }

                    @Override
                    public String initText() {
                        return "column" + column + "=>item" + num;
                    }

                    @Override
                    public int drawableLeft() {
                        return 0;
                    }
                };
                item.add(model);
            }

            list.add(item);
        }

        ChannelLayout channelLayout = findViewById(R.id.test_channel);
        channelLayout.update(list);

        channelLayout.setOnChannelChangeListener(new OnChannelChangeListener() {
            @Override
            public void onInit(@NonNull int column, @NonNull int position) {
                Log.e("TEST", "onInit => column = " + column + ", position = " + position);
            }

            @Override
            public void onFocus(@NonNull int column, @NonNull int position) {
                Log.e("TEST", "onFocus => column = " + column + ", position = " + position);
            }

            @Override
            public void onMove(@NonNull int column) {
                Log.e("TEST", "onMove => column = " + column);
            }
        });

        channelLayout.select(0, 0);
        channelLayout.select(1, 0, true);
        channelLayout.select(2, 0);
    }
}