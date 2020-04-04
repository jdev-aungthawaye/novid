package software.techbase.novid.domain.remote.domain;

import software.techbase.novid.domain.remote.constant.APIEndPoint;
import software.techbase.novid.domain.remote.service.DataAPIService;

/**
 * Created by Wai Yan on 2019-07-25.
 */
public class DataAPIServiceContext {

    private static DataAPIService dataAPIService;

    static {
        dataAPIService = (new RetrofitService<>(DataAPIService.class, APIEndPoint.DATA_ENDPOINT)).getService();
    }

    public static DataAPIService getAPIService() {
        return dataAPIService;
    }
}
