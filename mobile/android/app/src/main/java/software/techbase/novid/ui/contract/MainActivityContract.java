package software.techbase.novid.ui.contract;

import android.app.Activity;

import java.util.List;

import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 4/4/20.
 */
public interface MainActivityContract {

    interface View {

        void showContacts(List<GetContacts.Response> contacts);
    }

    interface Presenter {

        void loadContacts(Activity activity);
    }
}
