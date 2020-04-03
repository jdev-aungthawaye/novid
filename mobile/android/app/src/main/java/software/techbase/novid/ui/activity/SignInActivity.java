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
import software.techbase.novid.ui.contract.SignInActivityContract;
import software.techbase.novid.ui.presenter.SignInActivityPresenter;
import software.techbase.novid.util.InputValidationUtil;
import software.techbase.novid.util.XTransitionUtil;

public class SignInActivity extends BaseActivityWithFormValidation implements SignInActivityContract.View {

    @Pattern(regex = "^(09|9)\\d{7,12}$", messageResId = R.string.string_activity_sign_up__invalid_phone_number)
    @BindView(R.id.edtPhoneNumber)
    XAppCompatEditText edtPhoneNumber;

    private final int REQUEST_CODE = 1996;
    private String phoneNumber;
    private final SignInActivityPresenter presenter = new SignInActivityPresenter(this);

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
        this.inputValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        this.phoneNumber = Objects.requireNonNull(edtPhoneNumber.getText()).toString();
        this.presenter.requestSignIn(this, phoneNumber);
        XProgressDialog.getInstance().show(this);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        InputValidationUtil.showErrors(this, errors);
    }

    @Override
    public void requestSignInStatus(boolean isSuccessful) {

        XProgressDialog.getInstance().hide();
        XTransitionUtil.showNextActivityForResult(this,
                OTPConfirmActivity.class,
                null,
                0,
                0,
                REQUEST_CODE);
    }

    @Override
    public void doSignInOK(long userId, String accessToken) {

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

                presenter.doSignIn(this, this.phoneNumber, otp);
                XProgressDialog.getInstance().show(this);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                XLogger.debug(this.getClass(), "OTP not filled.");
            }
        }
    }
}
