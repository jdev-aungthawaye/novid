package software.techbase.novid.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.ui.base.BaseActivityWithNavigation;
import software.techbase.novid.component.ui.reusable.XAppCompatEditText;
import software.techbase.novid.component.ui.reusable.XProgressDialog;
import software.techbase.novid.ui.contract.SignUpActivityContract;
import software.techbase.novid.ui.presenter.SignUpActivityPresenter;
import software.techbase.novid.util.MMPhoneNumberFormatter;

public class SignUpActivity extends BaseActivityWithNavigation implements SignUpActivityContract.View {

    @BindView(R.id.edtFullName)
    XAppCompatEditText edtFullName;

    @BindView(R.id.edtNRIC)
    XAppCompatEditText edtNRIC;

    @BindView(R.id.edtPhoneNumber)
    XAppCompatEditText edtPhoneNumber;

    private final SignUpActivityPresenter presenter = new SignUpActivityPresenter(this);

    private String fullName;
    private String nric;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public String setScreenTitle() {
        return getString(R.string.string_activity_sign_up__title);
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_sign_up;
    }

    @OnClick(R.id.btnSignUp)
    public void onClickBtnSignUp() {
        this.startActivity(new Intent(this, OTPConfirmActivity.class));
    }

    @OnClick(R.id.btnSignUp)
    public void onClickSignUp() {

        this.fullName = Objects.requireNonNull(edtFullName.getText()).toString();
        this.nric = Objects.requireNonNull(edtNRIC.getText()).toString();
        this.phoneNumber = MMPhoneNumberFormatter.format(Objects.requireNonNull(edtPhoneNumber.getText()).toString());

        if (phoneNumber.isEmpty()) {
            edtPhoneNumber.setError("Please fill phone number");
        } else {
            presenter.requestVerification(this, phoneNumber);
            XProgressDialog.getInstance().show(this);
        }
    }

    @Override
    public void requestVerificationStatus(boolean isSuccessful) {

        XProgressDialog.getInstance().hide();
        //OTP GO
    }

    @Override
    public void signUpOK(long userId, String accessToken) {

        XProgressDialog.getInstance().hide();
        UserInfoStorage.getInstance().setUserId(userId);
        UserInfoStorage.getInstance().setAccessToken(accessToken);
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

}
