package software.techbase.novid.domain.remote.domain;

import software.techbase.novid.domain.remote.constant.APIEndPoint;
import software.techbase.novid.domain.remote.service.XApplicationAPIService;

/**
 * Created by Wai Yan on 2019-07-25.
 */
public class XApplicationAPIServiceContext {

    private static XApplicationAPIService XApplicationService;

    static {
        XApplicationService = (new RetrofitService<>(XApplicationAPIService.class, APIEndPoint.X_APPLICATION_API_ENDPOINT)).getService();
    }

    public static XApplicationAPIService getAPIService() {
        return XApplicationService;
    }
}
