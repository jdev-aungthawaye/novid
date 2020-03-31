package software.techbase.novid.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseActivityWithNavigation;

public class SignInActivity extends BaseActivityWithNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public String setScreenTitle() {
        return getString(R.string.string_activity_sign_in__title);
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_sign_in;
    }

    @OnClick(R.id.btnSignIn)
    public void onClickSignIn() {
        this.startActivity(new Intent(this, OTPConfirmActivity.class));
    }
}
