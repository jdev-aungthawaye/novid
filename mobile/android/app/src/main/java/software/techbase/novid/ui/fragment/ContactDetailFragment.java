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

    @Override
    protected void createView() {

        Bundle bundle = getArguments();
        assert bundle != null;
        GetContacts.Response contact = (GetContacts.Response) bundle.getSerializable("contact");

        if (contact != null) {
            if (contact.township != null) lblTownship.setText(contact.township);
            if (contact.industry != null) lblIndustry.setText(contact.industry);
            if (contact.region != null) lblRegion.setText(contact.region);
            if (contact.charge != null) lblCharge.setText(contact.charge);
            if (contact.othersInformation != null) lblOtherInformation.setText(contact.othersInformation);
            if (contact.phone1 != null) lblPhone1.setText(contact.phone1);
            if (contact.phone2 != null) lblPhone2.setText(contact.phone2);
        }
    }

    @Override
    protected int getLayoutXmlId() {
        return R.layout.fragment_contact_detail;
    }
}
