package software.techbase.novid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseActivity;

public class EntryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_entry;
    }

    @OnClick({R.id.btnSignUp, R.id.btnSignIn})
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                this.startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.btnSignIn:
                this.startActivity(new Intent(this, SignInActivity.class));
                break;
        }
    }
}
