package software.techbase.novid.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseActivityWithNavigation;

public class QuestionsActivity extends BaseActivityWithNavigation {

    @BindView(R.id.wv)
    WebView wv;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swipeContainer.post(() -> {
            swipeContainer.setRefreshing(true);
            this.showData();
        });
        swipeContainer.setOnRefreshListener(this::showData);
    }

    @Override
    protected int getLayoutFileId() {
        return R.layout.activity_questions;
    }

    @NonNull
    @Override
    public String setScreenTitle() {
        return "Questions";
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showData() {

        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:///android_asset/data/covid19-questions.html");
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }
}
