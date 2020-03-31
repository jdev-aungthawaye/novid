package software.techbase.novid.ui.contract;

import android.app.Activity;

/**
 * Created by Wai Yan on 4/1/20.
 */
public interface SignUpActivityContract {

    interface View {

        void signUpOK(long userId, String accessToken);

        void requestVerificationStatus(boolean isSuccessful);
    }

    interface Presenter {

        void requestVerification(Activity activity, String phoneNumber);

        void signUp(Activity activity,
                    String fullName,
                    String nric,
                    String phoneNumber,
                    String code);
    }
}
