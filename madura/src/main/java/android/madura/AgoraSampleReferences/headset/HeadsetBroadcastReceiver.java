package android.madura.AgoraSampleReferences.headset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class HeadsetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_HEADSET_PLUG.equalsIgnoreCase(intent.getAction())) {
            // http://developer.android.com/reference/android/content/Intent.html#ACTION_HEADSET_PLUG
            if (intent.hasExtra("state")) {
                int plugged = intent.getIntExtra("state", -1);
                String name = intent.getStringExtra("name");
                int microphone = intent.getIntExtra("microphone", -1);
                Log.d("Headset", "Intent.ACTION_HEADSET_PLUG " + name + " " + microphone + " " + plugged);
                if (plugged == 0 || plugged == 1) {
                    HeadsetPlugManager.getInstance().notifyHeadsetPlugged(plugged == 1); // 1 for plugged, 0 for unplugged
                } else {
                    Log.e("Headset", "illegal state from ACTION_HEADSET_PLUG");
                }
            }
        }
    }
}
