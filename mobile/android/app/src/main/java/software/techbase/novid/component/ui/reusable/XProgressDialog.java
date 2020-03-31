package software.techbase.novid.component.ui.reusable;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import java.util.Objects;

import software.techbase.novid.R;


/**
 * Created by Wai Yan on 10/17/18.
 */
public class XProgressDialog {

    private static XProgressDialog xProgressDialog;
    private Dialog mDialog;

    private XProgressDialog() {
    }

    public static XProgressDialog getInstance() {

        if (xProgressDialog == null) {
            xProgressDialog = new XProgressDialog();
        }
        return xProgressDialog;
    }

    public void show(Context mContext) {

        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_progress);

        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void hide() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
