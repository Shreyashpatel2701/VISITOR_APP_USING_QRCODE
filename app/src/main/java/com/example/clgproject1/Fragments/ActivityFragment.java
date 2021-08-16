package com.example.clgproject1.Fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clgproject1.Adapter.RequestsAdapter;
import com.example.clgproject1.Admin.Adapter.AdminActivityFragmentAdapter;
import com.example.clgproject1.Admin.fragments.AdminActivityFragment;
import com.example.clgproject1.DataClass.DataModel;
import com.example.clgproject1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static com.example.clgproject1.Activity.App.CHANNEL_1_ID;

public class ActivityFragment extends Fragment {


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentuser = firebaseAuth.getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        RecyclerView activity_recyclerview = (RecyclerView) view.findViewById(R.id.activity_recyclerview);
        activity_recyclerview.setHasFixedSize(true);
        activity_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String a=String.valueOf(currentDay) ;
        String b=String.valueOf(currentMonth);
        if (currentDay < 10){
            a ="0"+String.valueOf(currentDay);
        }
        if (currentMonth < 10) {
            b = "0" + String.valueOf(currentMonth);
        }
        String date =String.valueOf(currentYear)+"-"+b+"-"+a;

        //String date =String.valueOf(currentYear)+"-"+b+"-"+a;

        new ActivityBackGround(date,activity_recyclerview).execute();


        return view;
    }



    private class ActivityBackGround extends AsyncTask<Void, Void, Boolean> {
        RecyclerView activity_recyclerview;
        String Date;


        ActivityBackGround(String date, RecyclerView recyclerView) {
            this.Date = date;
            this.activity_recyclerview = recyclerView;
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


                            if (Visited == true) {

                                   if (currentuser.getUid().matches(dataModel.getWhomToMeet())) {
                                       dataModels.add(dataModel);
                                       AdminActivityFragmentAdapter requestsAdapter = new AdminActivityFragmentAdapter(getContext(), dataModels);

                                       activity_recyclerview.setAdapter(requestsAdapter);
                                   }

                            }

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



