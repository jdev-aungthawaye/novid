package software.techbase.novid.component.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;

/**
 * Created by Wai Yan on 4/7/20.
 */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment {

    private View fragmentView = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (null == this.fragmentView) {
            this.fragmentView = inflater.inflate(this.getLayoutXmlId(), container, false);
            ButterKnife.bind(this, this.fragmentView);
            this.createView();
        }
        return this.fragmentView;
    }

    protected abstract void createView();

    protected abstract int getLayoutXmlId();
}
