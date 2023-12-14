package com.farhan.badanus;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardFragment extends Fragment {
    public DashboardFragment() {
        // Required empty public constructor
    }
    TextView namaUser;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        namaUser = rootView.findViewById(R.id.namaUser);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        namaUser.setText(user.getEmail());
        return rootView;
    }
}