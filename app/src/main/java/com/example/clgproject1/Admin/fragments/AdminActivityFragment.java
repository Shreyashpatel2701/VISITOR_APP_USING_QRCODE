package com.example.clgproject1.Admin.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clgproject1.Adapter.RequestsAdapter;
import com.example.clgproject1.Admin.Adapter.AdminActivityFragmentAdapter;
import com.example.clgproject1.Admin.Adapter.AdminHomeFragmentAdapter;
import com.example.clgproject1.Admin.Model.AuthorityDataModel;
import com.example.clgproject1.DataClass.DataModel;
import com.example.clgproject1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

public class AdminActivityFragment extends Fragment {

    ImageView img_calender;
    int y;
    int m;
    int d;
    TextView date;
    int day;
    int month;
    int year;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_activity, container, false);
//        date = view.findViewById(R.id.date);
      //  img_calender = view.findViewById(R.id.img_calender);
        RecyclerView recyclerView = view.findViewById(R.id.ActivityFragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


         new AdminActivityBackGround(recyclerView).execute();


         return view;
    }





    private class AdminActivityBackGround extends AsyncTask<Void, Void, Boolean> {
        RecyclerView adminactivity_recyclerview;



        AdminActivityBackGround(RecyclerView recyclerView){

            this.adminactivity_recyclerview = recyclerView;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<DataModel> dataModels = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DateDB");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    dataModels.clear();


                    for (DataSnapshot datedataSnapshot : snapshot.getChildren()) {


                        for (DataSnapshot dataSnapshot : datedataSnapshot.getChildren()) {


                            DataModel dataModel = dataSnapshot.getValue(DataModel.class);
                            boolean Visited = dataModel.isVisited_Status();


                           // if (Visited == true) {


                                dataModels.add(dataModel);
                            Collections.reverse(dataModels);
                                AdminActivityFragmentAdapter requestsAdapter = new AdminActivityFragmentAdapter(getContext(),dataModels);

                                adminactivity_recyclerview.setAdapter(requestsAdapter);


                           // }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return true;
        }



    }

}
