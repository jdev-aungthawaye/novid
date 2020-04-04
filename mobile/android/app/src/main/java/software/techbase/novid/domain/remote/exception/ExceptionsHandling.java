package software.techbase.novid.domain.remote.exception;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import software.techbase.novid.R;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.component.ui.reusable.XProgressDialog;
import software.techbase.novid.component.ui.reusable.XSnackBar;

/**
 * Created by Wai Yan on 12/4/18.
 */
public class ExceptionsHandling {

    public static void handleException(Activity activity, Throwable throwable) {

        XLogger.debug(ExceptionsHandling.class, "HandleException Error : " + Log.getStackTraceString(throwable));

        XProgressDialog.getInstance().hide();

        String message;

        if (throwable instanceof NullPointerException) {

            return;

        } else if (throwable instanceof NoContentException) {

            message = activity.getResources().getString(R.string.MESSAGE_LOCAL__NO_CONTENTS);

        } else if ((throwable instanceof AuthenticationException) || (throwable instanceof UnauthorizedException)) {

            message = activity.getResources().getString(R.string.MESSAGE_LOCAL__UN_AUTHORIZE);

        } else if (throwable instanceof BadRequestException || throwable instanceof ResourceNotFoundException) {

            message = ((RestResponseException) throwable).errorMessage;

        } else if (throwable instanceof UnProcessableException) {

            message = ((RestResponseException) throwable).errorMessage;

        } else if (throwable instanceof NoInternetConnectionException) {

            message = activity.getResources().getString(R.string.MESSAGE_LOCAL__INTERNET_CONNECTION_ERROR);

        } else if (throwable instanceof IOException) {

            message = activity.getResources().getString(R.string.MESSAGE_LOCAL__CONNECT_EXCEPTION);

        } else if (throwable instanceof UnknownException) {

            new AlertDialog.Builder(activity)
                    .setTitle(activity.getResources().getString(R.string.MESSAGE_LOCAL__ERROR))
                    .setMessage(((RestResponseException) throwable).errorMessage)
                    .setCancelable(true)
                    .setPositiveButton("OK", (dialogInterface, which) -> {
                    })
                    .show();

            return;
        } else {
            message = activity.getResources().getString(R.string.MESSAGE_LOCAL__SOMETHING_WRONG);
        }

        XSnackBar.show(
                activity.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_INDEFINITE,
                R.drawable.ic_solid_white_info,
                R.color.x_error,
                R.string.DISMISS, v -> {
                }
        );
    }
}
