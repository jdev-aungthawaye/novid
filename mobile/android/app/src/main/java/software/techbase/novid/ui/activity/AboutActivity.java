package software.techbase.novid.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseActivityWithNavigation;

public class AboutActivity extends BaseActivityWithNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_about;
    }

    @NonNull
    @Override
    public String setScreenTitle() {
        return "About";
    }

    @OnClick(R.id.xrvGithub)
    public void onClickGithub() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/es-aungthawaye/novid")));
    }

    @OnClick(R.id.xrvQuestions)
    public void onClickQuestions() {
        startActivity(new Intent(this, QuestionsActivity.class));
    }
}
