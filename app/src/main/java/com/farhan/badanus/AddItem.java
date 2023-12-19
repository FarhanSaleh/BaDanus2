package com.farhan.badanus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatDialogFragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();
    String idDoc;
    public AddItem(String id){
        this.idDoc = id;
    }

    String idX;
    String nama, pesanan, noWa;
    public void setId(String id){
        this.idX = id;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public void setPesanan(String pesanan){
        this.pesanan = pesanan;
    }
    public void setNo(String noWa){
        this.noWa = noWa;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_item, null);

        EditText edtNama, edtPesanan, edtNo;
        String dialogTitle, dialogKet;

        edtNama = view.findViewById(R.id.edt_nama);
        edtPesanan = view.findViewById(R.id.edt_pesanan);
        edtNo = view.findViewById(R.id.edt_no);

        if (idX != null && idX.length()>0){
            dialogTitle = "Edit Pesanan";
            dialogKet = "memperbarui";
            edtNama.setText(nama);
            edtPesanan.setText(pesanan);
            edtNo.setText(noWa);
        }else{
            dialogTitle = "Tambah Pesanan";
            dialogKet = "menambah";
        }

        builder.setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nama = edtNama.getText().toString();
                        String pesanan = edtPesanan.getText().toString();
                        String noWa = edtNo.getText().toString();
                        String id = idDoc ;

                        if (edtNama.getText().length()>0 && edtPesanan.getText().length()>0){
                            saveData(nama, noWa, pesanan, id);
                            Toast.makeText(getActivity(), "Berhasil "+dialogKet+" Pesanan",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            getActivity().recreate();
                        } else {
                            Toast.makeText(getActivity(), "Isi Pesanan",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            getActivity().recreate();
                        }
                    }
                });
        return builder.create();
    }
    private void saveData(String nama, String noWa, String pesan, String id){
        DocumentReference parentDocumentRef = db.collection("users").document(userId).collection("kegiatan").document(id);
        CollectionReference subCollectionRef = parentDocumentRef.collection("pesanan");

        Map<String, Object> pesanan = new HashMap<>();
        pesanan.put("Nama Pemesan", nama);
        pesanan.put("Pesanan", pesan);
        pesanan.put("NoWa", noWa);


        if (idX != null && idX.length()>0){
            subCollectionRef.document(idX)
                    .set(pesanan)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
        }else{
            subCollectionRef.add(pesanan)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }
}
