package software.techbase.novid.component.ui.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import software.techbase.novid.R;

/**
 * Created by Wai Yan on 12/17/18.
 */
public abstract class BaseActivityWithNavigation extends BaseActivity {

    @BindView(R.id.iBtnBack)
    AppCompatImageButton iBtnBack;

    @BindView(R.id.lblScreenTitle)
    protected AppCompatTextView lblScreenTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lblScreenTitle.setText(this.setScreenTitle());

        iBtnBack.setOnClickListener(view -> this.onBackPressed());
    }

    public abstract @NonNull
    String setScreenTitle();
}
