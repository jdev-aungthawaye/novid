package software.techbase.novid.ui.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import software.techbase.novid.domain.remote.api.DoSignIn;
import software.techbase.novid.domain.remote.api.RequestSignIn;
import software.techbase.novid.domain.remote.exception.ExceptionsHandling;
import software.techbase.novid.ui.contract.SignInActivityContract;

/**
 * Created by Wai Yan on 4/2/20.
 */
@SuppressLint("CheckResult")
public class SignInActivityPresenter implements SignInActivityContract.Presenter {

    private SignInActivityContract.View view;

    public SignInActivityPresenter(SignInActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void requestSignIn(Activity activity, String phoneNumber) {

        RequestSignIn.Request request = new RequestSignIn.Request();
        request.mobile = phoneNumber;

        new RequestSignIn()
                .invoke(request)
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        view.requestSignInStatus(true);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));
    }

    @Override
    public void doSignIn(Activity activity, String phoneNumber, String code) {

        DoSignIn.Request request = new DoSignIn.Request();
        request.mobile = phoneNumber;
        request.code = code;

        new DoSignIn()
                .invoke(request)
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        DoSignIn.Response response = httpResponse.body();
                        assert response != null;
                        view.doSignInOK(response.userId, response.token);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));

    }
}
