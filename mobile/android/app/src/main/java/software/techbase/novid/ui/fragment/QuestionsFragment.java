package software.techbase.novid.ui.fragment;

import android.annotation.SuppressLint;
import android.webkit.WebView;

import butterknife.BindView;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseBottomSheetFragment;

public class QuestionsFragment extends BaseBottomSheetFragment {

    @BindView(R.id.wv)
    WebView wv;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void createView() {

        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:///android_asset/data/covid19-questions.html");
    }

    @Override
    protected int getLayoutXmlId() {
        return R.layout.fragment_questions;
    }
}
