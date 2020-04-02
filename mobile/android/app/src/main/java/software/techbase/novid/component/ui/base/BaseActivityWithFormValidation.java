package software.techbase.novid.component.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mobsandgeeks.saripaar.Validator;

/**
 * Created by Wai Yan on 2019-12-24.
 */
public abstract class BaseActivityWithFormValidation extends BaseActivityWithNavigation implements Validator.ValidationListener {

    protected Validator inputValidator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inputValidator = new Validator(this);
        this.inputValidator.setValidationListener(this);
    }
}
