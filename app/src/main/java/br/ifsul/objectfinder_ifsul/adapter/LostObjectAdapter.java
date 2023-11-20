package br.ifsul.objectfinder_ifsul.adapter;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import br.ifsul.objectfinder_ifsul.databinding.ViewHolderLostObjectBinding;

public class LostObjectAdapter extends Adapter<LostObjectAdapter.LostObjectViewHolder> {
    private final ArrayList<String> names;

    public LostObjectAdapter(ArrayList<String> names) {
        this.names = names;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public LostObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderLostObjectBinding view = ViewHolderLostObjectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LostObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostObjectViewHolder holder, int position) {
        String name = names.get(position);
        holder.binding.lostObjectName.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class LostObjectViewHolder extends ViewHolder {
        ViewHolderLostObjectBinding binding;
        public LostObjectViewHolder(@NonNull ViewHolderLostObjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
