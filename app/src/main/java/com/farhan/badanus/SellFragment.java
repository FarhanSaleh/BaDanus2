package com.farhan.badanus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SellFragment extends Fragment {
    private RecyclerView sellRecyclerView;
    private ArrayList<SellItem> sellItemList = new ArrayList<>();
    public SellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sell, container, false);
        sellRecyclerView = rootView.findViewById(R.id.sell_rv);
        sellItemList = getSellItemList();
        showRecyclerList();
        return rootView;
    }

    private ArrayList<SellItem> getSellItemList(){
        String[] dataName = getResources().getStringArray(R.array.data_name);
        int[] dataPrice = getResources().getIntArray(R.array.data_price);
        int[] dataCount = getResources().getIntArray(R.array.data_count);
        ArrayList<SellItem> sellItemArrayList = new ArrayList<>();
        for (int i = 0; i<dataName.length; i++){
            SellItem sellItem = new SellItem();
            sellItem.setSellName(dataName[i]);
            sellItem.setSellPrice(dataPrice[i]);
            sellItem.setSellCount(dataCount[i]);
            sellItemArrayList.add(sellItem);
        }
        return sellItemArrayList;
    }
    private void showRecyclerList(){
        sellRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SellAdapter sellAdapter = new SellAdapter(sellItemList);
        sellRecyclerView.setAdapter(sellAdapter);
        sellAdapter.setOnItemClickCallback(this::showSelectedSellItem);
    }

    private void showSelectedSellItem(SellItem sellItem)
    {
        Intent intent = new Intent(getActivity(), DetailSellActivity.class);

        intent.putExtra("sell_name", sellItem.getSellName());
        intent.putExtra("sell_count", Integer.toString(sellItem.getSellCount()));
        intent.putExtra("sell_price", Integer.toString(sellItem.getSellPrice()));

        startActivity(intent);

        Toast.makeText(getActivity(), "Kamu memilih "+ sellItem.getSellName(), Toast.LENGTH_SHORT).show();
    }
}