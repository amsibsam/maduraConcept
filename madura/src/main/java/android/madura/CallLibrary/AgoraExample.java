package android.madura.CallLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.madura.Madura;
import android.madura.event.AGEventHandler;
import android.madura.AgoraSampleReferences.model.ConstantApp;
import android.madura.AgoraSampleReferences.model.EngineConfig;
import android.madura.event.MyEngineEventHandler;
import android.madura.AgoraSampleReferences.threadhelper.WorkerThread;
import android.madura.event.CallEvent;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Created by rahardyan on 17/12/16.
 */

public class AgoraExample extends CallLibrary implements AGEventHandler {
    private RelativeLayout rootContainer;
    private RelativeLayout smallVideoView;

    private CallEvent listener;
    private Context mContext;
    private String mTarget;
    private Activity mActivity;
    SurfaceView localVideo;
    SurfaceView remoteVideo;

    private RtcEngine mRtcEngine;

    private volatile boolean mAudioMuted = false;

    public static final int LAYOUT_TYPE_DEFAULT = 0;


    public AgoraExample (Context context) {
        mContext = context;
        Madura.initWorkerThread(context);
        mRtcEngine = worker().getRtcEngine();
    }

    public void setListener(CallEvent listener){
        this.listener = listener;
        event().addEventHandler(this);
    }

    public void setRootContainer(Activity activity, RelativeLayout container, RelativeLayout smallVideo, String encryptionKey, String encryptionMode){
        mActivity = activity;
        doConfigEngine(encryptionKey, encryptionMode);
        rootContainer = container;
        smallVideoView = smallVideo;

        localVideo = mRtcEngine.CreateRendererView(mContext);
        mRtcEngine.setupLocalVideo(new VideoCanvas(localVideo, VideoCanvas.RENDER_MODE_HIDDEN, 0));
        localVideo.setZOrderOnTop(true);
        localVideo.setZOrderMediaOverlay(true);

        rootContainer.addView(localVideo);
        worker().preview(true, localVideo, 0);

        Log.d("amsibsam", "root set");
    }

    private void doConfigEngine(String encryptionKey, String encryptionMode) {
        int vProfile = ConstantApp.VIDEO_PROFILES[getVideoProfileIndex()];
        worker().configEngine(vProfile, encryptionKey, encryptionMode);
    }

    public final WorkerThread worker() {
        return Madura.getWorkerThread();
    }

    public final EngineConfig config() {
        return Madura.getWorkerThread().getEngineConfig();
    }

    protected final MyEngineEventHandler event() {
        return Madura.getWorkerThread().eventHandler();
    }

    @Override
    public void startCall(String target) {
        worker().joinChannel(target, config().mUid);
        mTarget = target;
    }

    @Override
    public void endCall() {
        leaveChannel();
        listener.onConversationEnd();
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        Log.d("amsibsam", "remote video agexample "+uid);
        doRenderRemoteUi(uid);
        listener.onConversationStart();
    }

    private void doRenderRemoteUi(final int uid) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity.isFinishing()) {
                    return;
                }

                remoteVideo = mRtcEngine.CreateRendererView(mActivity);
                remoteVideo.setZOrderOnTop(false);
                remoteVideo.setZOrderMediaOverlay(false);

                mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteVideo, VideoCanvas.RENDER_MODE_HIDDEN, uid));

                rootContainer.removeAllViews();
                smallVideoView.removeAllViews();
                rootContainer.addView(remoteVideo);
                smallVideoView.addView(localVideo);
            }
        });

    }

    public void muteAudio() {
        RtcEngine rtcEngine = mRtcEngine;
        rtcEngine.muteLocalAudioStream(mAudioMuted = !mAudioMuted);
    }

    public void onSwitchCameraClicked() {
        RtcEngine rtcEngine = mRtcEngine;
        rtcEngine.switchCamera();
    }



    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        Log.d("Example", "onJoinChannelSuccess " + channel + " " + (uid & 0xFFFFFFFFL) + " " + elapsed);

        mRtcEngine.muteLocalAudioStream(mAudioMuted);

        mRtcEngine.setEnableSpeakerphone(true);
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        listener.onConversationEnd();
        leaveChannel();
    }

    private void leaveChannel(){
        event().removeEventHandler(this);
        worker().leaveChannel(mTarget);
        worker().preview(false, null, 0);
    }

    @Override
    public void onExtraCallback(int type, Object... data) {

    }



    private int getVideoProfileIndex() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        int profileIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, ConstantApp.DEFAULT_PROFILE_IDX);
        if (profileIndex > ConstantApp.VIDEO_PROFILES.length - 1) {
            profileIndex = ConstantApp.DEFAULT_PROFILE_IDX;

            // save the new value
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, profileIndex);
            editor.apply();
        }
        return profileIndex;
    }

}
