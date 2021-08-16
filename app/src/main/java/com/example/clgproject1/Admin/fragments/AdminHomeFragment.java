package com.example.clgproject1.Admin.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clgproject1.Adapter.RequestsAdapter;
import com.example.clgproject1.Admin.Adapter.AdminHomeFragmentAdapter;
import com.example.clgproject1.Admin.Model.AuthorityDataModel;
import com.example.clgproject1.DataClass.DataModel;
import com.example.clgproject1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.HomeFragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



          new AdminRequestBackGround(recyclerView).execute();


        return view;
    }

    private class AdminRequestBackGround extends AsyncTask<Void, Void, Boolean> {
        RecyclerView Admin_main_recyclerview;



       AdminRequestBackGround(RecyclerView recyclerView){

            this.Admin_main_recyclerview = recyclerView;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {

            ArrayList<AuthorityDataModel> Higherauthoritymodel = new ArrayList<>();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        AuthorityDataModel dataModel = dataSnapshot1.getValue(AuthorityDataModel.class);
                        //  String Whom_to_meet =  dataSnapshot1.child("WhomToMeet").getValue().toString().trim();
                        // String Name = dataSnapshot1.child("First_Name").getValue().toString().trim();
                        Higherauthoritymodel.add(dataModel);


                        AdminHomeFragmentAdapter adapter = new AdminHomeFragmentAdapter(getContext(),Higherauthoritymodel);
                        Admin_main_recyclerview.setAdapter(adapter);



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   });




                }
            });

            return true;
        }



    }

}