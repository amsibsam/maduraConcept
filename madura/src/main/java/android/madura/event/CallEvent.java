package android.madura.event;

/**
 * Created by rahardyan on 17/12/16.
 */

public interface CallEvent {
    void onConversationStart();
    void onConversationEnd();
    void onCallFailed();
}
