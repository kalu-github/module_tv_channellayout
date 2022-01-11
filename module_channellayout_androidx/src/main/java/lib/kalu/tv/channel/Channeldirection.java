package lib.kalu.tv.channel;

import androidx.annotation.IntDef;
import androidx.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Keep
@Retention(RetentionPolicy.SOURCE)
public @interface Channeldirection {

    int INIT = 1;
    int CLICK = 2;
    int UP = 3;
    int NEXT_UP = 4;
    int DOWN = 5;
    int NEXT_DOWN = 6;
    int LEFT = 7;
    int RIGHT = 8;

    int MIN = INIT;
    int MAX = RIGHT;


    @IntDef(value = {
            Channeldirection.INIT,
            Channeldirection.CLICK,
            Channeldirection.UP,
            Channeldirection.NEXT_UP,
            Channeldirection.DOWN,
            Channeldirection.NEXT_DOWN,
            Channeldirection.LEFT,
            Channeldirection.RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @Keep
    @interface Value {
    }
}