package com.example.clgproject1.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clgproject1.Activity.HomeActivity;
import com.example.clgproject1.DataClass.DataModel;
import com.example.clgproject1.Fragments.ActivityFragment;
import com.example.clgproject1.Fragments.HomeFragment;
import com.example.clgproject1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mcontext;
    private ArrayList<DataModel> mlistitem;




    public RequestsAdapter(Context context, ArrayList<DataModel> mlistitem) {
        this.mcontext = context;
        this.mlistitem = mlistitem;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent,false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        RequestViewHolder RequestViewHolder = (RequestViewHolder) holder;
        DataModel item = mlistitem.get(position);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DateDB");
        RequestViewHolder.mtime.setText(item.getMTime());
        RequestViewHolder.mName.setText(item.getFirst_Name()+" "+item.getLast_Name() );
        RequestViewHolder.mFrom.setText(item.getState());
        RequestViewHolder.mPhnno.setText(item.getMobile_NO());
        RequestViewHolder.mTemp.setText(item.getFeverStatus());
        RequestViewHolder.mPurpose.setText(item.getReason());
       RequestViewHolder.mImage.setImageResource(R.drawable.avatar);
        RequestViewHolder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "accept",Toast.LENGTH_SHORT).show();

              if (checkStoragePermission() == true) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                  builder.setMessage("Accept " + item.getFirst_Name() + "'s request to meet you?")
                          .setCancelable(false)
                          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {

                                  SmsManager mySmsManager = SmsManager.getDefault();
                                  mySmsManager.sendTextMessage(item.getMobile_NO(), null, "Your meeting request has been accepted", null, null);
                                  Toast.makeText(view.getContext(), "SMS sent.", Toast.LENGTH_LONG).show();

                                  databaseReference.child(item.getMDate()).child(item.getMobile_NO()).child("Visited_Status").setValue(true);
                                  databaseReference.child(item.getMDate()).child(item.getMobile_NO()).child("Visited_Status_Update").setValue("Accepted");
                                  dialog.dismiss();


                              }
                          })
                          .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                  dialog.dismiss();
                              }
                          });
                  AlertDialog alert = builder.create();
                  alert.setTitle("Accpet this request");
                  alert.show();
              }else {






              }
            }

        });

        RequestViewHolder.mReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "reschedule",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.time_pickerr, viewGroup, false);
                Button a =dialogView.findViewById(R.id.button);
                TimePicker b =dialogView.findViewById(R.id.timepicker);

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),b.getHour()+":"+b.getMinute() ,Toast.LENGTH_SHORT).show();
                        SmsManager mySmsManager = SmsManager.getDefault();
                        mySmsManager.sendTextMessage(item.getMobile_NO(),null, "sorry for inconvenience ,please come again at"+b.getHour()+":"+b.getMinute() , null, null);
                        databaseReference.child(item.getMDate()).child(item.getMobile_NO()).child("Visited_Status").setValue(true);
                        databaseReference.child(item.getMDate()).child(item.getMobile_NO()).child("Visited_Status_Update").setValue("Rescheduled");
                        alertDialog.dismiss();
                    }
                });

            }
        });

        RequestViewHolder.mDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "deny",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Don't want to meet "+item.getFirst_Name()+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(view.getContext(), "Request denied",
                                        Toast.LENGTH_SHORT).show();
                                databaseReference.child(item.getMDate()).child(item.getMobile_NO()).child("Visited_Status").setValue(true);
                                databaseReference.child(item.getMDate()).child(item.getMobile_NO()).child("Visited_Status_Update").setValue("Deny");
                                SmsManager mySmsManager = SmsManager.getDefault();
                                mySmsManager.sendTextMessage(item.getMobile_NO(),null, "Your meeting request has been denied", null, null);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.dismiss();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Deny this request");
                alert.show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return mlistitem.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder{
         ImageView mImage;
        TextView mName;
        TextView mFrom;
        TextView mTemp;
        TextView mPhnno;
        TextView mPurpose;
        Button mAccept;
        Button mReschedule;
        Button mDeny;
        TextView mtime;


        public RequestViewHolder(@NonNull  View itemView) {
            super(itemView);
            mtime = itemView.findViewById(R.id.cardtime);
            mName = itemView.findViewById(R.id.cardName);
            mFrom = itemView.findViewById(R.id.cardFrom);
            mTemp = itemView.findViewById(R.id.cardTemp);
            mPhnno = itemView.findViewById(R.id.cardPhnno);
            mPurpose = itemView.findViewById(R.id.cardPurpose);
            mAccept = itemView.findViewById(R.id.accept);
            mReschedule = itemView.findViewById(R.id.reschedule);
            mDeny = itemView.findViewById(R.id.deny);
            mImage = itemView.findViewById(R.id.imageView1);


        }
    }

    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(mcontext, Manifest.permission.SEND_SMS) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
}




