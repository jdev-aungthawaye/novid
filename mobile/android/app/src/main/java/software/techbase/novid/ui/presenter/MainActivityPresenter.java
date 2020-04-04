package software.techbase.novid.ui.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import software.techbase.novid.domain.remote.api.GetContacts;
import software.techbase.novid.domain.remote.client.DataAPIClient;
import software.techbase.novid.domain.remote.exception.ExceptionsHandling;
import software.techbase.novid.ui.contract.MainActivityContract;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadContacts(Activity activity) {

        Observable<Response<List<GetContacts.Response>>> responseObservable = DataAPIClient.getContacts();
        responseObservable
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        List<GetContacts.Response> response = httpResponse.body();
                        view.showContacts(response);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));
    }
}
