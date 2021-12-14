package com.kalu.channellayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;

import lib.kalu.tv.channel.ChannelLayout;
import lib.kalu.tv.channel.listener.OnChannelChangeListener;
import lib.kalu.tv.channel.model.ChannelModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ChannelModel> list0 = new ArrayList<>();
        ArrayList<ChannelModel> list1 = new ArrayList<>();
        ArrayList<ChannelModel> list2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            int finalI = i;
            ChannelModel model0 = new ChannelModel() {
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
                    return "a" + finalI;
                }

                @Override
                public boolean initSelect() {
                    return finalI == 0;
                }
            };
            list0.add(model0);

            ChannelModel model1 = new ChannelModel() {
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
                    return "b" + finalI;
                }

                @Override
                public boolean initSelect() {
                    return finalI == 0;
                }
            };
            list1.add(model1);

            ChannelModel model2 = new ChannelModel() {
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
                    return "a" + finalI;
                }

                @Override
                public boolean initSelect() {
                    return finalI == 0;
                }
            };
            list2.add(model2);
        }

        ChannelLayout channelLayout = findViewById(R.id.test_channel);
        channelLayout.update(list0, list1, list2);
        channelLayout.select();

        channelLayout.setOnChannelChangeListener(new OnChannelChangeListener() {
            @Override
            public void onFocus(@NonNull int column, @NonNull int position) {
                Log.e("MainActivity", "onFocus => column = " + column + ", position = " + position);

                if (column == 0) {
                    list1.clear();
                    for (int i = 0; i < 20; i++) {
                        int finalI = i;
                        ChannelModel model1 = new ChannelModel() {
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
                                return "a" + position + "=>b" + finalI;
                            }

                            @Override
                            public boolean initSelect() {
                                return false;
                            }
                        };
                        list1.add(model1);
                    }
                    channelLayout.refresh(1, list1);
                }
            }

            @Override
            public void onMove(@NonNull int column) {
                Log.e("MainActivity", "onMove => column = " + column);
            }
        });
    }
}