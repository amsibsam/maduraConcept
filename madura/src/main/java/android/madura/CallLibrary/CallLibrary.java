package android.madura.CallLibrary;

import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by rahardyan on 17/12/16.
 */

public abstract class CallLibrary {
    public void startCall(String channel) {
        Log.d("amsibsam", "start calling");
    }

    public void endCall() {
        Log.d("amsibsam", "end call");
    }

    public void setRootContainer(RelativeLayout frameLayout){

    }
}
