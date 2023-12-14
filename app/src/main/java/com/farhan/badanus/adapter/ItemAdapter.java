package com.farhan.badanus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farhan.badanus.R;
import com.farhan.badanus.model.ItemModel;
import com.farhan.badanus.model.SellItem;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context context;
    private List<ItemModel> list;

    public ItemAdapter(Context context, List<ItemModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan, parent, false);
        return new ItemAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        holder.item_title.setText(list.get(position).getNama());
        holder.pesanan_data.setText(list.get(position).getPesanan());

        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(list.get(holder.getAdapterPosition())));
        holder.edtButton.setOnClickListener(v -> onEdtClickCallback.onItemClicked(list.get(holder.getAdapterPosition())));
        holder.delButton.setOnClickListener(v -> onDelClickCallback.onItemClicked(list.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickCallback{
        void onItemClicked(ItemModel data);
    }
    private OnItemClickCallback onItemClickCallback;
    private OnItemClickCallback onEdtClickCallback;
    private OnItemClickCallback onDelClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }
    public void setOnEdtClickCallback(OnItemClickCallback onItemClickCallback){
        this.onEdtClickCallback = onItemClickCallback;
    }
    public void setOnDelClickCallback(OnItemClickCallback onItemClickCallback){
        this.onDelClickCallback = onItemClickCallback;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView item_title, pesanan_data;
        Button edtButton, delButton;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            pesanan_data = itemView.findViewById(R.id.pesanan_data);
            edtButton = itemView.findViewById(R.id.btnEdit);
            delButton = itemView.findViewById(R.id.btnDelete);

        }
    }
}
