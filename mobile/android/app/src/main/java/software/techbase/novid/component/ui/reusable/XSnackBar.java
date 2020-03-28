package software.techbase.novid.component.ui.reusable;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

import software.techbase.novid.R;

public class XSnackBar {

    private static Snackbar snackbar;

    public static void show(View view,
                            @NonNull String message,
                            int duration,
                            @DrawableRes int iconResId,
                            @ColorRes int backgroundColorResId,
                            @StringRes int actionMessage) {

        snackbar = Snackbar.make(view, message, duration);
        if (actionMessage != 0) {
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction(actionMessage, v -> {
                snackbar.dismiss();
                snackbar = null;
            });
        }
        TextView textView = snackbar.getView().findViewById(R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        textView.setCompoundDrawablePadding(16);
        snackbar.getView().setBackgroundResource(backgroundColorResId);
        snackbar.show();
    }

    public static void show(View view,
                            @StringRes int message,
                            int duration,
                            @DrawableRes int iconResId,
                            @ColorRes int backgroundColorResId,
                            @StringRes int actionMessage) {

        snackbar = Snackbar.make(view, message, duration);
        if (actionMessage != 0) {
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.setAction(actionMessage, v -> {
                snackbar.dismiss();
                snackbar = null;
            });
        }
        TextView textView = snackbar.getView().findViewById(R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        textView.setCompoundDrawablePadding(16);
        snackbar.getView().setBackgroundResource(backgroundColorResId);
        snackbar.show();
    }

    public static void hide() {
        if (snackbar != null) {
            snackbar.dismiss();
            snackbar = null;
        }
    }
}
