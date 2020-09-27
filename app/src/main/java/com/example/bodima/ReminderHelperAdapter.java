package com.example.bodima;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.Reminders;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static androidx.core.content.ContextCompat.getSystemService;


public class ReminderHelperAdapter extends RecyclerView.Adapter {

    List<Reminders> remindersList;
    Context context;

//    public ReminderHelperAdapter(List<Reminders> remindersList, Context context) {
//        this.remindersList = remindersList;
//        this.context = context;
//    }

    public ReminderHelperAdapter(List<Reminders> remindersList){
        this.remindersList = remindersList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_layout, parent, false);
        viewHolderClass viewHolderClass = new viewHolderClass(view);

//        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//        int date = calendar.get(Calendar.DATE);
//
//        if(fetchData.getDay().equals(date)){
//            addNotification(holder.;
//        }

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        viewHolderClass viewHolderClass = (ReminderHelperAdapter.viewHolderClass)holder;
        Reminders reminders = remindersList.get(position);
        Reminders fetchData = remindersList.get(position);

        viewHolderClass.description.setText(fetchData.getDescription());
        viewHolderClass.day.setText(fetchData.getDay());
        viewHolderClass.month.setText(fetchData.getMonth());
        viewHolderClass.amount.setText(fetchData.getAmount());

//        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//        int date = calendar.get(Calendar.DATE);
//
//        if(fetchData.getDay().equals(date)){
//            addNotification(holder.);
//        }

    }

//    public void addNotification(Context context){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentTitle("You have a Reminder")
//                .setContentText("Today is your Special day");
//
//        Intent notificationIntent = new Intent(context, MyReminders.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(context, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        NotificationManager manager = (NotificationManager) getSystemService(context.Notification);
//        manager.notify(0, builder.build());
//
//    }

    @Override
    public int getItemCount() {
        return remindersList.size();
    }

    public class viewHolderClass extends RecyclerView.ViewHolder{

        TextView description;
        TextView month;
        TextView day;
        TextView amount;

        public viewHolderClass(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.desclabel);
            day = itemView.findViewById(R.id.day);
            month = itemView.findViewById(R.id.month);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
