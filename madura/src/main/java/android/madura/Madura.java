package android.madura;

import android.app.Activity;
import android.content.Context;
import android.madura.AgoraSampleReferences.threadhelper.WorkerThread;
import android.madura.CallLibrary.AgoraExample;
import android.madura.event.CallEvent;
import android.madura.event.OnEndCallClickListener;
import android.widget.RelativeLayout;

/**
 * Created by rahardyan on 17/12/16.
 */

public class Madura {
    private static AgoraExample callLibrary;
    private static WorkerThread mWorkerThread;
    private static OnEndCallClickListener callActivityClickListener;
    private static CallConfigs callConfigs;

    public Madura() {
    }

    public static void init(Context context) {
        callLibrary = new AgoraExample(context);
        callConfigs = new CallConfigs();
    }

    public static CallConfigs getCallConfigs(){
        return callConfigs;
    }

    public static void setListener(CallEvent listener){
        callLibrary.setListener(listener);
    }

    public static void setEndCallClickListener(OnEndCallClickListener callClickListener){
        callActivityClickListener = callClickListener;
    }

    public static OnEndCallClickListener getCallActivityClickListener(){
        return callActivityClickListener;
    }

    public static void setRootLayout(Activity activity, RelativeLayout rootLayout, RelativeLayout smallVideo, String key, String mode){
        callLibrary.setRootContainer(activity, rootLayout, smallVideo, key, mode);
    }

    public static void startCall(String target){
        callLibrary.startCall(target);
    }

    public static void endCall(){
        callLibrary.endCall();
    }

    public static void muteAudio() {
        callLibrary.muteAudio();
    }

    public static void swithcCamera(){
        callLibrary.onSwitchCameraClicked();
    }

    public static synchronized void initWorkerThread(Context context) {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(context);
            mWorkerThread.start();

            mWorkerThread.waitForReady();
        }
    }

    public static synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public static synchronized void doInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }

    public enum CallAs{
        CALLER,
        CALLEE
    }
}
