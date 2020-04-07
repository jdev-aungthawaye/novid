package software.techbase.novid.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnTextChanged;
import software.techbase.novid.R;
import software.techbase.novid.component.ui.base.BaseBottomSheetFragment;
import software.techbase.novid.component.ui.decoration.RVDividerItemDecoration;
import software.techbase.novid.domain.remote.api.GetContacts;
import software.techbase.novid.ui.adapter.ContactsRVAdapter;

/**
 * Created by Wai Yan on 4/7/20.
 */
public class ContactListFragment extends BaseBottomSheetFragment {

    @BindView(R.id.rvContactList)
    RecyclerView rvContactList;

    private ContactsRVAdapter contactsRVAdapter;
    private ArrayList<GetContacts.Response> contacts;

    @Override
    @SuppressWarnings("unchecked")
    protected void createView() {

        Bundle bundle = getArguments();
        assert bundle != null;
        this.contacts = (ArrayList<GetContacts.Response>) bundle.getSerializable("contacts");

        this.initContactsRecyclerView();
        this.contactsRVAdapter.addData(contacts);
    }

    @Override
    protected int getLayoutXmlId() {
        return R.layout.fragment_contact_list;
    }

    private void initContactsRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        this.contactsRVAdapter = new ContactsRVAdapter(getActivity(), (view, position, data) -> this.showContactDetail(data));
        this.rvContactList.setHasFixedSize(true);
        this.rvContactList.setLayoutManager(linearLayoutManager);
        this.rvContactList.addItemDecoration(new RVDividerItemDecoration(Objects.requireNonNull(getActivity()), R.drawable.view_divider));
        this.rvContactList.setAdapter(this.contactsRVAdapter);
    }

    private void showContactDetail(GetContacts.Response contact) {

        ContactDetailFragment contactDetailFragment = new ContactDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", contact);
        contactDetailFragment.setArguments(bundle);
        contactDetailFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "contact-detail");
    }

    @OnTextChanged(R.id.edtSearch)
    void onFocusChangeEdtSearch(CharSequence text) {

        String keyword = text.toString();

        List<GetContacts.Response> filteredList = new ArrayList<>();

        if (!keyword.isEmpty() && !(Character.isWhitespace(keyword.charAt(0)) && Character.isWhitespace(keyword.charAt(keyword.length() - 1)))) {
            for (GetContacts.Response contact : this.contacts) {
                if (contact.township.contains(keyword) || contact.region.contains(keyword)) {
                    filteredList.add(contact);
                }
            }
        } else {
            filteredList = this.contacts;
        }
        contactsRVAdapter.clearData();
        contactsRVAdapter.addData(filteredList);
    }
}
