package software.techbase.novid.component.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wai Yan on 11/27/18.
 */
public abstract class BaseRVAdapter<MODEL> extends RecyclerView.Adapter<BaseRVAdapter.ViewHolder> {

    private Context context;
    private List<MODEL> items = new ArrayList<>();
    private OnViewHolderClickListener<MODEL> viewHolderClickListener;

    public BaseRVAdapter(Context context,
                         OnViewHolderClickListener<MODEL> viewHolderClickListener) {
        super();
        this.context = context;
        this.viewHolderClickListener = viewHolderClickListener;
    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(ViewHolder viewHolder, MODEL item);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(this.createView(context, viewGroup, viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRVAdapter.ViewHolder holder, int position) {
        bindView(holder, this.getItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addData(List<MODEL> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        items.clear();
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.getItemCount());
    }

    public Context getContext() {
        return context;
    }

    private MODEL getItem(int index) {
        return ((items != null && index < items.size()) ? items.get(index) : null);
    }

    protected List<MODEL> getItems() {
        return this.items;
    }

    @FunctionalInterface
    public interface OnViewHolderClickListener<T> {
        void onClick(View view, int position, T data);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private View selfView;

        @SuppressLint("UseSparseArrays")
        ViewHolder(View view) {

            super(view);
            this.selfView = view;

            this.selfView.setOnClickListener(clickedView -> {
                if (BaseRVAdapter.this.viewHolderClickListener != null) {
                    BaseRVAdapter.this.viewHolderClickListener.onClick(
                            this.selfView,
                            this.getAdapterPosition(),
                            BaseRVAdapter.this.getItem(getAdapterPosition()));
                }
            });
        }
    }
}
