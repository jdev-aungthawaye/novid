package software.techbase.novid.ui.fragment;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseBottomSheetFragment;
import software.techbase.novid.ui.activity.SignInActivity;
import software.techbase.novid.ui.activity.SignUpActivity;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class EntryFragment extends BaseBottomSheetFragment {

    @Override
    protected void createView() {
    }

    @Override
    protected int getLayoutXmlId() {
        return R.layout.fragment_entry;
    }

    @OnClick({R.id.btnSignUp, R.id.btnSignIn})
    void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                this.startActivity(new Intent(getActivity(), SignUpActivity.class));
                break;
            case R.id.btnSignIn:
                this.startActivity(new Intent(getActivity(), SignInActivity.class));
                break;
        }
    }
}
