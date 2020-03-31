package software.techbase.novid.ui.contract;

import android.app.Activity;

import software.techbase.novid.domain.remote.api.GetStatsByCountry;
import software.techbase.novid.domain.remote.api.GetStatsGlobal;

/**
 * Created by Wai Yan on 3/31/20.
 */
public interface DashboardFragmentContract {

    interface View {

        void showLocalStats(GetStatsByCountry.Response response);

        void showGlobalStats(GetStatsGlobal.Response response);
    }

    interface Presenter {

        void getLocalStats(Activity activity);

        void getGlobalStats(Activity activity);
    }
}
