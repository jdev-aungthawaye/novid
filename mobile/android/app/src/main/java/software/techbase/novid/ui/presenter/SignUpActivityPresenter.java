package software.techbase.novid.ui.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import software.techbase.novid.domain.remote.api.RequestVerification;
import software.techbase.novid.domain.remote.api.SignUp;
import software.techbase.novid.domain.remote.exception.ExceptionsHandling;
import software.techbase.novid.ui.contract.SignUpActivityContract;

/**
 * Created by Wai Yan on 4/1/20.
 */
@SuppressLint("CheckResult")
public class SignUpActivityPresenter implements SignUpActivityContract.Presenter {

    private SignUpActivityContract.View view;

    public SignUpActivityPresenter(SignUpActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void requestVerification(Activity activity, String phoneNumber) {

        RequestVerification.Request request = new RequestVerification.Request();
        request.mobile = phoneNumber;

        new RequestVerification()
                .invoke(request)
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        view.requestVerificationStatus(true);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));
    }

    @Override
    public void signUp(Activity activity,
                       String fullName,
                       String nric,
                       String phoneNumber,
                       String code) {

        SignUp.Request request = new SignUp.Request();
        request.name = fullName;
        request.nric = nric;
        request.mobile = phoneNumber;
        request.code = code;

        new SignUp()
                .invoke(request)
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        SignUp.Response response = httpResponse.body();
                        assert response != null;
                        view.signUpOK(response.userId, response.token);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));
    }
}
