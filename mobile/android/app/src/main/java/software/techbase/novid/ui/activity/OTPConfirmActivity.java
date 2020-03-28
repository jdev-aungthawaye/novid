package software.techbase.novid.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseActivityWithNavigation;

public class OTPConfirmActivity extends BaseActivityWithNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public String setScreenTitle() {
        return "OTP Confirm";
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_otp_confirm;
    }

    @OnClick(R.id.btnConfirm)
    public void onClickBtnConfirm() {
        this.startActivity(new Intent(this, MainActivity.class));
    }
}
