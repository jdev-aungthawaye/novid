package software.techbase.novid.component.ui.reusable;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import software.techbase.novid.R;

/**
 * Created by Wai Yan on 10/17/18.
 */
public class XAlertDialog {

    public enum Type {
        SUCCESS, ERROR, QUESTION, INFO, WARNING
    }

    private static AlertDialog mDialog;

    public static void show(Context mContext,
                            Type type,
                            @NonNull String message,
                            @Nullable String btnMessage,
                            View.OnClickListener listener) {
        XAlertDialog.show(mContext, type, null, message, btnMessage, listener);
    }

    public static void show(Context mContext,
                            Type type,
                            @Nullable String title,
                            @NonNull String message,
                            @Nullable String btnMessage,
                            View.OnClickListener listener) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_alert, null);

        mDialog = new AlertDialog.Builder(mContext)
                .setView(view)
                .setCancelable(false)
                .create();

        AppCompatImageView imvIcon = view.findViewById(R.id.imvIcon);
        AppCompatTextView lblTitle = view.findViewById(R.id.lblTitle);
        AppCompatTextView lblMessage = view.findViewById(R.id.lblMessage);
        AppCompatButton btnOk = view.findViewById(R.id.btnOk);

        switch (type) {
            case ERROR:
                imvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_solid_error));
                break;

            case SUCCESS:
                imvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_solid_success));
                break;

            case QUESTION:
                imvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_solid_question));
                break;

            case INFO:
                imvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_solid_info));
                break;

            case WARNING:
                imvIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_solid_warning));
                break;
        }

        if (title != null) {
            lblTitle.setVisibility(View.VISIBLE);
            lblTitle.setText(title);
        }
        lblMessage.setText(message);
        if (btnMessage != null) {
            btnOk.setText(btnMessage);
        }

        btnOk.setOnClickListener(v -> {
            XAlertDialog.hide();
            listener.onClick(v);
        });
        mDialog.show();
    }

    public static void hide() {

        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
