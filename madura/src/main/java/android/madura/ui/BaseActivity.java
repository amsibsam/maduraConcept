package android.madura.ui;

import android.madura.R;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by rahardyan on 27/12/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    protected static final int RC_PERMISSIONS = 1;

    private static final String[] PERMISSIONS = {
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.RECORD_AUDIO",
            "android.permission.RECORD_AUDIO",
            "android.permission.CAMERA",
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestPermissions();
    }

    protected void requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            EasyPermissions.requestPermissions(this, "Please grant permissions to make apps working properly!",
                    RC_PERMISSIONS, PERMISSIONS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
