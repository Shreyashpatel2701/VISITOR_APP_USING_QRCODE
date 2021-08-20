package com.example.clgproject1.Admin.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clgproject1.Activity.EditProfile;
import com.example.clgproject1.Activity.LoginActivity;
import com.example.clgproject1.Activity.MainActivity;
import com.example.clgproject1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class AdminSettingsFragment extends Fragment {
    StringBuilder data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.settings_fragment_admin, container, false);

        FirebaseAuth firebaseAuth;
        FirebaseUser currentuser;
        TextView Logout = view.findViewById(R.id.logoutadmin);
        TextView editprofile = view.findViewById(R.id.Editprofileadmin);
        TextView exporttoexcel = view.findViewById(R.id.export_to_excel_admin);
        firebaseAuth = FirebaseAuth.getInstance();
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        exporttoexcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                export_to_excel();

            }
        });


        return view;
    }

    void export_to_excel() {
         data = new StringBuilder();
        data.append("First Name, Last Name, Mobile_No, Email ID, Time, Date , Address, City, State, Whom to meet, Purpose of meet, Fever, Cough, Breathing Difficulty, Respiratory Disease,Visited Status Update ,Rescheduled time ");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DateDB");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot datedatasnapshot : snapshot.getChildren()  ){

                    for ( DataSnapshot dataSnapshot : datedatasnapshot.getChildren() ){

                        ArrayList<String> Data =  new ArrayList();
                        for (DataSnapshot VsitorsDatasnapshot : dataSnapshot.getChildren()){

                          Data.add(VsitorsDatasnapshot.getValue().toString());


                      }

                        data.append("\n"+ Data.get(7)+","+ Data.get(10)+","+Data.get(13)+","+Data.get(5)+","+Data.get(12)+","+Data.get(11)+","+Data.get(0)+","+Data.get(3)+","+Data.get(18)+","+Data.get(23)+","+Data.get(15)+","+Data.get(6)+","+Data.get(4)+","+Data.get(2)+","+Data.get(17)+","+Data.get(20)+","+Data.get(16) ) ;


                    }




                }
                       abd();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }


        });



    }

    public void abd(){
        Log.i("finallist",data.toString());
        try {
            FileOutputStream out = getActivity().openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            Context context = getContext();
            File filelocation = new File(getActivity().getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.clgproject1", filelocation);
            Intent fileintent = new Intent(Intent.ACTION_SEND);
            fileintent.setType("text/csv");
            fileintent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileintent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileintent, "Send mail"));


        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}