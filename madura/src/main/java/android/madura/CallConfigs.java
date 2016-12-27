package android.madura;

import android.app.Activity;
import android.madura.event.OnEndCallClickListener;

/**
 * Created by rahardyan on 27/12/16.
 */

public class CallConfigs {
    private OnEndCallClickListener onEndCallClickListener = new OnEndCallClickListener() {
        @Override
        public void onClick(Activity activity) {
            activity.finish();
        }
    };

    public CallConfigs setEndCallClickListener(OnEndCallClickListener onEndCallClickListener){
        this.onEndCallClickListener = onEndCallClickListener;
        return this;
    }

    public OnEndCallClickListener getOnEndCallClickListener() {
        return this.onEndCallClickListener;
    }


}
