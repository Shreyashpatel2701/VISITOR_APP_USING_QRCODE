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

import com.example.clgproject1.DataClass.DataModel;
import com.example.clgproject1.R;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class AdminActivityFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mcontext;
    private ArrayList<DataModel> mlistitem;

    public AdminActivityFragmentAdapter(Context context,ArrayList<DataModel> mlistitem) {
        this.mcontext = context;
        this.mlistitem = mlistitem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminactivityfragment_rowitem, parent,false);
        return new AdminActivityFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AdminActivityFragmentViewHolder activityFragmentViewHolder = (AdminActivityFragmentViewHolder) holder;
        DataModel item = mlistitem.get(position);

        activityFragmentViewHolder.mImage.setImageResource(R.drawable.avatar);
        activityFragmentViewHolder.mName.setText(item.getFirst_Name());
        activityFragmentViewHolder.mFrom.setText(item.getState());
        activityFragmentViewHolder.mPhnno.setText(item.getMobile_NO());
        activityFragmentViewHolder.mTemp.setText(item.getFeverStatus());
        activityFragmentViewHolder.mToMeet.setText(item.getWhomToMeet_Name());
        activityFragmentViewHolder.mPurpose.setText(item.getReason());
        activityFragmentViewHolder.mDate.setText(item.getMDate());
        activityFragmentViewHolder.mVisited_status.setText(item.getVisited_Status_Update());


        activityFragmentViewHolder.mVisitor_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Call clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+91"+item.getMobile_NO()));
                startActivity(view.getContext(), intent,null);
            }
        });
        activityFragmentViewHolder.mVisitor_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Messaged clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.fromParts("sms","+91"+item.getMobile_NO(),null));
                startActivity(view.getContext(),intent,null);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlistitem.size();
    }

    class AdminActivityFragmentViewHolder extends RecyclerView.ViewHolder{
        TextView mDate;
        ImageView mImage;
        TextView mName;
        TextView mFrom;
        TextView mPhnno;
        TextView mTemp;
        TextView mToMeet;
        TextView mPurpose;
        Button mVisitor_call;
        Button mVisitor_message;
        TextView mVisited_status;


        public AdminActivityFragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.imageView1);
            mName = itemView.findViewById(R.id.cardName);
            mFrom = itemView.findViewById(R.id.cardFrom);
            mPhnno = itemView.findViewById(R.id.cardPhnno);
            mTemp = itemView.findViewById(R.id.cardTemp);
            mToMeet = itemView.findViewById(R.id.card_tomeetname);
            mPurpose = itemView.findViewById(R.id.cardPurpose);
            mVisitor_call = itemView.findViewById(R.id.visitor_call);
            mVisitor_message = itemView.findViewById(R.id.visitor_message);
            mDate = itemView.findViewById(R.id.card_date);
            mVisited_status = itemView.findViewById(R.id.card_visitedstatus);

        }
    }

}
