package com.example.clgproject1.Fragments;

import android.app.Notification;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.clgproject1.Adapter.RequestsAdapter;
import com.example.clgproject1.DataClass.DataModel;
import com.example.clgproject1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static com.example.clgproject1.Activity.App.CHANNEL_1_ID;

public class HomeFragment extends Fragment {

     FirebaseAuth firebaseAuth;
     FirebaseUser currentuser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
         firebaseAuth = FirebaseAuth.getInstance();
         currentuser = firebaseAuth.getCurrentUser();
        RecyclerView main_recyclerview = (RecyclerView) view.findViewById(R.id.mainRecyclerView2);
        main_recyclerview.setHasFixedSize(true);
        main_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        String date =a+"-"+b+"-"+String.valueOf(currentYear);

        new RequestBackGround(date,main_recyclerview).execute();




        return view;
    }




    private class RequestBackGround extends AsyncTask<Void, Void, Boolean> {
           RecyclerView main_recyclerview;
           String Date;


       RequestBackGround(String date,RecyclerView recyclerView){
           this.Date = date;
           this.main_recyclerview = recyclerView;
       }


        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<DataModel> dataModels = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DateDB");
            DatabaseReference DatedatabaseReference = databaseReference.child(Date);
            DatedatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dataModels.clear();
                    Log.e("msg",snapshot.toString());

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.e("msg",dataSnapshot.toString());
                        DataModel dataModel = dataSnapshot.getValue(DataModel.class);

                        boolean Visited = dataModel.isVisited_Status();


                           if (Visited == false) {



                            if (currentuser.getUid().matches(dataModel.getWhomToMeet())) {



                                dataModels.add(dataModel);
                                RequestsAdapter requestsAdapter = new RequestsAdapter(getContext(),dataModels);
                                main_recyclerview.setAdapter(requestsAdapter);
                                requestsAdapter.getItemCount();




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




