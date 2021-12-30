package lib.kalu.tv.channel;

import androidx.annotation.IntDef;
import androidx.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Keep
@Retention(RetentionPolicy.SOURCE)
public @interface Channeldirection {

    int INIT = 0x0001;
    int SELECT = 0x0002;
    int UP = 0x0003;
    int NEXT_UP = 0x0004;
    int DOWN = 0x0005;
    int NEXT_DOWN = 0x0006;
    int LEFT = 0x0007;
    int RIGHT = 0x0008;

    int MIN = INIT;
    int MAX = RIGHT;


    @IntDef(value = {
            Channeldirection.INIT,
            Channeldirection.SELECT,
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