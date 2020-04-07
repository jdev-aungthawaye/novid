package software.techbase.novid.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseBottomSheetFragment;
import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 4/4/20.
 */
public class ContactDetailFragment extends BaseBottomSheetFragment {

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

    @Override
    protected void createView() {

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

    @Override
    protected int getLayoutXmlId() {
        return R.layout.fragment_contact_detail;
    }
}
