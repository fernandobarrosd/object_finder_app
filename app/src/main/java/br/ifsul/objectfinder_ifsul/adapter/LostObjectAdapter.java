package br.ifsul.objectfinder_ifsul.adapter;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

import br.ifsul.objectfinder_ifsul.databinding.ViewHolderLostObjectBinding;
import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;

public class LostObjectAdapter extends Adapter<LostObjectAdapter.LostObjectViewHolder> {
    private final List<LostObjectDTO> lostObjectDTOS;

    public LostObjectAdapter(List<LostObjectDTO> lostObjectDTOS) {
        this.lostObjectDTOS = lostObjectDTOS;
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
        LostObjectDTO lostObjectDTO = lostObjectDTOS.get(position);
        holder.binding.lostObjectName.setText(lostObjectDTO.getName());
        holder.binding.lostObjectDescription.setText(lostObjectDTO.getDescription());
        holder.binding.lostObjectDate.setText(lostObjectDTO.getDataEncontrado());
    }

    @Override
    public int getItemCount() {
        return lostObjectDTOS.size();
    }

    public static class LostObjectViewHolder extends ViewHolder {
        ViewHolderLostObjectBinding binding;
        public LostObjectViewHolder(@NonNull ViewHolderLostObjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
