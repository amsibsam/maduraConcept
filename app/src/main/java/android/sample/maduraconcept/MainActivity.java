package android.sample.maduraconcept;

import android.content.Intent;
import android.madura.AgoraSampleReferences.model.ConstantApp;
import android.madura.ui.SampleCallActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.test_agora).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SampleCallActivity.class)
                        .putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, "channelDap"));
            }
        });
    }

}
