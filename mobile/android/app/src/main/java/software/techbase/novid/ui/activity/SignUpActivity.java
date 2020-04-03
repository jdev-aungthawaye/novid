package software.techbase.novid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.cache.sharepreferences.UserInfoStorage;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.component.ui.base.BaseActivityWithFormValidation;
import software.techbase.novid.component.ui.reusable.XAppCompatEditText;
import software.techbase.novid.component.ui.reusable.XProgressDialog;
import software.techbase.novid.foundation.constants.ParameterKeys;
import software.techbase.novid.ui.contract.SignUpActivityContract;
import software.techbase.novid.ui.presenter.SignUpActivityPresenter;
import software.techbase.novid.util.InputValidationUtil;
import software.techbase.novid.util.XTransitionUtil;

public class SignUpActivity extends BaseActivityWithFormValidation implements SignUpActivityContract.View {

    @BindView(R.id.edtFullName)
    XAppCompatEditText edtFullName;

    @BindView(R.id.edtNRIC)
    XAppCompatEditText edtNRIC;

    @Pattern(regex = "^(09|9)\\d{7,12}$", messageResId = R.string.string_activity_sign_up__invalid_phone_number)
    @BindView(R.id.edtPhoneNumber)
    XAppCompatEditText edtPhoneNumber;

    private final int REQUEST_CODE = 1996;
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
        super.inputValidator.validate();
    }

    @Override
    public void requestVerificationStatus(boolean isSuccessful) {

        XProgressDialog.getInstance().hide();
        XTransitionUtil.showNextActivityForResult(this,
                OTPConfirmActivity.class,
                null,
                0,
                0,
                REQUEST_CODE);
    }

    @Override
    public void signUpOK(long userId, String accessToken) {

        UserInfoStorage.getInstance().setUserId(userId);
        UserInfoStorage.getInstance().setAccessToken(accessToken);

        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                assert data != null;
                String otp = data.getStringExtra(ParameterKeys.OTP_KEY);
                assert otp != null;

                if (this.fullName.isEmpty()) {
                    this.fullName = "UNKNOWN";
                }

                if (this.nric.isEmpty()) {
                    this.nric = "UNKNOWN";
                }

                presenter.signUp(this, this.fullName, this.nric, this.phoneNumber, otp);
                XProgressDialog.getInstance().show(this);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                XLogger.debug(this.getClass(), "OTP not filled.");
            }
        }
    }

    @Override
    public void onValidationSucceeded() {

        this.fullName = Objects.requireNonNull(edtFullName.getText()).toString();
        this.nric = Objects.requireNonNull(edtNRIC.getText()).toString();
        this.phoneNumber = Objects.requireNonNull(edtPhoneNumber.getText()).toString();

        presenter.requestVerification(this, phoneNumber);
        XProgressDialog.getInstance().show(this);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        InputValidationUtil.showErrors(this, errors);
    }
}
