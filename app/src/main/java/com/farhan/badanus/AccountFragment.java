package com.farhan.badanus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }
    FirebaseAuth auth;
    Button btnLogout;
    FirebaseUser user;
    TextView accountName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        auth = FirebaseAuth.getInstance();
        btnLogout = rootView.findViewById(R.id.btnLogout);
        user = auth.getCurrentUser();
        accountName = rootView.findViewById(R.id.accountName);
        accountName.setText(user.getEmail());


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }
}