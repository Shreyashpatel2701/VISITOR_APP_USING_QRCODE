package com.example.clgproject1.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clgproject1.Admin.Model.AuthorityDataModel;
import com.example.clgproject1.R;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class AdminHomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mcontext;
    private ArrayList<AuthorityDataModel> mlistitem;

    public AdminHomeFragmentAdapter(Context context,ArrayList<AuthorityDataModel> mlistitem) {
        this.mcontext = context;
        this.mlistitem = mlistitem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminhomefragment_rowitem, parent,false);
        return new AdminHomeFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        AdminHomeFragmentViewHolder homeFragmentViewHolder = (AdminHomeFragmentViewHolder) holder;
        AuthorityDataModel item = mlistitem.get(position);

        homeFragmentViewHolder.mImage.setImageResource(R.drawable.avatar1);
        homeFragmentViewHolder.mName.setText(item.getName());
        homeFragmentViewHolder.mPosition.setText(item.getDesignation());
        homeFragmentViewHolder.mPhnno.setText(item.getPhone());


        homeFragmentViewHolder.mUser_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open default call app with number displayed
                Toast.makeText(view.getContext(), "Call clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                //intent.setData(Uri.parse("tel:+91"+item.getPhnno()));
                intent.setData(Uri.parse("tel:+917447297382"));
                startActivity(view.getContext(), intent,null);
            }
        });
        homeFragmentViewHolder.mUser_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open default msg app with opened conversation of the user number
                Toast.makeText(view.getContext(), "Messaged clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.fromParts("sms","+917447297382",null));
                startActivity(view.getContext(),intent,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlistitem.size();
    }

    class AdminHomeFragmentViewHolder extends RecyclerView.ViewHolder{

        ImageView mImage;
        TextView mName;
        TextView mPosition;
        TextView mPhnno;
        Button mUser_call;
        Button mUser_message;

        public AdminHomeFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.higherAuthority_img);
            mName = itemView.findViewById(R.id.higherAuthority_name);
            mPosition = itemView.findViewById(R.id.higherAuthority_position);
            mPhnno = itemView.findViewById(R.id.higherAuthority_phnno);
            mUser_call = itemView.findViewById(R.id.user_call);
            mUser_message = itemView.findViewById(R.id.user_message);



        }
    }
}
