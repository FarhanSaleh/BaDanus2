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
import com.farhan.badanus.model.SellItem;

import java.util.ArrayList;
import java.util.List;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.SellViewHolder>{
    private Context context;
    private List<SellItem> list;
    private Dialog dialog;
    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }
    public SellAdapter(Context context, List<SellItem> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell, parent, false);
        return new SellViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SellViewHolder holder, int position) {
        holder.sell_title.setText(list.get(position).getSellName());
        holder.date_data.setText(list.get(position).getSellDate());
        holder.delButton.setOnClickListener(v -> onDelClickCallback.onItemClicked(list.get(holder.getAdapterPosition())));
        holder.edtButton.setOnClickListener(v -> onEdtClickCallback.onItemClicked(list.get(holder.getAdapterPosition())));
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(list.get(holder.getAdapterPosition())));
    }

    public interface OnItemClickCallback{
        void onItemClicked(SellItem data);
    }
    private OnItemClickCallback onItemClickCallback;
    private OnItemClickCallback onEdtClickCallback;
    private OnItemClickCallback onDelClickCallback;
    public void setOnEdtClickCallback(OnItemClickCallback onItemClickCallback){
        this.onEdtClickCallback = onItemClickCallback;
    }
    public void setOnDelClickCallback(OnItemClickCallback onItemClickCallback){
        this.onDelClickCallback = onItemClickCallback;
    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class SellViewHolder extends RecyclerView.ViewHolder{
        TextView sell_title, date_data;
        Button edtButton, delButton;
        public SellViewHolder(@NonNull View itemView) {
            super(itemView);
            sell_title = itemView.findViewById(R.id.sell_title);
            date_data = itemView.findViewById(R.id.date_data);
            edtButton = itemView.findViewById(R.id.btnEditK);
            delButton = itemView.findViewById(R.id.btnDeleteK);
        }
    }
}
