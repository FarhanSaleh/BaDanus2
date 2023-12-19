package com.farhan.badanus;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.farhan.badanus.adapter.SellAdapter;
import com.farhan.badanus.model.SellItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SellFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();
    private RecyclerView sellRecyclerView;
    private List<SellItem> list = new ArrayList<>();
    private SellAdapter sellAdapter;
    private Button addButton, edtButton, delButton;
    private ProgressDialog progressDialog;
    public SellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sell, container, false);
        sellRecyclerView = rootView.findViewById(R.id.sell_rv);
        addButton = rootView.findViewById(R.id.btnAddSell);

        sellAdapter = new SellAdapter(getActivity(), list);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil Data...");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sellRecyclerView.setLayoutManager(layoutManager);
        sellRecyclerView.setAdapter(sellAdapter);

        sellAdapter.setOnItemClickCallback(this::showSelectedSellItem);
        sellAdapter.setOnEdtClickCallback(this::editData);
        sellAdapter.setOnDelClickCallback(this::delData);

        progressDialog.show();
        db.collection("users").document(userId).collection("kegiatan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChange")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                SellItem sellItem = new SellItem(document.getString("title"), document.getString("deadline"), document.getId());
                                list.add(sellItem);
                            }
                            sellAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Data gagal diambil", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormDialog();
            }
        });

        return rootView;
    }

    public void openFormDialog(){
        AddKegiatan addKegiatan = new AddKegiatan();
        addKegiatan.show(getActivity().getSupportFragmentManager(),"Tambah Kegiatan");
    }

    private void editData(SellItem sellItem){
        AddKegiatan addKegiatan = new AddKegiatan();
        String id = sellItem.getSellId();
        String title = sellItem.getSellName();
        addKegiatan.setId(id);
        addKegiatan.setTitle(title);
        addKegiatan.show(getActivity().getSupportFragmentManager(),"Tambah Kegiatan");
    }
    private void delData(SellItem sellItem){
        String id = sellItem.getSellId();
        db.collection("users").document(userId).collection("kegiatan").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getActivity(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                            getActivity().recreate();
                        }
                    }
                });
    }
    private void showSelectedSellItem(SellItem sellItem)
    {
        Intent intent = new Intent(getActivity(), DetailSellActivity.class);

        intent.putExtra("sell_name", sellItem.getSellName());
        intent.putExtra("sell_date", sellItem.getSellDate());
        intent.putExtra("sell_id", sellItem.getSellId());

        startActivity(intent);

        Toast.makeText(getActivity(), "Kamu memilih "+ sellItem.getSellName(), Toast.LENGTH_SHORT).show();
    }
}