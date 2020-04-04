package software.techbase.novid.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.reusable.XReusableViewDashboard;
import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;
import software.techbase.novid.ui.contract.DashboardFragmentContract;
import software.techbase.novid.ui.presenter.DashboardFragmentPresenter;
import software.techbase.novid.util.MMNumberUtil;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class DashboardFragment extends SuperBottomSheetFragment implements DashboardFragmentContract.View {

    private final DashboardFragmentPresenter presenter = new DashboardFragmentPresenter(this);

    @BindView(R.id.xRVDFoundLocal)
    XReusableViewDashboard xRVDFoundLocal;

    @BindView(R.id.xRVDDeathLocal)
    XReusableViewDashboard xRVDDeathLocal;

    @BindView(R.id.xRVDRecoveredLocal)
    XReusableViewDashboard xRVDRecoveredLocal;

    @BindView(R.id.xRVDFoundGlobal)
    XReusableViewDashboard xRVDFoundGlobal;

    @BindView(R.id.xRVDDeathGlobal)
    XReusableViewDashboard xRVDDeathGlobal;

    @BindView(R.id.xRVDRecoveredGlobal)
    XReusableViewDashboard xRVDRecoveredGlobal;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;


    private static DashboardFragment dashboardFragment;

    private DashboardFragment() {
    }

    public static DashboardFragment getInstance() {

        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
        }
        return dashboardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loadStats();
    }

    @Override
    public float getCornerRadius() {
        return this.getResources().getDimension(R.dimen.vew_corner_large);
    }

    @Override
    public int getStatusBarColor() {
        return Color.BLACK;
    }

    private void loadStats() {

        pbLoading.setVisibility(View.VISIBLE);

        presenter.getLocalStats(getActivity());
        presenter.getGlobalStats(getActivity());
    }

    @Override
    public void showLocalStats(GetStatsByCountry.Response response) {

        pbLoading.setVisibility(View.GONE);

        xRVDFoundLocal.setCounter(MMNumberUtil.convert(String.valueOf(response.cases)));
        xRVDDeathLocal.setCounter(MMNumberUtil.convert(String.valueOf(response.deaths)));
        xRVDRecoveredLocal.setCounter(MMNumberUtil.convert(String.valueOf(response.recovered)));
    }

    @Override
    public void showGlobalStats(GetStatsGlobal.Response response) {

        pbLoading.setVisibility(View.GONE);

        xRVDFoundGlobal.setCounter(MMNumberUtil.convert(String.valueOf(response.cases)));
        xRVDDeathGlobal.setCounter(MMNumberUtil.convert(String.valueOf(response.deaths)));
        xRVDRecoveredGlobal.setCounter(MMNumberUtil.convert(String.valueOf(response.recovered)));
    }
}
