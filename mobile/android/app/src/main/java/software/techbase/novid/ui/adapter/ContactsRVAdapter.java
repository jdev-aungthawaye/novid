package software.techbase.novid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

import software.techbase.novid.R;
import software.techbase.novid.component.android.xlogger.XLogger;
import software.techbase.novid.component.ui.base.BaseRVAdapter;
import software.techbase.novid.domain.remote.api.GetContacts;

/**
 * Created by Wai Yan on 3/10/20.
 */
public class ContactsRVAdapter extends BaseRVAdapter<GetContacts.Response> implements Filterable {

    private List<GetContacts.Response> contactListFiltered;

    public ContactsRVAdapter(Context context, OnViewHolderClickListener<GetContacts.Response> viewHolderClickListener) {
        super(context, viewHolderClickListener);
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater != null ? inflater.inflate(R.layout.item_contact, viewGroup, false) : null;
    }

    @Override
    protected void bindView(ViewHolder viewHolder, GetContacts.Response item) {

        AppCompatTextView lblTownship = viewHolder.itemView.findViewById(R.id.lblTownship);
        AppCompatTextView lblRegion = viewHolder.itemView.findViewById(R.id.lblRegion);
        AppCompatTextView lblDepartment = viewHolder.itemView.findViewById(R.id.lblDepartment);

        lblTownship.setText(item.township);
        lblRegion.setText(item.region);
        lblDepartment.setText(item.department);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    contactListFiltered = getItems();
                } else {
                    List<GetContacts.Response> filteredList = new ArrayList<>();
                    for (GetContacts.Response contact : getItems()) {
                        if (contact.township.contains(charString) || contact.region.contains(charSequence) || contact.charge.contains(charSequence) || contact.phone1.contains(charSequence)) {
                            filteredList.add(contact);
                        }
                    }
                    contactListFiltered = filteredList;
                }

                XLogger.debug(this.getClass(), "Filter result : " + contactListFiltered.get(1).township);

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<GetContacts.Response>) filterResults.values;
                clearData();
                notifyDataSetChanged();
            }
        };
    }
}

