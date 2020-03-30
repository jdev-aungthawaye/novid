package software.techbase.novid.domain.remote.domain;

import software.techbase.novid.domain.remote.constant.APIEndPoint;
import software.techbase.novid.domain.remote.service.StatsAPIService;

/**
 * Created by Wai Yan on 2019-07-25.
 */
public class StatsAPIServiceContext {

    private static StatsAPIService statsAPIService;

    static {
        statsAPIService = (new RetrofitService<>(StatsAPIService.class, APIEndPoint.STATS_API_ENDPOINT)).getService();
    }

    public static StatsAPIService getAPIService() {
        return statsAPIService;
    }
}
