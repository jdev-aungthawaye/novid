package software.techbase.novid.ui.contract;

import android.app.Activity;

/**
 * Created by Wai Yan on 4/2/20.
 */
public interface SignInActivityContract {

    interface View {

        void requestSignInStatus(boolean isSuccessful);

        void doSignInOK(long userId, String accessToken);
    }

    interface Presenter {

        void requestSignIn(Activity activity, String phoneNumber);

        void doSignIn(Activity activity, String phoneNumber, String code);
    }
}
