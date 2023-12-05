package br.ifsul.objectfinder_ifsul.adapter;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import java.util.List;
import br.ifsul.objectfinder_ifsul.databinding.ViewHolderLostObjectBinding;
import br.ifsul.objectfinder_ifsul.dto.LostObjectDTO;

public class LostObjectAdapter extends Adapter<LostObjectAdapter.LostObjectViewHolder> {
    private final List<LostObjectDTO> lostObjectDTOS;
    private NavController navController;

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
    public void onBindViewHolder(@NonNull LostObjectViewHolder viewHolder, int index) {
        LostObjectDTO lostObjectDTO = lostObjectDTOS.get(index);
        viewHolder.binding.lostObjectDateTextView.setText(lostObjectDTO.getFoundedDate());
        viewHolder.binding.lostObjectDescription.setText(lostObjectDTO.getDescription());
        viewHolder.binding.lostObjectName.setText(lostObjectDTO.getName());
    }

    @Override
    public int getItemCount() {
        return lostObjectDTOS.size();
    }

    public static class LostObjectViewHolder extends ViewHolder {
        public ViewHolderLostObjectBinding binding;
        public LostObjectViewHolder(@NonNull ViewHolderLostObjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }
}
