package software.techbase.novid.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import software.techbase.novid.R;
import software.techbase.novid.ui.activity.SignInActivity;
import software.techbase.novid.ui.activity.SignUpActivity;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class EntryFragment extends SuperBottomSheetFragment {

    private static EntryFragment entryFragment;

    private EntryFragment() {
    }

    public static EntryFragment getInstance() {

        if (entryFragment == null) {
            entryFragment = new EntryFragment();
        }
        return entryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_entry, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public float getCornerRadius() {
        return this.getResources().getDimension(R.dimen.vew_corner_large);
    }

    @Override
    public int getPeekHeight() {
        return (int) this.getResources().getDimension(R.dimen.bottom_sheet_height_regular);
    }

    @Override
    public int getStatusBarColor() {
        return Color.BLACK;
    }

    @OnClick({R.id.btnSignUp, R.id.btnSignIn})
    void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                this.startActivity(new Intent(getActivity(), SignUpActivity.class));
                break;
            case R.id.btnSignIn:
                this.startActivity(new Intent(getActivity(), SignInActivity.class));
                break;
        }
    }
}
