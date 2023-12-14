package com.farhan.badanus;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddKegiatan extends AppCompatDialogFragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id;
    public void setId(String id){
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_kegiatan, null);

        EditText edtTitle;
        DatePicker edtDeadline;
        String dialogTitle, dialogKet;

        edtTitle = view.findViewById(R.id.edt_title);
        edtDeadline = view.findViewById(R.id.edt_date_deadline);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        edtDeadline.init(year, month, day, null);

        if (id != null && id.length()>0){
            dialogTitle = "Edit Kegiatan";
            dialogKet = "memperbarui";
        }else{
            dialogTitle = "Tambah Kegiatan";
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
                        String title = edtTitle.getText().toString();
                        String selectedYear = String.valueOf(edtDeadline.getYear());
                        String selectedMonth = String.valueOf(edtDeadline.getMonth()+1);
                        String selectedDayOfMonth = String.valueOf(edtDeadline.getDayOfMonth());

                        if (edtTitle.getText().length()>0){
                            saveData(title, selectedYear + "-"+selectedMonth+"-"+selectedDayOfMonth);
                            Toast.makeText(getActivity(), "Berhasil "+ dialogKet +" Kegiatan",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Isi Nama Kegiatan",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }
    private void saveData(String title, String deadline){
        Map<String, Object> kegiatan = new HashMap<>();
        kegiatan.put("title", title);
        kegiatan.put("deadline", deadline);

        if (id != null && id.length()>0){
            db.collection("kegiatan").document(id)
                    .set(kegiatan)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
        }else{
            db.collection("kegiatan")
                    .add(kegiatan)
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
