package com.farhan.badanus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farhan.badanus.databinding.ItemSellBinding;

import java.util.ArrayList;
import java.util.List;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.SellViewHolder> {

    private ArrayList<SellItem> sellItemList;

    public SellAdapter(ArrayList<SellItem> sellItemList) {
        this.sellItemList = sellItemList;
    }

   class SellViewHolder extends RecyclerView.ViewHolder {
        TextView sellName, sellCount, sellPrice;
        SellViewHolder(View itemView) {
            super(itemView);
            sellName = itemView.findViewById(R.id.sell_name);
            sellCount = itemView.findViewById(R.id.count_data);
            sellPrice = itemView.findViewById(R.id.price_data);
        }
    }

    @NonNull
    @Override
    public SellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sell, parent, false);
        return new SellViewHolder(itemView);
    }
    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }
    @Override
    public void onBindViewHolder(@NonNull final SellAdapter.SellViewHolder holder, int position) {
        SellItem sellItem = sellItemList.get(position);
        holder.sellName.setText(sellItem.getSellName());
        holder.sellCount.setText(Integer.toString(sellItem.getSellCount()) );
        holder.sellPrice.setText(Integer.toString(sellItem.getSellPrice()) );
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(sellItemList.get(holder.getAdapterPosition())));
    }

    public interface OnItemClickCallback{
        void onItemClicked(SellItem data);
    }

    @Override
    public int getItemCount() {
        return sellItemList.size();
    }
}
