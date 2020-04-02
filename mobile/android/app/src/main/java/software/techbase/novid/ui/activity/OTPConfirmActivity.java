package software.techbase.novid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseActivityWithFormValidation;
import software.techbase.novid.component.ui.reusable.XAppCompatEditText;
import software.techbase.novid.foundation.constants.ParameterKeys;
import software.techbase.novid.util.InputValidationUtil;

public class OTPConfirmActivity extends BaseActivityWithFormValidation {

    @Pattern(regex = "^\\d{6}$", messageResId = R.string.string_activity_confirm__otp_error)
    @BindView(R.id.edtOTP)
    XAppCompatEditText edtOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public String setScreenTitle() {
        return getString(R.string.string_activity_confirm__title);
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_otp_confirm;
    }

    @OnClick(R.id.btnConfirm)
    public void onClickBtnConfirm() {
        super.inputValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ParameterKeys.OTP_KEY, Objects.requireNonNull(edtOTP.getText()).toString());
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        InputValidationUtil.showErrors(this, errors);
    }
}
