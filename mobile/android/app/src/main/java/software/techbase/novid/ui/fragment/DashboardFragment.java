package software.techbase.novid.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.Observable;
import retrofit2.Response;
import software.techbase.novid.R;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;
import software.techbase.novid.domain.remote.client.StatsAPIClient;
import software.techbase.novid.domain.remote.exception.ExceptionsHandling;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class DashboardFragment extends SuperBottomSheetFragment {

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadStatsLocal();
        loadStatsGlobal();
    }

    @Override
    public float getCornerRadius() {
        return this.getResources().getDimension(R.dimen.vew_corner_large_x);
    }

    @Override
    public int getStatusBarColor() {
        return Color.BLACK;
    }

    @SuppressLint("CheckResult")
    private void loadStatsLocal() {

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
                        XLogger.debug(this.getClass(), "Deaths" + response.deaths);
                    }
                }, throwable -> ExceptionsHandling.handleException(getActivity(), throwable));
    }

    @SuppressLint("CheckResult")
    private void loadStatsGlobal() {

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
                        XLogger.debug(this.getClass(), "Deaths" + response.deaths);
                    }
                }, throwable -> ExceptionsHandling.handleException(getActivity(), throwable));
    }
}
