package software.techbase.novid.component.ui.reusable;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.techbase.novid.R;

/**
 * AUTHOR       : Wai Yan
 * DATE         : 2018/09/02
 */
public class XAlertDialog extends AppCompatDialog {

    public enum Type {
        SUCCESS, ERROR, QUESTION, INFO, WARNING
    }

    @BindView(R.id.imvIcon)
    AppCompatImageView imvIcon;

    @BindView(R.id.lblTitle)
    AppCompatTextView lblTitle;

    @BindView(R.id.lblMessage)
    AppCompatTextView lblMessage;

    @BindView(R.id.btnOk)
    AppCompatButton btnOk;

    private Type type;
    private String title;
    private String message;
    private String btnMessage;
    private View.OnClickListener listener;

    public XAlertDialog(Context context,
                        Type type,
                        @NonNull String message,
                        @Nullable String btnMessage,
                        View.OnClickListener listener) {

        this(context, type, null, message, btnMessage, listener);
    }

    public XAlertDialog(Context context,
                        Type type,
                        @Nullable String title,
                        @NonNull String message,
                        @Nullable String btnMessage,
                        View.OnClickListener listener) {

        super(context);
        this.type = type;
        this.title = title;
        this.message = message;
        this.btnMessage = btnMessage;
        this.listener = listener;
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_alert);
        ButterKnife.bind(this);

        switch (this.type) {
            case ERROR:
                this.imvIcon.setImageDrawable(
                        ContextCompat.getDrawable(this.getContext(), R.drawable.ic_solid_error));
                break;

            case SUCCESS:
                this.imvIcon.setImageDrawable(ContextCompat.getDrawable(
                        this.getContext(),
                        R.drawable.ic_solid_success));
                break;

            case QUESTION:
                this.imvIcon.setImageDrawable(ContextCompat.getDrawable(
                        this.getContext(),
                        R.drawable.ic_solid_question));
                break;

            case INFO:
                this.imvIcon.setImageDrawable(ContextCompat.getDrawable(
                        this.getContext(), R.drawable.ic_solid_info));
                break;

            case WARNING:
                this.imvIcon.setImageDrawable(ContextCompat.getDrawable(
                        this.getContext(), R.drawable.ic_solid_warning));
                break;
        }

        if (this.title != null) {
            this.lblTitle.setVisibility(View.VISIBLE);
            this.lblTitle.setText(this.title);
        }

        this.lblMessage.setText(this.message);

        if (this.btnMessage != null) {
            this.btnOk.setText(this.btnMessage);
        }

        this.btnOk.setOnClickListener(v -> {
            this.dismiss();
            this.listener.onClick(v);
        });
    }
}
