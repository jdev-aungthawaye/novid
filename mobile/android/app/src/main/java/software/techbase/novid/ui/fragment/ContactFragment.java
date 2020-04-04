package software.techbase.novid.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.techbase.novid.R;
import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class ContactFragment extends SuperBottomSheetFragment {

    @BindView(R.id.lblTownship)
    AppCompatTextView lblTownship;

    @BindView(R.id.lblIndustry)
    AppCompatTextView lblIndustry;

    @BindView(R.id.lblRegion)
    AppCompatTextView lblRegion;

    @BindView(R.id.lblCharge)
    AppCompatTextView lblCharge;

    @BindView(R.id.lblOtherInformation)
    AppCompatTextView lblOtherInformation;

    @BindView(R.id.lblPhone1)
    AppCompatTextView lblPhone1;

    @BindView(R.id.lblPhone2)
    AppCompatTextView lblPhone2;

    @BindView(R.id.lblSRPCode)
    AppCompatTextView lblSRPCode;

    @BindView(R.id.lblTSPPcode)
    AppCompatTextView lblTSPPcode;

    private static ContactFragment contactFragment;

    private ContactFragment() {
    }


    public static ContactFragment getInstance() {

        if (contactFragment == null) {
            contactFragment = new ContactFragment();
        }
        return contactFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public float getCornerRadius() {
        return this.getResources().getDimension(R.dimen.vew_corner_large);
    }

    @Override
    public int getStatusBarColor() {
        return Color.BLACK;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        GetContacts.Response contact = (GetContacts.Response) bundle.getSerializable("contact");

        assert contact != null;
        lblTownship.setText(contact.township);
        lblIndustry.setText(contact.industry);
        lblRegion.setText(contact.region);
        lblCharge.setText(contact.charge);
        lblOtherInformation.setText(contact.othersInformation);
        lblPhone1.setText(contact.phone1);
        lblPhone2.setText(contact.phone2);
        lblSRPCode.setText(contact.srPCode);
        lblTSPPcode.setText(contact.tspPCode);
    }
}
