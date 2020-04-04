package software.techbase.novid.domain.remote.client;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class DataAPIClient {

    public static Observable<Response<List<GetContacts.Response>>> getContacts() {

        GetContacts.Request request = new GetContacts.Request();
        request.fileName = "contacts.json";

        return new GetContacts().invoke(request);
    }
}
