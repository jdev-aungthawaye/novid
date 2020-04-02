package software.techbase.novid.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Created by Wai Yan on 11/30/18.
 */
public final class InputValidationUtil {

    public static void showErrors(Context context, List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }
}
