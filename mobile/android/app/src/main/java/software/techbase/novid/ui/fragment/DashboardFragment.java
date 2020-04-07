package software.techbase.novid.ui.fragment;

import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseBottomSheetFragment;
import software.techbase.novid.component.ui.reusable.XReusableViewDashboard;
import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;
import software.techbase.novid.ui.contract.DashboardFragmentContract;
import software.techbase.novid.ui.presenter.DashboardFragmentPresenter;
import software.techbase.novid.util.MMNumberUtil;

/**
 * Created by Wai Yan on 3/30/20.
 */
public class DashboardFragment extends BaseBottomSheetFragment implements DashboardFragmentContract.View {

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

    @Override
    protected void createView() {

        this.loadStats();
    }

    @Override
    protected int getLayoutXmlId() {
        return R.layout.fragment_dashboard;
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
