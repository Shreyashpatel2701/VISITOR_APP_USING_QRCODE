package com.example.clgproject1.Activity;

//import static com.tejraj.myapplication.App.CHANNEL_ID;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.clgproject1.Adapter.RequestsAdapter;
import com.example.clgproject1.Admin.Adapter.AdminActivityFragmentAdapter;
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

public class ExampleService extends Service {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentuser = firebaseAuth.getCurrentUser();
    FirebaseDatabase database;
    DatabaseReference myRef;
    int counter = 0;
    int previousSize = 0;
    final int[] currentSize = {0};

    private static final  String CHANNEL_ID_2 = "exampleServiceChannell";
    private static final  String CHANNEL_ID_3 = "exampleServiceChannelll";

    @Override
    public void onCreate() { //called only first time when we create the servuce

        super.onCreate();
        Log.i("service created","yes");

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID_2,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationChannel sserviceChannel = new NotificationChannel(
                    CHANNEL_ID_3,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
            manager.createNotificationChannel(sserviceChannel);

        }
    }




    @Override //will be called every time we call start service on this service
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input =intent.getStringExtra("inputExtra");


       // final Handler handler = new Handler();
       // Runnable runnable = new Runnable() {
         //   @Override
          //  public void run() {
              /*  database = FirebaseDatabase.getInstance();
                myRef = database.getReference("2021-08-02");*/
                //final int[] currentSize = {0};
                //add for loop to get WhomTomeet id of each ohn number

//               if(refid = "sjdbcjkadbcajbdv"){
//                    currentSize++;
//                }

               /* if(previousSize < currentSize[0]){
                    //show notification
                    abc();
                    previousSize = currentSize[0];
                }*/

                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH) + 1;
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                String a="";
                String b="";
                if (currentDay < 10){
                    a ="0"+String.valueOf(currentDay);
                }
                if (currentMonth < 10){
                    b ="0"+String.valueOf(currentMonth);
                }

                String date =String.valueOf(currentYear)+"-"+b+"-"+a;


        ArrayList<DataModel> dataModels = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DateDB");
        DatabaseReference DatedatabaseReference = databaseReference.child(date);
        DatedatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataModels.clear();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataModel dataModel = dataSnapshot.getValue(DataModel.class);

                    boolean Visited = dataModel.isVisited_Status();
                    boolean Notificationstatus = dataModel.isNotification_Status();


                   if (Notificationstatus == false) {

                       DatedatabaseReference.child(dataModel.getMobile_NO()).child("Notification_Status").setValue(true);


                           if (Visited == false) {


                           if (currentuser.getUid().matches(dataModel.getWhomToMeet())) {
                               dataModels.add(dataModel);
                               abc(dataModel.getWhomToMeet_Name());

                           }
                       }

                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setContentTitle("Looking for new request")
                //.setContentText(input)
                .setSmallIcon(R.drawable.viit_logo)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(999, notification);
        //stopSelf();
        return START_NOT_STICKY;
    }

    @Override// when service will be stopped
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void abc(String username){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,notificationIntent,0);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_3)
                .setSmallIcon(R.drawable.viit_logo)
                .setContentTitle("You have a new request  :   " + username )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(99, builder.build());

    }

}
