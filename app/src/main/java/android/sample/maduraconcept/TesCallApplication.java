package android.sample.maduraconcept;

import android.app.Application;
import android.madura.Madura;

/**
 * Created by rahardyan on 17/12/16.
 */

public class TesCallApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Madura.init(getBaseContext());
    }
}
