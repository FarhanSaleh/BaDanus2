package com.farhan.badanus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.farhan.badanus.adapter.ItemAdapter;
import com.farhan.badanus.adapter.SellAdapter;
import com.farhan.badanus.model.ItemModel;
import com.farhan.badanus.model.SellItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DetailSellActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();
    private RecyclerView itemRecyclerView;
    private List<ItemModel> list = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private Button addButton;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sell);

        itemRecyclerView = findViewById(R.id.item_rv);
        itemAdapter = new ItemAdapter(getApplicationContext(), list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickCallback(this::showOption);
        itemAdapter.setOnEdtClickCallback(this::editData);
        itemAdapter.setOnDelClickCallback(this::delData);

        Intent intent = getIntent();
        String sellName = intent.getStringExtra("sell_name");
        String sellPrice = intent.getStringExtra("sell_date");
        String sellId = intent.getStringExtra("sell_id");
        this.id = sellId;

        TextView nameTextView = findViewById(R.id.sell_name);
        TextView priceTextView = findViewById(R.id.date_data);

        nameTextView.setText(sellName);
        priceTextView.setText(sellPrice);

        addButton = findViewById(R.id.btnAddItem);

        DocumentReference parentDocumentRef = db.collection("users").document(userId).collection("kegiatan").document(id);
        CollectionReference subCollectionRef = parentDocumentRef.collection("pesanan");

        subCollectionRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChange")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ItemModel itemModel = new ItemModel(document.getString("Nama Pemesan"), document.getString("Pesanan"), document.getString("NoWa"), document.getId());
                                list.add(itemModel);
                            }
                            itemAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data gagal diambil", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFormDialog();
            }
        });
    }
    public void openFormDialog(){
        AddItem addItem = new AddItem(id);
        addItem.show(getSupportFragmentManager(), "Tambah Item");
    }

    private void showOption(ItemModel itemModel)
    {
        String url = "https://api.whatsapp.com/send?phone="+itemModel.getNoWa();
        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
//        boolean installed = appInstalledOrNot("com.whatsapp");
//        if (installed){
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+itemModel.getNoWa()+"&text="+""));
//            startActivity(intent);
//        }else {
//            Toast.makeText(DetailSellActivity.this, "Whatsapp not installed"+itemModel.getNoWa(), Toast.LENGTH_SHORT).show();
//        }
    }

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
    private void editData(ItemModel itemModel){
        AddItem addItem = new AddItem(id);
        String id = itemModel.getIdItem();
        String name = itemModel.getNama();
        String pesanan = itemModel.getPesanan();
        String noWa = itemModel.getNoWa();
        addItem.setId(id);
        addItem.setNama(name);
        addItem.setPesanan(pesanan);
        addItem.setNo(noWa);
        addItem.show(getSupportFragmentManager(),"Tambah Kegiatan");
    }
    private void delData(ItemModel itemModel){
        DocumentReference parentDocumentRef = db.collection("users").document(userId).collection("kegiatan").document(id);
        CollectionReference subCollectionRef = parentDocumentRef.collection("pesanan");

        String id = itemModel.getIdItem();
        subCollectionRef.document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                        }else{
                           recreate();
                        }
                    }
                });
    }
}