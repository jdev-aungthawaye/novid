package software.techbase.novid.ui.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import io.reactivex.Observable;
import retrofit2.Response;
import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;
import software.techbase.novid.domain.remote.client.StatsAPIClient;
import software.techbase.novid.domain.remote.exception.ExceptionsHandling;
import software.techbase.novid.ui.contract.DashboardFragmentContract;

/**
 * Created by Wai Yan on 3/31/20.
 */
@SuppressLint("CheckResult")
public class DashboardFragmentPresenter implements DashboardFragmentContract.Presenter {

    private DashboardFragmentContract.View view;

    public DashboardFragmentPresenter(DashboardFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void getLocalStats(Activity activity) {

        Observable<Response<GetStatsByCountry.Response>> responseObservable = StatsAPIClient.getStatsByCountry("myanmar");
        responseObservable
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        GetStatsByCountry.Response response = httpResponse.body();
                        view.showLocalStats(response);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));
    }

    @Override
    public void getGlobalStats(Activity activity) {

        Observable<Response<GetStatsGlobal.Response>> responseObservable = StatsAPIClient.getStatsGlobal();
        responseObservable
                .doOnSubscribe(disposable -> {
                })
                .doOnTerminate(() -> {
                })
                .doOnError(throwable -> {
                })
                .subscribe(httpResponse -> {
                    if (httpResponse.isSuccessful()) {
                        GetStatsGlobal.Response response = httpResponse.body();
                        view.showGlobalStats(response);
                    }
                }, throwable -> ExceptionsHandling.handleException(activity, throwable));
    }
}
