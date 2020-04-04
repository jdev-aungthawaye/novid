package software.techbase.novid.domain.remote.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import software.techbase.novid.domain.remote.domain.DataAPIServiceContext;
import software.techbase.novid.domain.remote.domain.RestInvoker;
import software.techbase.novid.domain.remote.service.DataAPIService;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class GetContacts extends RestInvoker<DataAPIService, GetContacts.Request, List<GetContacts.Response>> {

    public GetContacts() {
        super(DataAPIServiceContext.getAPIService());
    }

    @Override
    protected Call<List<Response>> call(Request request) {
        return getService().getContacts(request.fileName);
    }

    public static class Request implements Serializable {

        @SerializedName("fileName")
        public String fileName;
    }

    public static class Response implements Serializable {

        @SerializedName("township")
        public String township;

        @SerializedName("industry")
        public String industry;

        @SerializedName("region")
        public String region;

        @SerializedName("sr_pcode")
        public String srPCode;

        @SerializedName("tsp_pcode")
        public String tspPCode;

        @SerializedName("charge")
        public String charge;

        @SerializedName("department")
        public String department;

        @SerializedName("others_information")
        public String othersInformation;

        @SerializedName("phone1")
        public String phone1;

        @SerializedName("phone2")
        public String phone2;

        @SerializedName("lng")
        public double lng;

        @SerializedName("lat")
        public double lat;
    }
}
