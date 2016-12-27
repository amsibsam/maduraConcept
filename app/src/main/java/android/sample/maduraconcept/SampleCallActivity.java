package android.sample.maduraconcept;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.madura.AgoraSampleReferences.model.ConstantApp;
import android.madura.Madura;
import android.madura.event.CallEvent;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SampleCallActivity extends AppCompatActivity implements CallEvent {
    private RelativeLayout rootContainer, smallVideoView;
    private TextView callDuration;
    private ImageView btnMute;
    private boolean shouldContinueTimer = false;
    private long callDurationInSeconds;
    private long startTime;
    final Handler handler = new Handler();
    private boolean mAudioMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_call);
        targetView(); //target xml view
        initMaduraSdk(); //init madura
        setButtonResponse(); //set button onclick
    }

    @Override
    public void onConversationStart() {
        Log.d("amsibsam", "conversation start");
        startTime = System.currentTimeMillis();
        startTimer();
//        TODO: do something when conversation start
    }

    @Override
    public void onConversationEnd() {
        finish();
        shouldContinueTimer = false;
    }

    @Override
    public void onCallFailed() {
//        TODO: notify user (callback not implemented yet)
    }

    private void targetView() {
        rootContainer = (RelativeLayout) findViewById(R.id.root_container);
        smallVideoView = (RelativeLayout) findViewById(R.id.small_video_container);
        callDuration = (TextView) findViewById(R.id.tv_duration);
        btnMute = (ImageView) findViewById(R.id.btn_mute);
    }

    private void initMaduraSdk() {
        String target = getIntent().getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);
        final String encryptionKey = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY);
        final String encryptionMode = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE);

        Madura.setListener(this); //set call listener

        Madura.setRootLayout(this, rootContainer, smallVideoView,
                encryptionKey, encryptionMode); //set video render view

        Madura.startCall(target); //start call
    }

    private void setButtonResponse() {
        findViewById(R.id.bottom_action_end_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Madura.endCall();
                shouldContinueTimer = false;
                finish();
            }
        });

        btnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Madura.muteAudio();

                if (mAudioMuted) {
                    btnMute.clearColorFilter();
                    mAudioMuted = false;
                } else {
                    mAudioMuted = true;
                    btnMute.setColorFilter(getResources().getColor(R.color.agora_blue), PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        findViewById(R.id.customized_function_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Madura.swithcCamera();
            }
        });
    }

    private void startTimer() {
        Log.d("amsibsam", "start timer");
        shouldContinueTimer = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                callDurationInSeconds = (currentTime - startTime) / 1000L;
                String formattedDuration = DateUtils.formatElapsedTime(callDurationInSeconds);
                callDuration.setText(formattedDuration);
                if (shouldContinueTimer) {
                    handler.postDelayed(this, 1000L);
                }
            }
        }, 1000L);
    }
}
